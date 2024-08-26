package com.dhitzel.server.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhitzel.server.entity.Role;
import com.dhitzel.server.repository.RoleJpaRepository;

@Service
public class RoleJpaService {

    private Logger logger = LoggerFactory.getLogger(UserJpaService.class);

    @Autowired
    private RoleJpaRepository roleRepository;

    public List<Role> findAll() {
        logger.info("*** RoleJpaRepositoryImpl.findAll()");

        return roleRepository.findAll();
     }

    public Role save(Role role) throws Exception {
        logger.info("*** RoleJpaRepositoryImpl.save()");

        return roleRepository.save(role);
    }

}
