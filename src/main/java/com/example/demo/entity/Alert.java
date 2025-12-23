package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to animal event
    @OneToOne
    @JoinColumn(name = "animal_event_id", nullable = false)
    private AnimalEvent animalEvent;

    @Column(nullable = false)
    private boolean acknowledged;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}

