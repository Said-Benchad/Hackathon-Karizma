package com.springCrud.CrudSpringBackEnd.Service;

import com.springCrud.CrudSpringBackEnd.Model.Role;
import com.springCrud.CrudSpringBackEnd.Model.User;
import com.springCrud.CrudSpringBackEnd.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    Role saveRole(Role role);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();


    void addRoleToUser(String username, String roleName);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<User> listUsers();
}
