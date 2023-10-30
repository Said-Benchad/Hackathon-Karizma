package com.springCrud.CrudSpringBackEnd.Repositories;

import com.springCrud.CrudSpringBackEnd.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);
}
