package ru.dkalchenko.limit.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.dkalchenko.limit.dto.Request;
import ru.dkalchenko.limit.entity.Limit;
import ru.dkalchenko.limit.exception.LimitSizeException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LimitServiceTest {

    @Autowired
    private LimitService limitService;

    @Test
    @Transactional
    public void whenReduceLimitSumThenSuccess() {
        Request request = new Request(1L, 200.0);
        limitService.reduceLimitSum(request);
        Limit limit = limitService.findById(request.id());
        assertThat(limit.getSum()).isEqualTo(9_800);
    }

    @Test
    public void whenReduceLimitSumThenFail() {
        Request request = new Request(1L, 20_000.0);
        assertThrows(LimitSizeException.class, () -> limitService.reduceLimitSum(request),
                "Указанная сумма превышает лимит");
    }

}
