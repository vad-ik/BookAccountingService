package com.github.vad_ik.Book_accounting_service.service;

import com.github.vad_ik.Book_accounting_service.model.Author;
import com.github.vad_ik.Book_accounting_service.model.Book;
import com.github.vad_ik.Book_accounting_service.model.BookJson;
import com.github.vad_ik.Book_accounting_service.repository.AuthorRepository;
import com.github.vad_ik.Book_accounting_service.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Iterable<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Transactional
    public Optional<Book> getBook(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Page<Author> getAllAuthor(int page, int size) {
        return authorRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public Optional<Author> getAuthor(Long id) {
        return authorRepository.findById(id);
    }


    @Transactional
    public void addBook(BookJson bookJson) {
        Book book = transformBook(bookJson);
        bookRepository.save(book);

    }

    @Transactional
    public void updateBook(Long id, BookJson bookJson) {
        Book book = transformBook(bookJson);
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }


    Book transformBook(BookJson bookJson) {
        return new Book(bookJson.getTitle(),
                authorRepository.getReferenceById(bookJson.getAuthor_id()),
                bookJson.getYear(),
                bookJson.getGenre()
        );
    }
}
