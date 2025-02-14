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

    public String createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user).getId();
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
