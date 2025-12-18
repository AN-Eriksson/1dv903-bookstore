package me.andreaseriksson.bookstore.service;

import me.andreaseriksson.bookstore.model.CartItem;
import me.andreaseriksson.bookstore.model.Member;
import me.andreaseriksson.bookstore.repository.OrderDetailsRepository;
import me.andreaseriksson.bookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CheckoutService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final CartService cartService;

    public CheckoutService(OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.cartService = cartService;
    }

    public int placeOrder(Member member) {

        List<CartItem> cartItems = cartService.getCartContents(member);

        int ono = orderRepository.createOrder(
                member.getUserid(),
                LocalDate.now(),
                member.getAddress(),
                member.getCity(),
                member.getZip()
        );

        for (CartItem item : cartItems) {
            orderDetailsRepository.createOrderDetails(
                    ono,
                    item.getBook().getIsbn(),
                    item.getQty(),
                    (float) (item.getQty() * item.getBook().getPrice())
            );
        }

        return ono;
    }
}
