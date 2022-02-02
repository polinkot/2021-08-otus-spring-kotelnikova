package ru.otus.pk.spring.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.pk.spring.service.BookService;

@RequiredArgsConstructor
@Component
public class BookCntHealthIndicator implements HealthIndicator {

    private final BookService bookService;

    @Override
    public Health health() {
        boolean noBooks = bookService.count() == 0;
        return noBooks ?
                Health.down().withDetail("message", "No books!!!").build() :
                Health.up().withDetail("message", "There are some books in the library!").build();
    }
}
