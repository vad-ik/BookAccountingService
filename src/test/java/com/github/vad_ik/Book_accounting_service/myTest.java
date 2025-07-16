package com.github.vad_ik.Book_accounting_service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class myTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i <100 ; i++) {
            add();
        }
        get();
    }
static void add() throws IOException, InterruptedException {
    String json = "{\"name\":\"Олег"+(new Random()).nextInt()+"\"}";
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/authors"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

    HttpResponse<String> response = client.send(
            request, HttpResponse.BodyHandlers.ofString()
    );

    System.out.println(response.body());
}

static void get() throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/authors?page=1&size=10"))
            .GET()
            .build();

    HttpResponse<String> response = client.send(
            request, HttpResponse.BodyHandlers.ofString()
    );

    System.out.println(response.body());
}

}
