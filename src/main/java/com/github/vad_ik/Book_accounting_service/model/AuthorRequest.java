package com.github.vad_ik.Book_accounting_service.model;

import com.github.vad_ik.Book_accounting_service.database.entity.AuthorEntity;
import com.github.vad_ik.Book_accounting_service.model.exeption.DataIncorrectException;
import lombok.Data;

@Data
//класс для конвертации json от пользователя без id и с проверками
public class AuthorRequest {

    private String name;

    private Integer birth_year;


    public AuthorEntity toEntity() throws DataIncorrectException {

        if (name == null) {
            throw new DataIncorrectException("Name is null");
        }
        if (name.length() > 255) {
            throw new DataIncorrectException("Name is to long (max 255)");
        }

        AuthorEntity entity = new AuthorEntity();
        entity.setName(name);
        if (birth_year != null && birth_year < 0) {
            birth_year = null;
        }
        entity.setBirthYear(birth_year);

        return entity;
    }
}
