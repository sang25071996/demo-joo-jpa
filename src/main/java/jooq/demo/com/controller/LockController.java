package jooq.demo.com.controller;

import jooq.demo.com.dto.BookStateDTO;
import jooq.demo.com.model.LockKey;
import jooq.demo.com.service.LockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lock")
public class LockController {

  private final LockService lockService;

  public LockController(LockService lockService) {
    this.lockService = lockService;
  }

  @PostMapping("/obtain")
  public ResponseEntity<Boolean> obtainLock(@RequestBody LockKey lockKey) {
    return ResponseEntity.ok(lockService.obtainLock(lockKey));
  }

  @PostMapping("/release-lock")
  public ResponseEntity<Boolean> releaseLock(@RequestBody BookStateDTO bookStateDTO) {
    return ResponseEntity.ok(this.lockService.releaseLock(bookStateDTO));
  }
}
