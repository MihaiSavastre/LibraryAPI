package com.softbinator.labs.miniproject.libraryapi.controllers;

import com.softbinator.labs.miniproject.libraryapi.dtos.NewBookDto;
import com.softbinator.labs.miniproject.libraryapi.dtos.UpdateBookDto;
import com.softbinator.labs.miniproject.libraryapi.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<?> getBooks(@RequestParam(defaultValue = "") String filterBy) {
        return new ResponseEntity<>(bookService.getBooks(filterBy), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createBook(NewBookDto newBookDto) {
        bookService.createBook(newBookDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBookDetails(@PathVariable Long id, UpdateBookDto updateBookDto) {
        bookService.updateBookDetails(id, updateBookDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
