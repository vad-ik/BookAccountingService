package com.github.vad_ik.Book_accounting_service.model;

import com.github.vad_ik.Book_accounting_service.database.entity.AuthorEntity;
import com.github.vad_ik.Book_accounting_service.database.entity.BookEntity;
import com.github.vad_ik.Book_accounting_service.model.exeption.DataIncorrectException;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BookRequest {

    private String title;

    private Long author_id;

    private Integer year;

    private String genre;

    public BookEntity toEntity(AuthorEntity authorEntity) throws DataIncorrectException {
        if (title == null || title.isEmpty()) {
            throw new DataIncorrectException("title is null");
        }
        if (author_id == null) {
            throw new DataIncorrectException("author_id is null");
        }
        if (year == null) {
            throw new DataIncorrectException("year is null");
        } else if (authorEntity.getBirthYear() != null && year < authorEntity.getBirthYear()) {
            throw new DataIncorrectException("written before birth");
        }
        if (genre == null) {
            throw new DataIncorrectException("genre is null");
        }
        if (genre.length() > 255) {
            throw new DataIncorrectException("genre is to long (max 255)");
        }
        if (title.length() > 255) {
            throw new DataIncorrectException("title is to long (max 255)");
        }
        return new BookEntity(title, authorEntity, year, genre);
    }
}
