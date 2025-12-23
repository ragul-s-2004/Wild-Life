package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AnimalEventRequest {

    private String name;
    private String species;
    private String time;
    private Integer camera;
    private String imageBase64;

}