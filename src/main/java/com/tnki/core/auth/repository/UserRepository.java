package com.tnki.core.auth.repository;

import com.tnki.core.auth.model.User;
import com.tnki.core.share.model.BaseRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends BaseRepository {

    public User findByUsername(String username) {
        User user = new User();
        user.setID("U001");
        user.setUsername("Linus");
        user.setPassword("P123456");
        return user;
    }

}
