package com.meet.aibot.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "User")
public class User {

    @Id
    private String id;

    private String name;

    @NotBlank
    @Email(message = "Email is required")
    @Indexed(unique = true)
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private Chat[] chats;

}
