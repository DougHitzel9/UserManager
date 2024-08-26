package com.dhitzel.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhitzel.server.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {}