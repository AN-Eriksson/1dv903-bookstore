package me.andreaseriksson.bookstore.model;

import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        int ono,
        LocalDate created,
        String name,
        String address,
        List<OrderLineDto> lines,
        double total,
        LocalDate deliveryDate
) {
}