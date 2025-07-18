package com.github.vad_ik.Book_accounting_service.service;

import com.github.vad_ik.Book_accounting_service.database.entity.AuthorEntity;
import com.github.vad_ik.Book_accounting_service.database.repository.AuthorRepository;
import com.github.vad_ik.Book_accounting_service.model.AuthorResponse;
import com.github.vad_ik.Book_accounting_service.model.exeption.DataIncorrectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Page<AuthorResponse> getAllAuthor(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new DataIncorrectException("page<0 or size<=0");
        }
        return authorRepository.findAll(PageRequest.of(page, size)).map(AuthorResponse::of);
    }

    public AuthorResponse getAuthor(Long id) {

        Optional<AuthorEntity> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            throw new DataIncorrectException("author with id" + id + " not found");
        }
        return AuthorResponse.of(author.get());
    }

    public void addAuthor(AuthorEntity authorEntity) {
        authorRepository.save(authorEntity);
    }
}
