package me.andreaseriksson.bookstore.model;

public class CartItem {
    private final Book book;
    private final int qty;

    public CartItem(Book book, int qty) {
        this.book = book;
        this.qty = qty;
    }

    public Book getBook() {
        return book;
    }

    public int getQty() {
        return qty;
    }
}