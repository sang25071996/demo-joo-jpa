package jooq.demo.com.repository;

import jooq.demo.com.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  boolean existsByUsername(String username);
}
