package com.softbinator.labs.miniproject.libraryapi.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.Year;

@Data
@Builder
public class BookDto {
    private Long id;
    private Long author_id;
    private String title;
    private String category;
    private Year year;
    private int totalCopies;
    private int availableCopies;
}
