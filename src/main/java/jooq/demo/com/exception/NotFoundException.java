package jooq.demo.com.exception;

import jooq.demo.com.model.SysError;

public class NotFoundException extends RuntimeException{

  private static final long serialVersionUID = 1L;
  private SysError sysError;

  public NotFoundException(SysError sysError) {
    this.sysError = sysError;
  }

  public NotFoundException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public NotFoundException(Throwable throwable) {
    super(throwable);
  }

  public NotFoundException(String message) {
    super(message);
  }

  public SysError getSysError() {
    return sysError;
  }

  public void setSysError(SysError sysError) {
    this.sysError = sysError;
  }

}
