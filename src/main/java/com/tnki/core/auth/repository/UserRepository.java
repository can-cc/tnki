package com.tnki.core.auth.repository;

import com.tnki.core.auth.model.User;
import com.tnki.core.share.model.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends BaseRepository {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUsername(String username) {
        User user = new User();
        user.setID("U001");
        user.setUsername("doge");
        user.setPassword(bCryptPasswordEncoder.encode(("dddoge123")));
        return user;
    }

}
