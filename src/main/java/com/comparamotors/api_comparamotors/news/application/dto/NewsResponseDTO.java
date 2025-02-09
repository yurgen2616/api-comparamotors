package com.comparamotors.api_comparamotors.news.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class NewsResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String redirectLink;
    private LocalDateTime publishedAt;
    private List<NewsImageDTO> images;
}