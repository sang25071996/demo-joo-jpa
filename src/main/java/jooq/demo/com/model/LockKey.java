package jooq.demo.com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LockKey {
  private static final String LOCK_PREFIX = "book";
  private String title;
  private String username;

  @Override
  public String toString() {
    return LOCK_PREFIX + "|" + title + "-" + username;
  }
}
