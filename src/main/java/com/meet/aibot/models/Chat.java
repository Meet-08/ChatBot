package com.meet.aibot.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Chat")
public class Chat {

    @Id
    private UUID id = UUID.randomUUID();

    @NotBlank
    private String role;

    @NotBlank
    private String content;

}
