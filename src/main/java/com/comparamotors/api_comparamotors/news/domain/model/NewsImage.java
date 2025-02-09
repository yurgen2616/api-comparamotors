package com.comparamotors.api_comparamotors.news.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "news_images")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class NewsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    public NewsImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}