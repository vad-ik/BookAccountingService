package com.github.vad_ik.Book_accounting_service.controller;

import com.github.vad_ik.Book_accounting_service.model.Author;
import com.github.vad_ik.Book_accounting_service.model.Book;
import com.github.vad_ik.Book_accounting_service.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final BookService bookService;

    public AuthorController(BookService bookService) {
        this.bookService = bookService;
    }

    //Добавить автора(POST /authors)
    @PostMapping
    public String addAuthor(@RequestBody Author author, BindingResult result) {


        log.info("POST request came to add a author {}",author);
        try {
            bookService.addAuthor(author);
        }catch (DataIntegrityViolationException e){
            log.info("POST request Error invalid JSON, required fields are not filled");
            return "invalid JSON, required fields are not filled";
        }
        log.info("POST request successfully completed");
        return "addAuthor";
    }
    //Получить список авторов (пагинация)(GET /authors?page=0&size=10)
    @GetMapping
    public Stream<Author> getAllAuthor(@RequestParam int page, @RequestParam int size) {
        log.info("GET request received to get AllAuthor");
        Page<Author> ans = bookService.getAllAuthor(page, size);
        log.info("received authors: {}",ans.toString());
        return ans.get();
    }
    //Получить автора по ID (GET /authors/{id})
    @GetMapping("/{id}")
    public Optional<Author> getAuthorById(@PathVariable Long id){
        log.info("get request received author num {}",id);
        Optional<Author> ans= bookService.getAuthor(id);
        log.info("received for GET getAuthor: {}", ans.toString());
        return ans;
    }

}