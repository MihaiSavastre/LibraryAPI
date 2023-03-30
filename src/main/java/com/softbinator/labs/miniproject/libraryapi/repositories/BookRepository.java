package com.softbinator.labs.miniproject.libraryapi.repositories;

import com.softbinator.labs.miniproject.libraryapi.models.Author;
import com.softbinator.labs.miniproject.libraryapi.models.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitleAndAuthor(String title, Author author);

    List<Book> findAllByCategory(String category, Sort sort);

    @Modifying
    @Query("UPDATE books SET title=:newTitle WHERE id=:id")
    void updateTitle(Long id, String newTitle);

    @Modifying
    @Query("UPDATE books SET category=:newCategory WHERE id=:id")
    void updateCategory(Long id, String newCategory);

    @Modifying
    @Query("UPDATE books SET year=:newYear WHERE id=:id")
    void updateYear(Long id, Year newYear);

    @Modifying
    @Query("UPDATE books SET totalCopies=:newTotalCopies WHERE id=:id")
    void updateTotalCopies(Long id, int newTotalCopies);

    @Modifying
    @Query("UPDATE books SET availableCopies=:newAvailableCopies WHERE id=:id")
    void updateAvailableCopies(Long id, int newAvailableCopies);

}
