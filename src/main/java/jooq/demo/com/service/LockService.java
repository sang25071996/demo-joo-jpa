package jooq.demo.com.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import jooq.demo.com.common.utils.WebUtils;
import jooq.demo.com.dto.BookStateDTO;
import jooq.demo.com.entites.BookState;
import jooq.demo.com.model.LockKey;
import jooq.demo.com.repository.BookStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

@Service
public class LockService {

  private final static Logger LOGGER = LoggerFactory.getLogger(LockService.class);
  private static final long LOCK_TIMEOUT = 60000L * 5;
  private final BookStateRepository bookStateRepository;
  private final LockRegistry lockRegistry;

  public LockService(BookStateRepository bookStateRepository,
      LockRegistry lockRegistry) {
    this.bookStateRepository = bookStateRepository;
    this.lockRegistry = lockRegistry;
  }

  public boolean obtainLock(LockKey lockKey) {

    BookState bookState = bookStateRepository.findBookStateByTitle(lockKey.getTitle());
    Lock lock = lockRegistry.obtain(lockKey.toString());
    boolean lockAcquired = false;
    if (bookState == null) {
      try {
        lockAcquired = lock.tryLock(1, TimeUnit.SECONDS);
        LOGGER.info("acquired lock: {}", lockAcquired);
        if (lockAcquired) {
          bookStateRepository.save(buildEntity(lockKey));
          Thread.currentThread().sleep(5000L);
          LOGGER.info("message: {} - user: {}", "lock successful", WebUtils.getUsername());
        } else {
          LOGGER.info("message: {}", "lock unsuccessful");
        }
      } catch (InterruptedException e) {
        LOGGER.info("Error: {} - user: {}", e.getMessage(), WebUtils.getUsername());
      } finally {
        lock.unlock();
        LOGGER.info("unlock successful - user: {}", WebUtils.getUsername());
      }
      return lockAcquired;
    }

    long bookStateTimeout = bookState.getUpdatedDate().getTime() + LOCK_TIMEOUT;
    //calculate timeout state
    if (bookStateTimeout < System.currentTimeMillis()) {
      try {
        lockAcquired = lock.tryLock(1, TimeUnit.SECONDS);
        if (lockAcquired) {
          bookState.setUsername(WebUtils.getUsername());
          bookState.setUpdatedDate(new Date(System.currentTimeMillis()));
          this.bookStateRepository.save(bookState);
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      } finally {
        lock.unlock();
      }
      //lock exists, has time out, so assign it to new user
      return lockAcquired;
    }
    return bookState.getUsername().equals(WebUtils.getUsername());
  }

  public boolean releaseLock(BookStateDTO bookStateDTO) {
    BookState bookState = bookStateRepository.findBookStateByTitle(bookStateDTO.getTitle());
    if (bookState != null && bookState.getUsername().equals(WebUtils.getUsername())) {
      bookStateRepository.delete(bookState);
      LOGGER.info("Lock release successfully");
    }
    return true;
  }

  public BookState buildEntity(LockKey lockKey) {
    BookState bookState = new BookState();
    bookState.setTitle(lockKey.getTitle());
    bookState.setUsername(lockKey.getUsername());
    bookState.setCreatedDate(new Date());
    bookState.setUpdatedDate(new Date());
    return bookState;
  }
}
