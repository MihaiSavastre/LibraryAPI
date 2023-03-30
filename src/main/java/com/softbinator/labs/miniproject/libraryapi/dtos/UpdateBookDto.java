package com.softbinator.labs.miniproject.libraryapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class UpdateBookDto {
    private String title;

    private String category;

    private Integer year;

    private Integer totalCopies;

    private Integer availableCopies;
}