package com.softbinator.labs.miniproject.libraryapi.repositories;

import com.softbinator.labs.miniproject.libraryapi.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a from authors a WHERE a.firstName=:firstName and a.lastName=:lastName")
    Optional<Author> findAuthorByFullName(String firstName, String lastName);
}
