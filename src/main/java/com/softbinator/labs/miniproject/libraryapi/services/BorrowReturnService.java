package com.softbinator.labs.miniproject.libraryapi.services;

import com.softbinator.labs.miniproject.libraryapi.exceptions.BadRequestException;
import com.softbinator.labs.miniproject.libraryapi.exceptions.BookNotFoundException;
import com.softbinator.labs.miniproject.libraryapi.models.Book;
import com.softbinator.labs.miniproject.libraryapi.models.BorrowRecord;
import com.softbinator.labs.miniproject.libraryapi.repositories.BookRepository;
import com.softbinator.labs.miniproject.libraryapi.repositories.BorrowReturnRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowReturnService {

    private final BookRepository bookRepository;
    private final BorrowReturnRepository borrowReturnRepository;

    @Transactional
    public void borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow( () -> {throw new BookNotFoundException(bookId, this.getClass());});

        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("no more copies", BadRequestException.BOOK_UNAVAILABLE, this.getClass());
        }

        if (borrowReturnRepository.existsBorrowRecordByBookAndUserIdAndStatus(book, userId, "BORROWED")) {
            throw new BadRequestException("duplicate borrow", BadRequestException.BOOK_BORROWED, this.getClass());
        }

        //if all is good, subtract 1 from available copies and save the entry to the table
        bookRepository.updateAvailableCopies(bookId, book.getAvailableCopies() - 1);

        BorrowRecord borrowed = BorrowRecord.builder().book(book).userId(userId).status("BORROWED").build();
        borrowReturnRepository.save(borrowed);

    }

    @Transactional
    public void returnBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow( () -> {throw new BookNotFoundException(bookId, this.getClass());});

        Long borrowRecordId =
                borrowReturnRepository.findByBookAndUserIdAndStatus(book, userId, "BORROWED")
                .orElseThrow(() -> {
                    throw new BadRequestException("not borrowed", BadRequestException.BOOK_NOT_BORROWED,
                            this.getClass());
                })
                .getId();

        //book can be returned
        bookRepository.updateAvailableCopies(bookId, book.getAvailableCopies() + 1);
        borrowReturnRepository.updateStatus(borrowRecordId, "RETURNED");
    }
}
