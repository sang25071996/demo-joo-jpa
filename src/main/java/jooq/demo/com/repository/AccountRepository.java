package jooq.demo.com.repository;

import static jooq.demo.com.masters.tables.Accounts.ACCOUNTS;

import jooq.demo.com.entites.Account;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository {

    @Autowired
    private DSLContext dslContext;

    public List<Account> getAccounts() {
        List<Account> queryResults = dslContext.selectFrom(ACCOUNTS).fetchInto(Account.class);
        return queryResults;
    }
}
