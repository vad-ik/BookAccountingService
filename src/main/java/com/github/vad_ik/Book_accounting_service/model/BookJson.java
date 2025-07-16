package com.github.vad_ik.Book_accounting_service.model;

import lombok.Data;

@Data

public class BookJson {//нужен, чтобы не передавать в json автора целиком
    private Long id;

    private String title;

    private Long author_id;

    private int year;

    private String genre;
}
