package jooq.demo.com.exception;

import jooq.demo.com.model.SysError;

public class BadRequestException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private SysError sysError;

  public BadRequestException(SysError sysError) {
    this.sysError = sysError;
  }

  public BadRequestException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public BadRequestException(Throwable throwable) {
    super(throwable);
  }

  public BadRequestException(String message) {
    super(message);
  }

  public SysError getSysError() {
    return sysError;
  }

  public void setSysError(SysError sysError) {
    this.sysError = sysError;
  }
}
