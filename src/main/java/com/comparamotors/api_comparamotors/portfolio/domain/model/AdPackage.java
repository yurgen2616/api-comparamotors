package com.comparamotors.api_comparamotors.portfolio.domain.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ad_packages")
public class AdPackage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
    
    @ManyToMany
    @JoinTable(
        name = "package_ad_space",
        joinColumns = @JoinColumn(name = "package_id"),
        inverseJoinColumns = @JoinColumn(name = "ad_space_id")
    )
    private List<AdSpace> adSpaces;
}
