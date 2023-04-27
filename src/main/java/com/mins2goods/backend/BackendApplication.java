package com.mins2goods.backend;

import com.mins2goods.backend.model.User;
import com.mins2goods.backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	static CommandLineRunner run(UserService userService){
		return args -> {
			// Creating a new user with ROLE_USER
			userService.saveUser(new User(null, "ROLE_USER", "alice", true, "password123", "1234567890", "alice@example.com", "Smith", "Alice"));

			// Creating a new user with ROLE_SELLER
			userService.saveUser(new User(null, "ROLE_SELLER", "bob", true, "password456", "0987654321", "bob@example.com", "Johnson", "Bob"));

			// Creating a new user with ROLE_ADMIN
			userService.saveUser(new User(null, "ROLE_ADMIN", "charlie", true, "password789", "9876543210", "charlie@example.com", "Williams", "Charlie"));

		};
	}

}
