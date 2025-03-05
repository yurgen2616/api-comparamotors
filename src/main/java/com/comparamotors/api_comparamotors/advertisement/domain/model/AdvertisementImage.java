package com.comparamotors.api_comparamotors.advertisement.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "advertisement_images")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class AdvertisementImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    public AdvertisementImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}