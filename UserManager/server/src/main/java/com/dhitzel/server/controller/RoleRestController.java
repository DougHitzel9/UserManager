package com.dhitzel.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhitzel.server.entity.Role;
import com.dhitzel.server.service.RoleJpaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
@RequestMapping("/jpa/roles")
@CrossOrigin(origins = "http://localhost:4200")
public class RoleRestController {

    private Logger logger = LoggerFactory.getLogger(RoleRestController.class);

    @Autowired
    RoleJpaService roleService;

	@GetMapping("")
    @PreAuthorize("isAuthenticated()")
	public String getRoles() {
		String json = null;

		try {
			List<Role> roleList = roleService.findAll();

			ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

			json = writer.writeValueAsString(roleList);
		} catch (Exception e) {
			logger.error("*** Unable to retrieve Role. Exception encountered - " + e);
		}

		return json;
	}
}
