package com.github.vad_ik.Book_accounting_service.model;

import com.github.vad_ik.Book_accounting_service.database.entity.AuthorEntity;
import com.github.vad_ik.Book_accounting_service.database.entity.BookEntity;
import lombok.Data;

@Data
public class BookResponse {
    private Long id;

    private String title;

    private AuthorEntity authorEntity;

    private int year;

    private String genre;

    public static BookResponse of(BookEntity entity) {
        BookResponse response = new BookResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setAuthorEntity(entity.getAuthorEntity());
        response.setYear(entity.getPublicationYear());
        response.setGenre(entity.getGenre());
        return response;
    }
}
