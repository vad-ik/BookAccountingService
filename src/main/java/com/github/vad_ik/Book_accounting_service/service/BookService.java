package com.github.vad_ik.Book_accounting_service.service;

import com.github.vad_ik.Book_accounting_service.database.entity.AuthorEntity;
import com.github.vad_ik.Book_accounting_service.database.entity.BookEntity;
import com.github.vad_ik.Book_accounting_service.database.repository.AuthorRepository;
import com.github.vad_ik.Book_accounting_service.database.repository.BookRepository;
import com.github.vad_ik.Book_accounting_service.model.BookRequest;
import com.github.vad_ik.Book_accounting_service.model.BookResponse;
import com.github.vad_ik.Book_accounting_service.model.exeption.DataIncorrectException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Stream<BookResponse> getAllBook() {
        return (bookRepository.findAll()).stream().map(BookResponse::of);
    }

    public BookResponse getBook(Long id) {
        Optional<BookEntity> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new DataIncorrectException("book with id " + id + " not found");
        }
        return BookResponse.of(book.get());
    }

    public void addBook(BookRequest bookRequest) {
        BookEntity bookEntity = transformBook(bookRequest);
        bookRepository.save(bookEntity);
    }

    public void updateBook(Long id, BookRequest bookRequest) {
        BookEntity bookEntity = transformBook(bookRequest);
        bookEntity.setId(id);
        try {
            bookRepository.save(bookEntity);
        } catch (ObjectOptimisticLockingFailureException e) {//id книги не найден
            throw new DataIncorrectException("during execution of PUT request book with id " + id + " Not Found ");
        }
    }

    public void delBook(Long id) {
        bookRepository.deleteById(id);
    }

    BookEntity transformBook(BookRequest bookRequest) {
        AuthorEntity authorEntity = authorRepository.findById(bookRequest.getAuthor_id())
                .orElseThrow(() -> new DataIncorrectException("author with id " + bookRequest.getAuthor_id() + " not found"));
        return bookRequest.toEntity(authorEntity);
    }
}
