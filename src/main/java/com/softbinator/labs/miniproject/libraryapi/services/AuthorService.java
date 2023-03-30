package com.softbinator.labs.miniproject.libraryapi.services;

import com.softbinator.labs.miniproject.libraryapi.dtos.NewAuthorDto;
import com.softbinator.labs.miniproject.libraryapi.exceptions.BadRequestException;
import com.softbinator.labs.miniproject.libraryapi.models.Author;
import com.softbinator.labs.miniproject.libraryapi.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void createAuthor (NewAuthorDto newAuthorDto) {
        if ( authorRepository
                .findAuthorByFullName(newAuthorDto.getFirstName(), newAuthorDto.getLastName())
                .isPresent() ) {
            throw new BadRequestException("Duplicate author", BadRequestException.AUTHOR_EXISTS, this.getClass());
        }
        Author author =
                Author.builder().firstName(newAuthorDto.getFirstName())
                .lastName(newAuthorDto.getLastName())
                .build();

        authorRepository.save(author);
    }
}
