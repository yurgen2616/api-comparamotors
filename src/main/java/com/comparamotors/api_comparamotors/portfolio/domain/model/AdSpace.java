package com.comparamotors.api_comparamotors.portfolio.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ad_spaces")
public class AdSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "monthly_cost", nullable = false)
    private BigDecimal monthlyCost;
}