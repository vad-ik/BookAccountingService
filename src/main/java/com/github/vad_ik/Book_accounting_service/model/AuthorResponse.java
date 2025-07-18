package com.github.vad_ik.Book_accounting_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.vad_ik.Book_accounting_service.database.entity.AuthorEntity;
import lombok.Data;

@Data
//класс для ответного json
public class AuthorResponse {

    private Long id;

    private String name;

    //не включать null в json
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer birth_year;

    public static AuthorResponse of(AuthorEntity entity) {
        AuthorResponse response = new AuthorResponse();
        response.setId(entity.getId());
        response.setBirth_year(entity.getBirthYear());
        response.setName(entity.getName());
        return response;
    }
}





