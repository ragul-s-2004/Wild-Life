package com.example.demo.repository;

import com.example.demo.entity.AnimalEvent;
import com.example.demo.entity.SpeciesStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalEventRepository extends JpaRepository<AnimalEvent, Long> {


    Page<AnimalEvent> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<AnimalEvent> findBySpeciesStatus(
            SpeciesStatus speciesStatus,
            Pageable pageable
    );

    Page<AnimalEvent> findBySpeciesStatusAndNameContainingIgnoreCase(
            SpeciesStatus speciesStatus,
            String name,
            Pageable pageable
    );

}
