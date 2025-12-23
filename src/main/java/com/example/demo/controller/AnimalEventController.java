package com.example.demo.controller;

import com.example.demo.entity.AnimalEvent;
import com.example.demo.repository.AnimalEventRepository;
import com.example.demo.service.AnimalEventService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class AnimalEventController {

    private final AnimalEventService service;
    private final AnimalEventRepository repository;

    public AnimalEventController(AnimalEventService service, AnimalEventRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    public Page<AnimalEvent> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getAllEvents(page, size);
    }

    @GetMapping("/search")
    public Page<AnimalEvent> searchEvents(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.searchEvents(name, page, size);
    }

    @GetMapping("/endangered")
    public Page<AnimalEvent> getEndangeredEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getEndangeredEvents(page, size);
    }

    @GetMapping("/endangered/search")
    public Page<AnimalEvent> searchEndangeredEvents(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.searchEndangeredEvents(name, page, size);
    }

    @GetMapping("/{id}")
    public AnimalEvent getEventById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }
}
