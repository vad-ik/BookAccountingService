package com.github.vad_ik.Book_accounting_service.repository;

import com.github.vad_ik.Book_accounting_service.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {
}
