package br.com.billing.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BillingStatus {

    AWAITING_PAYMENT("Aguardando pagamento"),
    PAYING("Pagando"),
    PAID("Pago"),
    EXPIRED("Vencido"),
    CANCELED("Cancelado");

    private final String label;
}
