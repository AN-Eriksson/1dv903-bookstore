package me.andreaseriksson.bookstore.model;

public record OrderLineDto(String isbn,String title, int qty, double unitPrice, double lineTotal) { }
