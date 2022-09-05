package jooq.demo.com.service;

import static jooq.demo.com.masters.tables.Roles.ROLES;

import jooq.demo.com.dto.RoleDTO;
import jooq.demo.com.entites.Role;
import jooq.demo.com.repository.RoleRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

  private final RoleRepository roleRepository;
  private final DSLContext dslContext;

  public RoleService(RoleRepository roleRepository, DSLContext dslContext) {
    this.roleRepository = roleRepository;
    this.dslContext = dslContext;
  }

  @Transactional
  public boolean save(RoleDTO roleDTO) {
    Role role = new Role();
    role.setName(roleDTO.getName());
    role.setDescription(roleDTO.getDescription());
    this.roleRepository.save(role);
    return true;
  }

  public boolean existsByUsername(String name) {
    return this.roleRepository.existsByName(name);
  }

  public Role findById(Long id) {
    return this.dslContext.select().from(ROLES).where(ROLES.ID.eq(id)).fetchOneInto(Role.class);
  }
}
