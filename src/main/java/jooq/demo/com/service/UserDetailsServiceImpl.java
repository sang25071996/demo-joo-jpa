package jooq.demo.com.service;

import java.util.Collections;
import jooq.demo.com.entites.User;
import jooq.demo.com.repository.UserRepository;
import org.jooq.DSLContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  private final DSLContext dslContext;

  public UserDetailsServiceImpl(UserRepository userRepository,
      DSLContext dslContext) {
    this.userRepository = userRepository;
    this.dslContext = dslContext;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("user-not-found");
    }
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        true,
        true,
        true,
        true,
        Collections.emptyList());
  }
}
