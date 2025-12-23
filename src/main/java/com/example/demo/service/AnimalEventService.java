package com.example.demo.service;

import com.example.demo.entity.AnimalEvent;
import com.example.demo.entity.SpeciesStatus;
import com.example.demo.repository.AnimalEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class AnimalEventService {

    private final AnimalEventRepository repository;
    private final AlertService alertService;

    public AnimalEventService(
            AnimalEventRepository repository,
            AlertService alertService
    ) {
        this.repository = repository;
        this.alertService = alertService;
    }

    public AnimalEvent saveEvent(
            String name,
            SpeciesStatus speciesStatus,
            LocalDateTime detectedAt,
            Integer cameraNumber,
            String imageBase64
    ){

        String imagePath = saveImage(imageBase64);

        AnimalEvent event =AnimalEvent.builder()
                .name(name)
                .speciesStatus(speciesStatus)
                .detectedAt(detectedAt)
                .cameraNumber(cameraNumber)
                .imagePath(imagePath)
                .createdAt(LocalDateTime.now())
                .build();

        AnimalEvent savedEvent = repository.save(event);

        if (speciesStatus == SpeciesStatus.ENDANGERED) {
            alertService.createAlert(event);
        }


        return savedEvent;
    }

    public Page<AnimalEvent> getAllEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }


    public Page<AnimalEvent> searchEvents(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }


    public Page<AnimalEvent> getEndangeredEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findBySpeciesStatus(
                SpeciesStatus.ENDANGERED,
                pageable
        );
    }


    public Page<AnimalEvent> searchEndangeredEvents(
            String name,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findBySpeciesStatusAndNameContainingIgnoreCase(
                SpeciesStatus.ENDANGERED,
                name,
                pageable
        );
    }

    private String saveImage(String base64Image) {

        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            String folderPath = "images";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName = "animal_" + System.currentTimeMillis() + ".jpg";
            File imageFile = new File(folderPath + File.separator + fileName);

            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                fos.write(imageBytes);
            }

            return "images/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
