package com.dhitzel.server.repository;

import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository extends AbstractRepository {

    public void reset() throws Exception {
        String call = "CALL reset()";

        Object[] params = new Object[0];

        execute(call, params);
    }
}