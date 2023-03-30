package com.softbinator.labs.miniproject.libraryapi.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BorrowReturnBookDto {
    private Long userId;
    private Long bookId;

}
