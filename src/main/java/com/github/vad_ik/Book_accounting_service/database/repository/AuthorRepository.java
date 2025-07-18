package com.github.vad_ik.Book_accounting_service.database.repository;

import com.github.vad_ik.Book_accounting_service.database.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
