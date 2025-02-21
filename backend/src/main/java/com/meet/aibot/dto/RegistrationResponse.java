package com.meet.aibot.dto;

import com.meet.aibot.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RegistrationResponse {

    private User user;
    private String token;
}
