package ru.dkalchenko.limit.dto;

import jakarta.validation.constraints.Min;

public record Request(Long id, @Min(value = 0, message = "Лимит не может быть меньше 0") Double sum) {
}
