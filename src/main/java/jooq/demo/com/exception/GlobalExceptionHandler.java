package jooq.demo.com.exception;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = {"jooq.demo.com"})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
  private static final String ERROR_MGS = "[demo-app]: {}";

  @ResponseBody
  @ExceptionHandler(value = {Exception.class})
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ResponeJson<Boolean>> exception(Exception ex) {
    LOG.error(ERROR_MGS, ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ResponeJson(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
  }

  @ResponseBody
  @ExceptionHandler(value = BadRequestException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ResponseEntity<ResponeJson<Boolean>> badRequestException(BadRequestException badRequestException) {
    String message = badRequestException.getMessage();
    LOG.error(ERROR_MGS, badRequestException.getMessage(), badRequestException);
    SysError ex = badRequestException.getSysError();
    if (ObjectUtils.isEmpty(ex)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponeJson(message));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ResponeJson(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex));
    }
  }

  @ResponseBody
  @ExceptionHandler(value = UsernameNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ResponseEntity<ResponeJson<Boolean>> usernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
    String message = usernameNotFoundException.getMessage();
    LOG.error(ERROR_MGS, usernameNotFoundException.getMessage(), usernameNotFoundException);
    SysError ex = usernameNotFoundException.getSysError();
    if (ObjectUtils.isEmpty(ex)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponeJson(message));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ResponeJson(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex));
    }
  }

  @ResponseBody
  @ExceptionHandler(value = {BadCredentialsException.class})
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ResponeJson<Boolean>> authorizationException(
      AuthenticationException authorizationException) {
    String message = authorizationException.getMessage();
    LOG.error(ERROR_MGS, authorizationException.getMessage(), authorizationException);

    if (ObjectUtils.isEmpty(message)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponeJson(message));
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ResponeJson("Username or Pasword incorrect"));
    }

  }

}
