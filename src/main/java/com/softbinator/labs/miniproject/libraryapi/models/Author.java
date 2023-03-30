package com.softbinator.labs.miniproject.libraryapi.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity (name = "authors")
@Table (name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nonnull
    @Column
    private String firstName;

    @Nonnull
    @Column
    private String lastName;
}
