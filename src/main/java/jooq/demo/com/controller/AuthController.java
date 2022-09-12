package jooq.demo.com.controller;


import java.util.Collections;
import jooq.demo.com.common.constant.Constants;
import jooq.demo.com.common.constant.Errors;
import jooq.demo.com.common.utils.JwtUtils;
import jooq.demo.com.entites.User;
import jooq.demo.com.exception.BadRequestException;
import jooq.demo.com.model.ErrorParam;
import jooq.demo.com.model.JwtResponse;
import jooq.demo.com.model.LoginRequest;
import jooq.demo.com.model.ResponeJson;
import jooq.demo.com.model.SignupRequest;
import jooq.demo.com.model.SysError;
import jooq.demo.com.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final JwtUtils jwtUtils;

  private final UserService userService;

  @PostMapping("/signin")
  @PreAuthorize("permitAll()")
  public ResponseEntity<ResponeJson<JwtResponse>> authenticateUser(
      @Validated @RequestBody LoginRequest loginRequest) {
    if (!userService.existsByUsername(loginRequest.getUsername())) {
      throw new BadRequestException(
          new SysError(Errors.USERNAME_NOT_FOUND, new ErrorParam(Errors.USERNAME)));
    }
    User user = userService.getUserByUsername(loginRequest.getUsername());

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(),
            loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    return ResponseEntity.ok().body(new ResponeJson<>(new JwtResponse(jwt,
        jwtUtils.getExpiration(jwt),
        user.getId(),
        user.getUsername(),
        Collections.EMPTY_SET
    ), HttpStatus.OK, Constants.SUCCESS));
  }

  @PostMapping("/signup")
  @PreAuthorize("permitAll()")
  public ResponseEntity<ResponeJson<Boolean>> registerUser(
      @Validated @RequestBody SignupRequest signupRequest) {

    if (userService.existsByUsername(signupRequest.getUsername())) {
      throw new BadRequestException(
          new SysError(Errors.USERNAME_NOT_FOUND, new ErrorParam(Errors.USERNAME)));
    }

    return ResponseEntity.ok().body(
        new ResponeJson<>(userService.saveUser(signupRequest), HttpStatus.OK, Constants.SUCCESS));
  }
}
