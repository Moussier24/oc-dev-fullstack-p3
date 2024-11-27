package com.ocangjava.portail.dto;

import com.ocangjava.portail.model.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();

        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}