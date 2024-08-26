package com.dhitzel.server.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhitzel.server.entity.User;
import com.dhitzel.server.repository.UserJpaRepository;

@Service
public class UserJpaService {

    private Logger logger = LoggerFactory.getLogger(UserJpaService.class);

    @Autowired
    private UserJpaRepository userRepository;

    public void deleteById(Long id) {
        logger.info("*** UserJpaRepositoryImpl.deleteById()");

        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        logger.info("*** UserJpaRepositoryImpl.findAll()");

        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        logger.info("*** UserJpaRepositoryImpl.findById()");

        return userRepository.findById(id);
    }

    public User save(User user) throws Exception {
        logger.info("*** UserJpaRepositoryImpl.save()");

        String password = "$2y$06$6AJQwwi.o6cvRCpY5mgW7ONuDFgutTAPdk8/Tikxtg120ongnLokK";

        user.setPassword(password);

        return userRepository.save(user);
    }
}
