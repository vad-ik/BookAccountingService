
# Book accounting REST API 

выполняющее команды

+ **Добавить книгу** `POST /books`

Тело запроса (JSON):
  ```json
  {
    "title": "Harry Potter and the Chamber of Secrets",
    "author_id": 1,
    "year": 1998,
    "genre": "Фэнтези"
  }
```
+ **Получить список всех книг** `GET /books`
+ **Получить книгу по ID** `GET /books/{id}`
+ **Обновить информацию о книге** `PUT /books/{id}`
+ **Удалить книгу** `DELETE /books/{id}`

  
+ **Добавить автора** `POST /authors`

 Тело запроса (JSON):
  ```json
 {
  "name": "Joanne Rowling",
  "birth_year": "1965"
}
```
или
  ```json
{
  "name": "Joanne Rowling",
}
```
+ **Получить список авторов** `GET /authors?page=0&size=10`

  Параметры:
  -  page - номер страницы (начиная с 0)

  - size - количество элементов на странице

+ **Получить автора по ID** `GET /authors/{id}`


## Технологии
Язык программирования: Java

Фреймворк: Spring Boot

База данных: H2

