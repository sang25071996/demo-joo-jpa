package jooq.demo.com.service;

import java.util.Collections;
import java.util.Date;
import jooq.demo.com.dto.AccountDTO;
import jooq.demo.com.entites.Account;
import jooq.demo.com.mapper.AccountMapper;
import jooq.demo.com.repository.AccountRepository;
import org.jooq.DSLContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final int EXPIRATION = 60 * 24;

  private final AccountRepository accountRepository;

  private final PasswordEncoder encoder;

  private final DSLContext dslContext;

  private final AccountMapper accountMapper;

  public UserDetailsServiceImpl(AccountRepository accountRepository,
      PasswordEncoder encoder, DSLContext dslContext,
      AccountMapper accountMapper) {
    this.accountRepository = accountRepository;
    this.encoder = encoder;
    this.dslContext = dslContext;
    this.accountMapper = accountMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account user = accountRepository.findByUsername(username);

    return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            true,
            true,
            true,
            true,
        Collections.emptyList());
  }

  public AccountDTO saveUser(AccountDTO accountDTO) {
    Account account = new Account();
    account.setUsername(accountDTO.getUsername());
    account.setEmail(accountDTO.getEmail());
    account.setPassword(encoder.encode(accountDTO.getPassword()));
    account.setCreatedOn(new Date());
    account.setLastLogin(new Date());
    return accountMapper.toDTO(this.accountRepository.save(account));
  }
}
