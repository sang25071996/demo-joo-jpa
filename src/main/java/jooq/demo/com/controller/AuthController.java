package jooq.demo.com.controller;

import jooq.demo.com.dto.AccountDTO;
import jooq.demo.com.exception.ResponeJson;
import jooq.demo.com.service.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponeJson<AccountDTO>> registerUser(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(new ResponeJson<>(this.userDetailsService.saveUser(accountDTO)));
    }
}
