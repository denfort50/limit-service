package ru.dkalchenko.limit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dkalchenko.limit.dto.Request;
import ru.dkalchenko.limit.dto.Response;
import ru.dkalchenko.limit.service.LimitService;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final LimitService limitService;

    /**
     * Снизить сумму лимита на указанную в запросе.
     */
    @PostMapping("/reduce")
    public Response reduceLimit(@Valid @RequestBody Request request) {
        return limitService.reduceLimitSum(request);
    }

    /**
     * Изменить стандартное значение лимита на указанное в запросе.
     */
    @PostMapping("/default")
    public Response changeDefaultLimit(@Valid @RequestBody Request request) {
        return limitService.changeDefaultLimitSum(request.sum());
    }

}
