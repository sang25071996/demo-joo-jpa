package jooq.demo.com.repository;

import jooq.demo.com.entites.BookState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStateRepository extends JpaRepository<BookState, Long> {

  BookState findBookStateByTitle(String title);
}