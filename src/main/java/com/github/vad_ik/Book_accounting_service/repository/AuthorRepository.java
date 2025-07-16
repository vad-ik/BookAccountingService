package com.github.vad_ik.Book_accounting_service.repository;

import com.github.vad_ik.Book_accounting_service.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
