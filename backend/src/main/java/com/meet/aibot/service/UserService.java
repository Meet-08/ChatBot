package com.meet.aibot.service;

import com.meet.aibot.dto.LoginRequest;
import com.meet.aibot.dto.LoginResponse;
import com.meet.aibot.dto.RegistrationRequest;
import com.meet.aibot.dto.RegistrationResponse;
import com.meet.aibot.models.User;
import com.meet.aibot.repository.UserRepo;
import com.meet.aibot.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private final UserRepo repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepo repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
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
        existingUser.setChats(user.getChats());
        repo.save(existingUser);
        existingUser.setPassword(null);
        return existingUser;
    }

    public User verifyUser(String email, String password) {
        User user = repo.findOneByEmail(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

    public User getUser(String email) {
        return repo.findOneByEmail(email).orElse(null);
    }

    public RegistrationResponse registerUser(RegistrationRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setChats(new ArrayList<>());
        User createdUser = createUser(user);
        String token = jwtUtil.generateToken(createdUser.getEmail(), createdUser.getId());
        return new RegistrationResponse(createdUser, token);
    }

    public LoginResponse loginUser(LoginRequest request) {
        User user = verifyUser(request.getEmail(), request.getPassword());
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        user.setPassword(null);
        return new LoginResponse(user, token);
    }
}
