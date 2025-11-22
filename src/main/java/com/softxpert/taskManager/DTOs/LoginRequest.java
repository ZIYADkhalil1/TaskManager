package com.softxpert.taskManager.DTOs;

public record LoginRequest(
        String email,
        String password
) {}
