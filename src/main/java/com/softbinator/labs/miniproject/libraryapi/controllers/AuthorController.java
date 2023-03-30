package com.softbinator.labs.miniproject.libraryapi.controllers;

import com.softbinator.labs.miniproject.libraryapi.dtos.NewAuthorDto;
import com.softbinator.labs.miniproject.libraryapi.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createAuthor (@RequestBody NewAuthorDto newAuthorDto) {
        authorService.createAuthor(newAuthorDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
