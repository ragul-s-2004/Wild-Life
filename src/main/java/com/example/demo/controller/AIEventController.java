package com.example.demo.controller;

import com.example.demo.dto.AnimalEventRequest;
import com.example.demo.entity.AnimalEvent;
import com.example.demo.entity.SpeciesStatus;
import com.example.demo.repository.AnimalEventRepository;
import com.example.demo.service.AnimalEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/events")
@CrossOrigin
public class AIEventController {

    private final AnimalEventService animalEventService;
    private final AnimalEventRepository repository;

    public AIEventController(
            AnimalEventService animalEventService,
            AnimalEventRepository repository
    ) {
        this.animalEventService = animalEventService;
        this.repository = repository;
    }

    // 🔥 GET event by ID (for React View page)
    @GetMapping("/{id}")
    public AnimalEvent getEventById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    // 🔥 POST from AI dummy / real server
    @PostMapping
    public ResponseEntity<?> receiveEvent(
            @RequestBody AnimalEventRequest request
    ) {
        System.out.println("🔥 POST /api/events HIT");

        SpeciesStatus status =
                request.getSpecies().equalsIgnoreCase("endangered")
                        ? SpeciesStatus.ENDANGERED
                        : SpeciesStatus.NOT_ENDANGERED;

        LocalDateTime detectedAt = LocalDateTime.parse(request.getTime());

        animalEventService.saveEvent(
                request.getName(),
                status,
                detectedAt,
                request.getCamera(),
                request.getImageBase64()
        );

        return ResponseEntity.ok().build();
    }
}
