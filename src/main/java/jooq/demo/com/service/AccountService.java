package jooq.demo.com.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import jooq.demo.com.entites.Account;
import jooq.demo.com.entites.AccountState;
import jooq.demo.com.masters.Tables;
import jooq.demo.com.repository.AccountRepository;
import jooq.demo.com.repository.AccountStateRepository;
import jooq.demo.com.request.LockKeyAccount;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private static final long LOCK_TIMEOUT = 60000L;
    private final AccountRepository accountRepositiory;
    private final AccountStateRepository accountStateRepository;
    private final LockRegistry lockRegistry;
    private final DSLContext dslContext;

    public AccountService(AccountRepository accountRepositiory,
        AccountStateRepository accountStateRepository,
        LockRegistry lockRegistry, DSLContext dslContext) {
        this.accountRepositiory = accountRepositiory;
        this.accountStateRepository = accountStateRepository;
        this.lockRegistry = lockRegistry;
        this.dslContext = dslContext;
    }

    public List<Account> getAccounts() {
        return this.dslContext.select().from(Tables.ACCOUNTS).fetchInto(Account.class);
    }

    public boolean obtainLock(LockKeyAccount lockKeyAccount) {
        AccountState accountState = this.accountStateRepository.findAccountStatesByUsernameAndEmail(
            lockKeyAccount.getUsername(), lockKeyAccount.getEmail());
        Lock lock = lockRegistry.obtain(lockKeyAccount.toString());
        boolean lockAcquired = false;
        if (accountState == null) {
            try {
                lockAcquired = lock.tryLock(1, TimeUnit.SECONDS);
                if (lockAcquired) {
                    this.accountStateRepository.save(convertToAccountState(lockKeyAccount));
                }
            } catch (InterruptedException e) {
                LOGGER.info("Error obtainLock: {}", e.getMessage());
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
            return lockAcquired;
        }

        long expireAfter = accountState.getModifiedDate().getTime() + LOCK_TIMEOUT;
        if (expireAfter < System.currentTimeMillis()) {

        }
        return true;
    }

    private AccountState convertToAccountState(LockKeyAccount lockKeyAccount) {
        AccountState accountState = new AccountState();
        accountState.setUsername(lockKeyAccount.getUsername());
        accountState.setEmail(lockKeyAccount.getEmail());
        accountState.setCreatedDate(new Date());
        accountState.setModifiedDate(new Date());
        return accountState;
    }
}
