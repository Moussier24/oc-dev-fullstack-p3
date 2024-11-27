package com.ocangjava.portail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponse {
    private Long id;
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    @lombok.Setter(lombok.AccessLevel.NONE)
    private String picture;
    private String description;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setPicture(String picture) {
        System.out.println("******cc*** picture: " + picture);
        if (picture == null || picture.trim().isEmpty()) {
            this.picture = null;
            return;
        }

        if (picture.startsWith("http://") || picture.startsWith("https://")) {
            this.picture = picture;
        } else {
            String baseUrl = "http://localhost:8080";
            String normalizedPicture = picture.startsWith("/") ? picture : "/" + picture;
            this.picture = baseUrl + normalizedPicture;
        }
    }
}