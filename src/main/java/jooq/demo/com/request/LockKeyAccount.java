package jooq.demo.com.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LockKeyAccount {

  private String username;
  private String email;

  @Override
  public String toString() {
    return "account_state" + "|" + username + "_" + email;
  }
}
