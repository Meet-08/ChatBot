package com.meet.aibot.dto;

import jakarta.validation.constraints.NotBlank;

public record Message(@NotBlank String message){}
