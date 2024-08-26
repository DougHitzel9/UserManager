package com.dhitzel.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhitzel.server.entity.Role;

@Repository
public interface RoleJpaRepository extends JpaRepository<Role, Long> {}