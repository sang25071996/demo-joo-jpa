package jooq.demo.com.controller;

import jooq.demo.com.common.constant.Constants;
import jooq.demo.com.common.constant.Errors;
import jooq.demo.com.dto.RoleDTO;
import jooq.demo.com.entites.Role;
import jooq.demo.com.exception.BadRequestException;
import jooq.demo.com.model.ErrorParam;
import jooq.demo.com.model.ResponeJson;
import jooq.demo.com.model.SysError;
import jooq.demo.com.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @PostMapping()
  @PreAuthorize("permitAll()")
  public ResponseEntity<ResponeJson<Boolean>> saveRole(
      @Validated @RequestBody RoleDTO roleDTO) {

    if (roleService.existsByUsername(roleDTO.getName())) {
      throw new BadRequestException(
          new SysError(Errors.ROLE_NOT_FOUND, new ErrorParam(Errors.ROLE)));
    }

    return ResponseEntity.ok().body(
        new ResponeJson<>(roleService.save(roleDTO), HttpStatus.OK, Constants.SUCCESS));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponeJson<Role>> getRoleById(@PathVariable("id") Long id) {
    return ResponseEntity.ok().body(
        new ResponeJson<>(roleService.findById(id), HttpStatus.OK, Constants.SUCCESS));
  }
}

