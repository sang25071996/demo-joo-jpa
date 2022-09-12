package jooq.demo.com.repository;

import jooq.demo.com.entites.AccountState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStateRepository extends JpaRepository<AccountState, Long> {

    AccountState findAccountStatesByUsernameAndEmail(String username, String email);
}
