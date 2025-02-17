package com.meet.aibot.service;

import com.meet.aibot.models.User;
import com.meet.aibot.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User createUser(User user) {
        if (repo.findOneByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        user.setPassword(null);
        return user;
    }

    public User updateUser(User user) {
        User existingUser = repo.findOneByEmail(user.getEmail()).orElse(null);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        System.out.println(existingUser);
        repo.save(existingUser);
        existingUser.setPassword(null);
        return existingUser;
    }

    public User verifyUser(String email, String password) {
       User user = repo.findOneByEmail(email).orElse(null);
       if(user == null) {
           throw new RuntimeException("User not found");
       }
       if(!encoder.matches(password, user.getPassword())) {
           throw new RuntimeException("Invalid password");
       }
       return user;
    }

    public User getUser(String email) {
        return repo.findOneByEmail(email).orElse(null);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
