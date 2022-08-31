package jooq.demo.com.exception;


import org.springframework.security.access.AccessDeniedException;

public class UnauthorizedAccessException extends AccessDeniedException {

  private static final long serialVersionUID = 1L;
  private  SysError sysError;

  public UnauthorizedAccessException(SysError sysError) {
      super(sysError.getCode());
      this.sysError = sysError;
  }

  public UnauthorizedAccessException(String message) {
    super(message);
  }

  public SysError getSysError() {
    return sysError;
  }

  public void setSysError(SysError sysError) {
    this.sysError = sysError;
  }
}
