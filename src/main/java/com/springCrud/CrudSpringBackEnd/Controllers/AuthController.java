package com.springCrud.CrudSpringBackEnd.Controllers;

import com.springCrud.CrudSpringBackEnd.Model.JwtRequest;
import com.springCrud.CrudSpringBackEnd.Model.JwtResponse;
import com.springCrud.CrudSpringBackEnd.Model.User;
import com.springCrud.CrudSpringBackEnd.Repositories.UserRepository;
import com.springCrud.CrudSpringBackEnd.Service.UserDetailServiceImp;
import com.springCrud.CrudSpringBackEnd.Service.UserServiceClass;
import com.springCrud.CrudSpringBackEnd.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@CrossOrigin
@RestController
@Configuration
@RequestMapping("/auth")
public class AuthController implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")  // Replace with the origin of your frontend app
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Autowired
    private UserDetailServiceImp UserDetailServiceImp;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceClass userService;

    @Autowired
    private JwtHelper helper;


    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = UserDetailServiceImp.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private void doAuthenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    @PostMapping("/signup")
    public User createUser(@RequestBody User user) {
        Optional<User> usernameEntry = userRepository.findByUsername(user.getUsername());
        Optional<User> emailEntry = userRepository.findByUsername(user.getUsername());
        if(usernameEntry.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
        else if (emailEntry.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
        } else return userService.createUser(user);
    }

}
