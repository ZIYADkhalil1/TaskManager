package com.softxpert.taskManager.DTOs;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        Integer roleId
) {}