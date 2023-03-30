package com.softbinator.labs.miniproject.libraryapi.exceptions;

import java.lang.reflect.Type;

public class BookNotFoundException extends BadRequestException {

    private static final String BOOK_NOT_FOUND = "Book not found for id: ";

    public BookNotFoundException(Long id, Type issuedInClass) {
        super(BOOK_NOT_FOUND + id, HIDDEN, issuedInClass);
    }
}
