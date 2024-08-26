package com.dhitzel.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhitzel.server.entity.User;
import com.dhitzel.server.service.UserJpaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

// https://www.bezkoder.com/spring-boot-jpa-crud-rest-api

@RestController
@RequestMapping("/jpa/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {

    private Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    UserJpaService userService;

	@GetMapping("")
    @PreAuthorize("isAuthenticated()")
	public String getUsers() {
		String json = null;

		try {
			List<User> userList = userService.findAll();

			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

			json = writer.writeValueAsString(userList);
		} catch (Exception e) {
			logger.error("*** Unable to retrieve User list. Exception encountered - " + e);
		}

		return json;
	}

	@DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
		logger.info("*** deleteById() - " + id);

		userService.deleteById(id);

        return ResponseEntity.ok().build();
	}

	@PostMapping("")
    @PreAuthorize("isAuthenticated()")
	public String save(@RequestBody User user) {
		logger.info("*** save() - " + user.getUsername());

		String json = null;

		try {
			User savedUser = userService.save(user);

			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

			json = writer.writeValueAsString(savedUser);
		} catch (Exception e) {
			logger.error("*** Unable to save User. Exception encountered - " + e);
		}

		return json;
	}
}
