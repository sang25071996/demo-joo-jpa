package jooq.demo.com.entites;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOOK_STATE", schema = "masters")
@Setter
@Getter
public class BookState {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "username")
  private String username;

  @Column(name = "created_date")
  private Date createdDate;

  @Column(name = "updated_date")
  private Date updatedDate;

}
