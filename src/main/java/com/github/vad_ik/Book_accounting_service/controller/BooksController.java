package com.github.vad_ik.Book_accounting_service.controller;

import com.github.vad_ik.Book_accounting_service.model.Book;
import com.github.vad_ik.Book_accounting_service.model.BookJson;
import com.github.vad_ik.Book_accounting_service.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public String addBook(@RequestBody BookJson bookJson) {
        log.info("POST request came to add a book {}", bookJson);
        try {
            bookService.addBook(bookJson);
        } catch (DataIntegrityViolationException e) {//id автора не найден
            log.info("during execution of post request author {} Not Found or invalid JSON", bookJson.getAuthor_id());
            return "Author Not Found or invalid JSON";
        }
        log.info("post request successfully completed");
        return "addBook";
    }

    //Получить список всех книг (GET /books)
    @GetMapping
    public Iterable<Book> getAllBooks() {
        log.info("GET request came to AllBooks");
        Iterable<Book> books= bookService.getAllBook();
        log.info("received list of books {}", books);
        return books;
    }

    //Получить книгу по ID (GET /books/{id})
    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        log.info("get request came to Book num {}", id);
        Optional<Book> book= bookService.getBook(id);
        log.info("received books {}", book);
        return book;
    }

    //Обновить информацию о книге (PUT /books/{id})
    @PutMapping("/{id}")
    public String updateBook(@PathVariable Long id, @RequestBody BookJson bookJson) {
        log.info("PUT request came to update a book {}{}", id, bookJson);
        try {
            bookService.updateBook(id, bookJson);
        } catch (DataIntegrityViolationException e) {//id автора не найден или не все обязательные поля заполнены
            log.info("during execution of post request author {} Not Found or invalid JSON ", bookJson.getAuthor_id());
            return "Author Not Found or invalid JSON";
        }catch (ObjectOptimisticLockingFailureException e){//id книги не найден
            log.info("during execution of PUT request book {} Not Found ", id);
            return "updated book not found";
        }
        log.info("PUT request successfully completed");
        return "updateBook";
    }

    //Удалить книгу (DELETE /books/{id})
    @DeleteMapping("/{id}")
    public String delBook(@PathVariable Long id) {
        log.info("delBook {}", id);
        bookService.delBook(id);
        return "delBook";
    }
}