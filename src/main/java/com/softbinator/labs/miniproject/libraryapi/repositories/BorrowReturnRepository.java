package com.softbinator.labs.miniproject.libraryapi.repositories;

import com.softbinator.labs.miniproject.libraryapi.models.Book;
import com.softbinator.labs.miniproject.libraryapi.models.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BorrowReturnRepository extends JpaRepository<BorrowRecord, Long> {
    boolean existsBorrowRecordByBookAndUserIdAndStatus(Book book, Long userId, String status);

    Optional<BorrowRecord> findByBookAndUserIdAndStatus (Book book, Long userId, String status);

    List<BorrowRecord> findAllByBookAndStatus(Book book, String status);

    @Modifying
    @Query("UPDATE BorrowRecord SET status=:newStatus WHERE id=:id")
    void updateStatus(Long id, String newStatus);
}
