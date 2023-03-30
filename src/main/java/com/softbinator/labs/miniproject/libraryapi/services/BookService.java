package com.softbinator.labs.miniproject.libraryapi.services;

import com.softbinator.labs.miniproject.libraryapi.dtos.BookDto;
import com.softbinator.labs.miniproject.libraryapi.dtos.NewBookDto;
import com.softbinator.labs.miniproject.libraryapi.dtos.UpdateBookDto;
import com.softbinator.labs.miniproject.libraryapi.exceptions.AuthorNotFoundException;
import com.softbinator.labs.miniproject.libraryapi.exceptions.BadRequestException;
import com.softbinator.labs.miniproject.libraryapi.exceptions.BookNotFoundException;
import com.softbinator.labs.miniproject.libraryapi.models.Author;
import com.softbinator.labs.miniproject.libraryapi.models.Book;
import com.softbinator.labs.miniproject.libraryapi.repositories.AuthorRepository;
import com.softbinator.labs.miniproject.libraryapi.repositories.BookRepository;
import com.softbinator.labs.miniproject.libraryapi.repositories.BorrowReturnRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BorrowReturnRepository borrowReturnRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository,
                       BorrowReturnRepository borrowReturnRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.borrowReturnRepository = borrowReturnRepository;
    }

    public void createBook(NewBookDto newBookDto) {
        //checks if the book is of valid format
        Book book = createValidBook(newBookDto);

        bookRepository.save(book);
    }
    /**
     * Creates a book if the input fits the requirements:
     * - the author exists
     * - Numerical values are positive (year, number of copies)
     * - available copies are less than total
     *
     * @return a valid Book, throws relevant error otherwise
     */
    private Book createValidBook (NewBookDto newBookDto) {

        //get author by id or throw exception if it doesn't exist
        Author author = authorRepository.findById(newBookDto.getAuthorId())
                .orElseThrow(() -> {
                    throw new AuthorNotFoundException(newBookDto.getAuthorId(), this.getClass());
                });

        //throw relevant errors if there are issues with the request body
        if (bookRepository.existsByTitleAndAuthor(newBookDto.getTitle(), author)) {
            throw new BadRequestException("duplicate book", BadRequestException.DUPLICATE_BOOK, this.getClass());
        }

        validateData(newBookDto);

        //if all is ok
        return Book.builder()
                .title(newBookDto.getTitle())
                .author(author)
                .category(newBookDto.getCategory())
                .year(Year.of(newBookDto.getYear()))
                .totalCopies((newBookDto.getTotalCopies()))
                .availableCopies(newBookDto.getAvailableCopies())
                .build();
    }

    private void validateData(NewBookDto newBookDto) {
        if (newBookDto.getYear() < 0) {
            throw new BadRequestException("negative year", BadRequestException.INVALID_YEAR, this.getClass());
        }
        if (newBookDto.getAvailableCopies() < 0
                || newBookDto.getTotalCopies() < newBookDto.getAvailableCopies()) {
            throw new BadRequestException("invalid data", BadRequestException.INVALID_DATA, this.getClass());
        }
    }

    /**
     * Only updates non-null field of UpdateBookDto
     */
    @Transactional
    public void updateBookDetails(Long id, UpdateBookDto updateBookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {throw new BookNotFoundException(id, this.getClass());} );


        if (updateBookDto.getTitle() != null) bookRepository.updateTitle(id, updateBookDto.getTitle());
        if (updateBookDto.getCategory() != null) bookRepository.updateCategory(id, updateBookDto.getCategory());

        //Try to use the validateData method
        //Default to existing data if nothing is provided
        NewBookDto tempDto = NewBookDto.builder()
                .year(updateBookDto.getYear() == null ? book.getYear().getValue() : updateBookDto.getYear())
                .totalCopies(updateBookDto.getTotalCopies() == null ?
                            book.getTotalCopies() : updateBookDto.getTotalCopies())
                .availableCopies(updateBookDto.getAvailableCopies() == null ?
                        book.getAvailableCopies() : updateBookDto.getAvailableCopies())
                .build();

        //Will throw errors if there are negative values or the number of copies doesn't make sense
        validateData(tempDto);

        bookRepository.updateYear(id, Year.of(tempDto.getYear()));
        bookRepository.updateTotalCopies(id, tempDto.getTotalCopies());
        bookRepository.updateAvailableCopies(id, tempDto.getAvailableCopies());

    }

    public List<BookDto> getBooks(String filterBy) {
        List<BookDto> bookDtos = new ArrayList<>();

        Sort titleSort = Sort.by("title");

        List<Book> books;

        if (filterBy == null || filterBy.isBlank()) {
            books = bookRepository.findAll(titleSort);
        }
        else {
            books = bookRepository.findAllByCategory(filterBy, titleSort);
        }

        books.forEach((b) -> bookDtos.add(BookDto.builder()
                .id(b.getId())
                .author_id(b.getAuthor().getId())
                .title(b.getTitle())
                .category(b.getCategory())
                .year(b.getYear())
                .totalCopies(b.getTotalCopies())
                .availableCopies(b.getAvailableCopies())
                .build()));
        return bookDtos;
    }

    public void deleteBook(Long id) {
        //We'll first delete all the borrow records still active for this book
        //(those already return can stay in the registry, but active ones cannot
        //be completed. Effectively we erase the duty to return deleted books)
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {throw new BookNotFoundException(id, this.getClass());} );

        //delete records
        borrowReturnRepository.findAllByBookAndStatus(book, "BORROWED")
                .forEach((record) -> borrowReturnRepository.deleteById(record.getId()));

        //delete the book
        bookRepository.delete(book);
    }

    @Deprecated
    @Transactional
    public void updateBookDetailsOLD(Long id, UpdateBookDto updateBookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {throw new BookNotFoundException(id, this.getClass());} );


        if (updateBookDto.getTitle() != null) bookRepository.updateTitle(id, updateBookDto.getTitle());
        if (updateBookDto.getCategory() != null) bookRepository.updateCategory(id, updateBookDto.getCategory());

        if (updateBookDto.getYear() != null) {
            if (updateBookDto.getYear() < 0) {
                throw new BadRequestException("negative year", BadRequestException.INVALID_YEAR, this.getClass());
            }
            bookRepository.updateYear(id, Year.of(updateBookDto.getYear()));
        }

        if (updateBookDto.getTotalCopies() != null && updateBookDto.getAvailableCopies() != null) {
            if (updateBookDto.getTotalCopies() >= updateBookDto.getAvailableCopies()
                    && updateBookDto.getAvailableCopies() >= 0) {
                bookRepository.updateTotalCopies(id, updateBookDto.getTotalCopies());
                bookRepository.updateAvailableCopies(id, updateBookDto.getAvailableCopies());
                return;
            }
            else throw new BadRequestException("invalid data", BadRequestException.INVALID_DATA, this.getClass());
        }

        if (updateBookDto.getTotalCopies() != null) {
            if (updateBookDto.getTotalCopies() >= book.getAvailableCopies())
                bookRepository.updateTotalCopies(id, updateBookDto.getTotalCopies());
            else throw new BadRequestException("invalid data", BadRequestException.INVALID_DATA, this.getClass());
        }

        if (updateBookDto.getAvailableCopies() != null) {
            if (updateBookDto.getAvailableCopies() <= book.getTotalCopies()
                    &&updateBookDto.getAvailableCopies() >= 0) {
                bookRepository.updateAvailableCopies(id, updateBookDto.getAvailableCopies());
            }
            else {
                throw new BadRequestException("invalid data", BadRequestException.INVALID_DATA, this.getClass());
            }
        }
    }
}
