package ru.itis.rssreader.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String username;
    private String passwordHash;
    private LocalDateTime createdAt;
}