package com.github.vad_ik.Book_accounting_service;

import com.github.vad_ik.Book_accounting_service.model.AuthorRequest;
import com.github.vad_ik.Book_accounting_service.model.AuthorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/cleanup.sql", executionPhase = BEFORE_TEST_METHOD)
class AuthorControllerIntegratedTest {


    @Autowired
    private TestRestTemplate restTemplate;


    private AuthorRequest initAuthor() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setName("tester");
        return authorRequest;
    }

    private final String urlAuthors = "/authors";

    @Test
    void addAuthorTest() {
        AuthorRequest authorRequest = initAuthor();

        ResponseEntity<String> response = this.restTemplate.postForEntity(urlAuthors, authorRequest, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("add author successfully");
    }

    @Test
    void getAuthorTest() {
        AuthorRequest authorRequest = initAuthor();

        this.restTemplate.postForEntity(urlAuthors, authorRequest, String.class);
        ResponseEntity<List<AuthorResponse>> responseAuthor = restTemplate.exchange(
                urlAuthors + "?page=0&size=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );


        Long id = Objects.requireNonNull(responseAuthor.getBody()).getFirst().getId();
        ResponseEntity<AuthorResponse> response = restTemplate.getForEntity(urlAuthors + "/{id}", AuthorResponse.class, id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(check(Objects.requireNonNull(response.getBody()), authorRequest)).isTrue();

    }
    

    public boolean check(AuthorResponse authorResponse, AuthorRequest authorRequest) {

        if (!authorRequest.getName().equals(authorResponse.getName())) return false;
        if ((authorRequest.getBirth_year() == null && authorResponse.getBirth_year() == null)) return true;
        assert authorRequest.getBirth_year() != null;
        return authorRequest.getBirth_year().equals(authorResponse.getBirth_year());
    }
}
