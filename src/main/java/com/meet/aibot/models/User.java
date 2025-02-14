package com.meet.aibot.models;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String name;

    @NotBlank
    @Indexed(unique = true)
    private String  email;

    @NotBlank
    private String password;

    private Chat[] chats;

}
