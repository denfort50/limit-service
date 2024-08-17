package ru.dkalchenko.limit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dkalchenko.limit.entity.Limit;

public interface LimitRepository extends JpaRepository<Limit, Long> {
}
