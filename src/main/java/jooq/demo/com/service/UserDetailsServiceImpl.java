package jooq.demo.com.service;

import static jooq.demo.com.Tables.ROLES;
import static jooq.demo.com.Tables.USERS;

import com.cdp.cdp.entity.Privilege;
import com.cdp.cdp.entity.Role;
import com.cdp.cdp.entity.User;
import com.cdp.cdp.entity.UserAuthCode;
import com.cdp.cdp.repository.AuthCodeRepository;
import com.cdp.cdp.repository.RoleRepository;
import com.cdp.cdp.repository.UserRepository;
import com.cdp.common.constants.Constant;
import com.cdp.common.enums.IngestionStatusEnums;
import com.cdp.common.enums.RoleEnum;
import com.cdp.common.utils.ImageUtils;
import com.cdp.dto.CreateUserRequest;
import com.cdp.dto.RequestPaging;
import com.cdp.dto.SignupRequest;
import com.cdp.dto.UpdateUserRequest;
import com.cdp.exception.BadRequestException;
import com.cdp.exception.ErrorParam;
import com.cdp.exception.Errors;
import com.cdp.exception.SysError;
import com.cdp.model.RoleUpdate;
import com.cdp.model.UserDetail;
import com.cdp.model.UserFilter;
import com.cdp.model.UserPagination;
import com.cdp.model.UserProfile;
import com.cdp.service.softdelete.UserExecuteSoftDelete;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.jooq.DSLContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;

  private final DSLContext dslContext;

  public UserDetailsServiceImpl(
      PasswordEncoder passwordEncoder, DSLContext dslContext) {

    this.passwordEncoder = passwordEncoder;
    this.dslContext = dslContext;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new BadRequestException(
                    new SysError(Errors.ERROR_USER_NOT_FOUND, new ErrorParam(Errors.USERNAME))));

    return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            true,
            true,
            true,
            true,
            getAuthorities(Arrays.asList(user.getRole())));
  }
}
