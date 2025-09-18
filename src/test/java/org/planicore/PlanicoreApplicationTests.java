package org.planicore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PlanicoreApplicationTests {

    @Test
    void contextLoads() {
        // Тест проверяет, что контекст Spring загружается корректно
    }
}