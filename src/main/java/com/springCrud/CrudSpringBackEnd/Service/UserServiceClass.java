package com.springCrud.CrudSpringBackEnd.Service;

import com.springCrud.CrudSpringBackEnd.Model.User;
import com.springCrud.CrudSpringBackEnd.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;
@Service

public class UserServiceClass {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    public User createUser(User user)
    {
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        return userRepository.save(user);
    }
}
