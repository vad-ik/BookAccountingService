package com.github.vad_ik.Book_accounting_service.database.repository;

import com.github.vad_ik.Book_accounting_service.database.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
