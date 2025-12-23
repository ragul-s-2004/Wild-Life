package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name= "animal_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpeciesStatus speciesStatus;

    @Column(nullable = false)
    private LocalDateTime detectedAt;

    @Column(nullable = false)
    private Integer cameraNumber;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
