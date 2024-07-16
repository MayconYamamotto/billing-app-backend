package br.com.billing.domain.record;

import java.time.LocalDate;

public record TotalBillsPeriodInput(LocalDate initialDate, LocalDate finalDate) {
}
