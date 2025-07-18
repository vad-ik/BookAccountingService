package com.github.vad_ik.Book_accounting_service.controller;

import com.github.vad_ik.Book_accounting_service.model.AuthorRequest;
import com.github.vad_ik.Book_accounting_service.model.AuthorResponse;
import com.github.vad_ik.Book_accounting_service.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //Добавить автора(POST /authors)
    @PostMapping
    public ResponseEntity<String> addAuthor(@RequestBody AuthorRequest authorRequest) {
        log.debug("POST request came to add a author {}", authorRequest);
        authorService.addAuthor(authorRequest.toEntity());
        log.debug("POST request successfully completed");
        return ResponseEntity.ok("add author successfully");
    }

    //Получить список авторов (пагинация)(GET /authors?page=0&size=10)
    @GetMapping
    public Stream<AuthorResponse> getAllAuthor(@RequestParam int page, @RequestParam int size) {
        log.debug("GET request received to get AllAuthor");
        Page<AuthorResponse> ans = authorService.getAllAuthor(page, size);
        log.debug("received authors: {}", ans.toString());
        return ans.get();
    }

    //Получить автора по ID (GET /authors/{id})
    @GetMapping("/{id}")
    public AuthorResponse getAuthorById(@PathVariable Long id) {
        log.debug("get request received author num {}", id);
        AuthorResponse ans = authorService.getAuthor(id);
        log.debug("received for GET getAuthor: {}", ans.toString());
        return ans;
    }
}