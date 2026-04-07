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
public class Post {

    private Long id;
    private Long sourceId;
    private String sourceName;
    private String title;
    private String description;
    private String link;
    private String guid;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
}