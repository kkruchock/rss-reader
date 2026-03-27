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
public class RssSource {

    private Long id;
    private Long userId;
    private String name;
    private String url;
    private LocalDateTime createdAt;
}