package ru.itis.rssreader.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RssSourceDto {

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotBlank(message = "URL не может быть пустым")
    private String url;
}
