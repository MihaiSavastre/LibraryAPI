package com.softbinator.labs.miniproject.libraryapi.dtos;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewBookDto {
    private Long authorId;
    private String title;
    private String category;
    private int year;
    private int totalCopies;
    private int availableCopies;
}
