package ru.dkalchenko.limit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dkalchenko.limit.dto.Request;
import ru.dkalchenko.limit.dto.Response;
import ru.dkalchenko.limit.entity.Limit;
import ru.dkalchenko.limit.exception.LimitSizeException;
import ru.dkalchenko.limit.repository.LimitRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LimitService {

    private double defaultBalance = 10_000.0;

    private final LimitRepository limitRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void setDefaultLimit() {
        List<Limit> limits = limitRepository.findAll();
        limits.forEach(limit -> limit.setSum(defaultBalance));
    }

    @Transactional
    public Response reduceLimitSum(Request request) {
        Limit limit = limitRepository.findById(request.id())
                .orElseGet(() -> limitRepository.save(new Limit(defaultBalance)));
        Double sum = limit.getSum();
        if (sum < request.sum()) {
            throw new LimitSizeException("Указанная сумма превышает лимит", HttpStatus.CONFLICT);
        } else {
            limit.setSum(sum - request.sum());
            return new Response("Лимит уменьшен на сумму: " + request.sum());
        }
    }

    public Response changeDefaultLimitSum(Double sum) {
        defaultBalance = sum;
        return new Response("Стандартное значение лимита успешно изменено");
    }

    public Limit findById(Long id) {
        return limitRepository.findById(id).orElseThrow();
    }
}
