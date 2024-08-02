package com.dhitzel.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dhitzel.server.entity.Role;
import com.dhitzel.server.entity.User;
import com.dhitzel.server.repository.RoleRepository;
import com.dhitzel.server.repository.UserRepository;
import com.dhitzel.server.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EntityRestController {

	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


	private Logger logger = LoggerFactory.getLogger(EntityRestController.class);

	@GetMapping("/roles")
	public String getRoles() {
		String json = null;

		try {
			List<Role> roleList = roleRepository.findAll();

			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

			json = writer.writeValueAsString(roleList);
		} catch (Exception e) {
			logger.error("*** Unable to retrieve Role. Exception encountered - " + e);
		}

		return json;
	}

	@GetMapping("/role/{id}")
	public String getRole(@PathVariable(value = "id") long id) {
		String json = null;

		try {
			Role role = roleRepository.findById(id);

			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

			json = writer.writeValueAsString(role);
		} catch (Exception e) {
			logger.error("*** Unable to retrieve Role. Exception encountered - " + e);
		}

		return json;
	}

	@GetMapping("/users")
    @PreAuthorize("isAuthenticated()")
	public String getUsers() {
		String json = null;

		try {
			List<User> userList = userRepository.findAll();

			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

			json = writer.writeValueAsString(userList);
		} catch (Exception e) {
			logger.error("*** Unable to retrieve User. Exception encountered - " + e);
		}

		return json;
	}

	@GetMapping("/user/{id}")
	public String getUser(@PathVariable(value = "id") long id) {
		String json = null;

		try {
			User user = userRepository.findById(id);

			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

			json = writer.writeValueAsString(user);
		} catch (Exception e) {
			logger.error("*** Unable to retrieve User. Exception encountered - " + e);
		}

		return json;
	}

	@PostMapping("/user")
	public String saveUser(@RequestBody User user) {
		logger.info("*** saveUser() - " + user.getUsername());

		String json = null;

		try {
			if (user.exists()) {
				userRepository.update(user);
			} else {
				long userId = userRepository.insert(user);

				user.setUserId(userId);
			}
	
			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

			json = writer.writeValueAsString(user);
		} catch (Exception e) {
			logger.error("*** Unable to save User. Exception encountered - " + e);
		}

		return json;
	}

	@PostMapping("/reset")
	public void reset() {
		logger.info("*** reset()");

		try {
			roleRepository.reset();
		} catch (Exception e) {
			logger.error("*** Unable to retrieve User. Exception encountered - " + e);
		}
	}
}