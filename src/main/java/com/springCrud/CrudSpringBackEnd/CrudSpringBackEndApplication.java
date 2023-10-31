package com.springCrud.CrudSpringBackEnd;

import com.springCrud.CrudSpringBackEnd.Model.User;
import com.springCrud.CrudSpringBackEnd.Service.UserServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CrudSpringBackEndApplication {

	public static void main(String[] args) {

		SpringApplication.run(CrudSpringBackEndApplication.class, args);

	}

}
