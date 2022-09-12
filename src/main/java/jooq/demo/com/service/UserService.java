package jooq.demo.com.service;

import java.util.Date;
import jooq.demo.com.entites.Role;
import jooq.demo.com.entites.User;
import jooq.demo.com.model.SignupRequest;
import jooq.demo.com.repository.RoleRepository;
import jooq.demo.com.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;

  public UserService(UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  public User getUserByUsername(String username) {
    return this.userRepository.findByUsername(username);
  }

  public boolean existsByUsername(String username) {
    return this.userRepository.existsByUsername(username);
  }

  @Transactional
  public boolean saveUser(SignupRequest signupRequest) {
    Role role = this.roleRepository.findByName(signupRequest.getRole());
    User user = new User();
    user.setUsername(signupRequest.getUsername());
    user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    user.setRole(role);
    user.setCreatedDate(new Date());
    user.setUpdatedDate(new Date());
    this.userRepository.save(user);
    return true;
  }
}
