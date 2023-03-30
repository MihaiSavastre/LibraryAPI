package com.softbinator.labs.miniproject.libraryapi.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BorrowRecord")
@Table(name = "borrow_record")
@SQLDelete(sql = "UPDATE borrow_record SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column (name = "user_id")
    private Long userId;

    @Nonnull
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Nonnull
    @Column
    private String status;

    @Column //to see in DB tool
    private boolean deleted = Boolean.FALSE;
}
