package com.github.vad_ik.Book_accounting_service.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
@AllArgsConstructor
@RequiredArgsConstructor

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)
    private Author author;

    @Column(nullable = false)
    private int publicationYear;

    @Column(nullable = false)
    private String genre;
}
