package jooq.demo.com.entites;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ACCOUNT_STATE", schema = "masters")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountState {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private int id;

  @Column(name = "username")
  private String username;


  @Column(name = "email")
  private String email;

  @Column(name = "created_date")
  private Date createdDate;

  @Column(name = "modified_date")
  private Date modifiedDate;
}
