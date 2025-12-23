package com.example.demo.controller;

import com.example.demo.dto.AnimalEventRequest;
import com.example.demo.entity.AnimalEvent;
import com.example.demo.entity.SpeciesStatus;
import com.example.demo.repository.AnimalEventRepository;
import com.example.demo.service.AnimalEventService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ai")
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


    @GetMapping("/api/events/{id}")
    public AnimalEvent getEventById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }


    @PostMapping("/events")
    public void receiveEvent(@RequestBody AnimalEventRequest request) {

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
    }
}

