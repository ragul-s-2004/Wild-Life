package com.example.demo.service;


import com.example.demo.entity.Alert;
import com.example.demo.entity.AnimalEvent;
import com.example.demo.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository repository;

    public AlertService(AlertRepository repository) {
        this.repository = repository;
    }

    public void createAlert(AnimalEvent event) {
        Alert alert = Alert.builder()
                .animalEvent(event)
                .acknowledged(false)
                .createdAt(LocalDateTime.now())
                .build();

        alert.setAcknowledged(false);

        repository.save(alert);
    }

    public List<Alert> getActiveAlerts() {
        return repository.findByAcknowledgedFalse();
    }

    public void acknowledgeAlert(Long alertId) {
        Alert alert = repository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setAcknowledged(true);
        repository.save(alert);
    }
}
