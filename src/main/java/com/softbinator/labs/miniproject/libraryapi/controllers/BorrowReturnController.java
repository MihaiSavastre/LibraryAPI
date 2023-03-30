package com.softbinator.labs.miniproject.libraryapi.controllers;

import com.softbinator.labs.miniproject.libraryapi.services.BorrowReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class BorrowReturnController {

    private final BorrowReturnService borrowReturnService;

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @RequestParam Long userId) {
        borrowReturnService.borrowBook(bookId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/return/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId, @RequestParam Long userId) {
        borrowReturnService.returnBook(bookId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
