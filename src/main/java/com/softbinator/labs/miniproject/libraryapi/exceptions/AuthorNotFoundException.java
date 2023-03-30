package com.softbinator.labs.miniproject.libraryapi.exceptions;

import java.lang.reflect.Type;

public class AuthorNotFoundException extends BadRequestException {
    private static final String BOOK_NOT_FOUND = "Author not found for id: ";

    public AuthorNotFoundException(Long id, Type issuedInClass) {
        super(BOOK_NOT_FOUND + id, HIDDEN, issuedInClass);
    }
}
