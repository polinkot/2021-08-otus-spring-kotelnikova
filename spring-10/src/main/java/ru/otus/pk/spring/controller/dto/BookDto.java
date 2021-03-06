package ru.otus.pk.spring.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookDto {

    private Long id;

    private String name;

    private Long authorId;

    private Long genreId;
}
