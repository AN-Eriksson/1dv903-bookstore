package me.andreaseriksson.bookstore.service;

import me.andreaseriksson.bookstore.model.CartItem;
import me.andreaseriksson.bookstore.model.Member;
import me.andreaseriksson.bookstore.model.OrderDto;
import me.andreaseriksson.bookstore.model.OrderLineDto;
import me.andreaseriksson.bookstore.repository.OrderDetailsRepository;
import me.andreaseriksson.bookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public OrderDto placeOrder(Member member) {

        List<CartItem> cartItems = cartService.getCartContents(member);

        LocalDate created = LocalDate.now();

        int ono = orderRepository.createOrder(
                member.getUserid(),
                created,
                member.getAddress(),
                member.getCity(),
                member.getZip()
        );

        List<OrderLineDto> lines = new ArrayList<>();
        double total = 0.0;

        for (CartItem item : cartItems) {
            double lineTotal = item.getQty() * item.getBook().getPrice();
            orderDetailsRepository.createOrderDetails(
                    ono,
                    item.getBook().getIsbn(),
                    item.getQty(),
                    (float) lineTotal
            );

            lines.add(new OrderLineDto(item.getBook().getIsbn(), item.getBook().getTitle(), item.getQty(), item.getBook().getPrice(), lineTotal));
            total += lineTotal;
        }

        cartService.clearCart(member);

        String name = member.getFname() + " " + member.getLname();
        String address = member.getAddress() + "<br/>" + member.getCity() + ", " + member.getZip();
        LocalDate deliveryDate = created.plusDays(7);

        return new OrderDto(ono, created, name, address, lines, total, deliveryDate);
    }
}
