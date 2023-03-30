package com.softbinator.labs.miniproject.libraryapi.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.Year;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "books")
@Table(name = "books")
@SQLDelete(sql = "UPDATE books SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;


    @Nonnull
    private String title;

    private String category;

    @Nonnull
    private Year year;

    @Nonnull
    @Column (name = "total_copies")
    private int totalCopies;

    @Nonnull
    @Column (name = "available_copies")
    private int availableCopies;

    @Column //in order to see changes in DB tool (Heidi, Datagrip)
    private boolean deleted = Boolean.FALSE;

}
