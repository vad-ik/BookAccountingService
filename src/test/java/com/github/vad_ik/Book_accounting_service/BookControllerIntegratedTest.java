package com.github.vad_ik.Book_accounting_service;

import com.github.vad_ik.Book_accounting_service.model.AuthorRequest;
import com.github.vad_ik.Book_accounting_service.model.AuthorResponse;
import com.github.vad_ik.Book_accounting_service.model.BookRequest;
import com.github.vad_ik.Book_accounting_service.model.BookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/cleanup.sql", executionPhase = BEFORE_TEST_METHOD)
class BookControllerIntegratedTest {


    @Autowired
    private TestRestTemplate restTemplate;

    private BookRequest initBook() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("test");
        bookRequest.setYear(2000);
        bookRequest.setGenre("test");
        bookRequest.setAuthor_id(1L);
        return bookRequest;
    }

    private AuthorRequest initAuthor() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setName("tester");
        return authorRequest;
    }

    private final String urlBooks = "/books";
    private final String urlAuthors = "/authors";

    @Test
    void addBookExceptionTest() {
        BookRequest bookRequest = initBook();


        ResponseEntity<String> response = this.restTemplate.postForEntity(urlBooks, bookRequest, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("{\"statusCode\":400,\"message\":\"author with id " + bookRequest.getAuthor_id() + " not found\"}");
    }

    @Test
    void addBookTest() {
        AuthorRequest authorRequest = initAuthor();
        BookRequest bookRequest = initBook();

        this.restTemplate.postForEntity(urlAuthors, authorRequest, String.class);
        ResponseEntity<List<AuthorResponse>> responseAuthor = restTemplate.exchange(
                urlAuthors+"?page=0&size=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        bookRequest.setAuthor_id(Objects.requireNonNull(responseAuthor.getBody()).getFirst().getId());
        ResponseEntity<String> response = this.restTemplate.postForEntity(urlBooks, bookRequest, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("add book successfully");
    }

    @Test
    void getBookTest() {
        AuthorRequest authorRequest = initAuthor();
        BookRequest bookRequest = initBook();

        this.restTemplate.postForEntity(urlAuthors, authorRequest, String.class);
        ResponseEntity<List<AuthorResponse>> responseAuthor = restTemplate.exchange(
                urlAuthors+"?page=0&size=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        bookRequest.setAuthor_id(Objects.requireNonNull(responseAuthor.getBody()).getFirst().getId());
        this.restTemplate.postForEntity(urlBooks, bookRequest, String.class);

        ResponseEntity<List<BookResponse>> responseAll = restTemplate.exchange(
                urlBooks,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        Long id = Objects.requireNonNull(responseAll.getBody()).getFirst().getId();
        ResponseEntity<BookResponse> response = restTemplate.getForEntity(urlBooks + "/{id}", BookResponse.class, id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(check(Objects.requireNonNull(response.getBody()), bookRequest)).isTrue();

    }

    @Test
    void delBookTest() {
        AuthorRequest authorRequest = initAuthor();
        BookRequest bookRequest = initBook();

        this.restTemplate.postForEntity(urlAuthors, authorRequest, String.class);
        ResponseEntity<List<AuthorResponse>> responseAuthor = restTemplate.exchange(
                urlAuthors+"?page=0&size=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        bookRequest.setAuthor_id(Objects.requireNonNull(responseAuthor.getBody()).getFirst().getId());
        this.restTemplate.postForEntity(urlBooks, bookRequest, String.class);

        ResponseEntity<List<BookResponse>> responseAll = restTemplate.exchange(
                urlBooks,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        Long id = Objects.requireNonNull(responseAll.getBody()).getFirst().getId();
        restTemplate.delete(urlBooks + "/"+id );

        responseAll = restTemplate.exchange(
                urlBooks,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(responseAll.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseAll.getBody()).isEmpty()).isTrue();

    }

    @Test
    void updateBookTest() {
        AuthorRequest authorRequest = initAuthor();
        BookRequest bookRequest = initBook();

        this.restTemplate.postForEntity(urlAuthors, authorRequest, String.class);
        ResponseEntity<List<AuthorResponse>> responseAuthor = restTemplate.exchange(
                urlAuthors+"?page=0&size=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        bookRequest.setAuthor_id(Objects.requireNonNull(responseAuthor.getBody()).getFirst().getId());
        this.restTemplate.postForEntity(urlBooks, bookRequest, String.class);

        bookRequest.setTitle("updateTest");
        ResponseEntity<List<BookResponse>> responseAll = restTemplate.exchange(
                urlBooks,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        Long id = Objects.requireNonNull(responseAll.getBody()).getFirst().getId();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BookRequest> requestEntity = new HttpEntity<>(bookRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                urlBooks+"/"+id,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("updateBook");


        ResponseEntity<BookResponse> responseBook = restTemplate.getForEntity(urlBooks + "/{id}", BookResponse.class, id);

        assertThat((Objects.requireNonNull(responseBook.getBody())).getTitle()).isEqualTo("updateTest");
    }

    public boolean check(BookResponse bookResponse, BookRequest bookRequest) {

        return bookResponse.getTitle().equals(bookRequest.getTitle()) &&
                bookResponse.getGenre().equals(bookRequest.getGenre()) &&
                bookResponse.getYear() == (bookRequest.getYear()) &&
                bookResponse.getAuthorEntity().getId().equals(bookRequest.getAuthor_id());
    }
}
