package com.meet.aibot.repository;

import com.meet.aibot.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

    Optional<User> findOneByEmail(@NotBlank @Email(message = "Email is required") String email);
    
}
