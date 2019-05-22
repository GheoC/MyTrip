package com.sci.Trip.Service;

import com.sci.Trip.Model.User;
import com.sci.Trip.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Iterable<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User findUser(String email){
        return usersRepository.findByEmail(email);
    }

    public User loginUser(String email, String password){
        User user = this.findUser(email);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())==true){
            return user;
        }
        return null;
    }

    public void saveUser(User user){
        user.setActive(1);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);

        jdbcTemplate.update("INSERT INTO user_role (user_id, role_id) VALUES (?, ?)", user.getId(),1);
    }


}
