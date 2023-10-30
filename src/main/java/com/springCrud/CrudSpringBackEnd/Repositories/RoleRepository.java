package com.springCrud.CrudSpringBackEnd.Repositories;

import com.springCrud.CrudSpringBackEnd.Model.ERole;
import com.springCrud.CrudSpringBackEnd.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role findByName(ERole erole);

}
