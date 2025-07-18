package com.github.vad_ik.Book_accounting_service.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class BookEntity {//класс книги для бд
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity authorEntity;

    @Column(nullable = false)
    private int publicationYear;

    @Column(nullable = false)
    private String genre;


    public BookEntity(String title, AuthorEntity authorEntity, int publicationYear, String genre) {
        this.title = title;
        this.authorEntity = authorEntity;
        this.publicationYear = publicationYear;
        this.genre = genre;
    }
}
