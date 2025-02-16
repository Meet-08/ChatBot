package com.meet.aibot.security;

import com.meet.aibot.models.User;
import com.meet.aibot.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserServiceDetailsImp implements UserDetailsService {

    private final UserRepo repo;

    public UserServiceDetailsImp(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findOneByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .build();
    }
}
