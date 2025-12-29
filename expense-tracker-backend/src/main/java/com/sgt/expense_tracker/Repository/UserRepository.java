package com.sgt.expense_tracker.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void register(String name,String username,String email,String password,long phone){

    }
}
