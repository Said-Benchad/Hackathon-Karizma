package com.springCrud.CrudSpringBackEnd.Controllers;

import com.springCrud.CrudSpringBackEnd.Model.User;
import com.springCrud.CrudSpringBackEnd.Service.UserDetailsImpl;
import com.springCrud.CrudSpringBackEnd.Service.UserService;
import com.springCrud.CrudSpringBackEnd.Service.UserServiceImpl;
import com.springCrud.CrudSpringBackEnd.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> Signup(@RequestBody User user) {
        System.out.println(user);
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userService.saveUser(userDto);
        return ResponseEntity.ok(userDto.getUsername() + " Saved");
    }

    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<String> signin(@RequestBody User user) {
        UserDto userDto = new UserDto();
        System.out.println(user);
        return ResponseEntity.ok(user.getUsername() + " Is Signed in!");

    }
}
