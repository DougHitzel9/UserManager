package com.dhitzel.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhitzel.server.repository.AdminRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminRestController {

	@Autowired
    private AdminRepository adminRepository;

	private Logger logger = LoggerFactory.getLogger(AdminRestController.class);

	@PostMapping("/reset")
	public void reset() {
		logger.info("*** reset()");

		try {
			adminRepository.reset();
		} catch (Exception e) {
			logger.error("*** Unable to retrieve User. Exception encountered - " + e);
		}
	}
}