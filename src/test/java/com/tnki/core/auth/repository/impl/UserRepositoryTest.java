package com.tnki.core.auth.repository.impl;

import com.tnki.core.auth.model.User;
import com.tnki.core.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;



@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@ComponentScan
class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    @Test
    void insertUser() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String stamp = Long.toString(timestamp.getTime());

        User user = new User();
        user.setUsername("O" + stamp);
        user.setPasswordHash("*****");
        repo.insertUser(user);
        assertNotEquals(0, user.getID());
    }

}