package ru.otus.pk.spring.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AdoptionDto {

    private Long id;

    private Long animalId;

    private Long ownerId;
}
