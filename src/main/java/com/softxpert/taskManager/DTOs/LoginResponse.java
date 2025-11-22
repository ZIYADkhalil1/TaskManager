package com.softxpert.taskManager.DTOs;

public record LoginResponse(
        String token,
        Integer role
) {}