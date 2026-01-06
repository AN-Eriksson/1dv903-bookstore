# Book store
A simple bookstore web application built with Java Spring Boot. 
The app connects to a MySQL database named book_store (configured in src/main/resources/application.properties) and provides member registration/login, subject/author/title search with pagination, a shopping cart, and checkout that creates orders and order details and shows an invoice.


# Database connection (application.properties) — MySQL

This project reads the datasource URL, username and password from `src/main/resources/application.properties`.

Example `src/main/resources/application.properties` snippet (MySQL):

`spring.datasource.url=jdbc:mysql://127.0.0.1:3306/book_store?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC`

`spring.datasource.username=springuser`

`spring.datasource.password=bookpass`

`spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver`

The port on which the application is served is set in `src/main/resources/application.properties`.

`server.port=3030`
