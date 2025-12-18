package me.andreaseriksson.bookstore.service;

import me.andreaseriksson.bookstore.model.CartItem;
import me.andreaseriksson.bookstore.model.Member;
import me.andreaseriksson.bookstore.repository.CartRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartItem> getCartContents(Member member) {
        return cartRepository.getCartContents(member);
    }

    public void save(Member member, String isbn, int qty) {
        cartRepository.save(member, isbn, qty);
    }

    public double calculateTotal(List<CartItem> items) {
        double total = 0;
        for (CartItem item : items) {
            total += item.getQty() * item.getBook().getPrice();
        }
        return total;
    }
}
