package com.github.vad_ik.Book_accounting_service.controller;

import com.github.vad_ik.Book_accounting_service.model.BookRequest;
import com.github.vad_ik.Book_accounting_service.model.BookResponse;
import com.github.vad_ik.Book_accounting_service.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    //Добавить книгу (POST /books)
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookRequest bookRequest) {
        log.debug("POST request came to add a book {}", bookRequest);
        bookService.addBook(bookRequest);
        log.debug("post request successfully completed");
        return ResponseEntity.ok("add book successfully");
    }

    //Получить список всех книг (GET /books)
    @GetMapping
    public List<BookResponse> getAllBooks() {
        log.debug("GET request came to AllBooks");
        List<BookResponse> books = bookService.getAllBook().toList();
        log.debug("received list of books {}", books);
        return books;
    }

    //Получить книгу по ID (GET /books/{id})
    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id) {
        log.debug("get request came to Book num {}", id);
        BookResponse book = bookService.getBook(id);
        log.debug("received books {}", book);
        return book;
    }

    //Обновить информацию о книге (PUT /books/{id})
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        log.debug("PUT request came to update a book {}{}", id, bookRequest);
        bookService.updateBook(id, bookRequest);
        log.debug("PUT request successfully completed");
        return ResponseEntity.ok("updateBook");
    }

    //Удалить книгу (DELETE /books/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        log.debug("delBook {}", id);
        bookService.delBook(id);
        return ResponseEntity.ok("delBook");
    }
}