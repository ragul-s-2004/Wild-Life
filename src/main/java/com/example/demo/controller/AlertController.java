package com.example.demo.controller;

import com.example.demo.entity.Alert;
import com.example.demo.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService service;

    public AlertController(AlertService service) {
        this.service = service;
    }

    @GetMapping("/active")
    public List<Alert> getActiveAlerts() {
        return service.getActiveAlerts();
    }

    @PutMapping("/{id}/acknowledge")
    public void acknowledgeAlert(@PathVariable Long id) {
        service.acknowledgeAlert(id);
    }
}
