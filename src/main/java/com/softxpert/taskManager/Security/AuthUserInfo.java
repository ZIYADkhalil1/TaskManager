package com.softxpert.taskManager.Security;

public class AuthUserInfo {
    private Long id;
    private String email;
    private Integer role;

    public AuthUserInfo(Long id, String email, Integer role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getRole() {
        return role;
    }
}
