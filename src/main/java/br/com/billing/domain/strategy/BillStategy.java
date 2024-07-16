package br.com.billing.domain.strategy;

import br.com.billing.domain.dto.Bill;
import br.com.billing.domain.enums.BillingStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static br.com.billing.infrastructure.UserContext.isLoggedUser;

@Component
public class BillStategy {

    public void isAllowedToCreate(Bill bill) {
        if (bill.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da conta deve ser maior que zero.");
        }

        if (bill.getStatus().equals(BillingStatus.PAID) && bill.getPaymentDate() == null) {
            throw new IllegalArgumentException("A conta paga deve ter a data de pagamento preenchida.");
        }

        if (!bill.getStatus().equals(BillingStatus.PAID) && bill.getPaymentDate() != null) {
            throw new IllegalArgumentException("Somente contas pagas devem ter a data de pagamento preenchida.");
        }
    }

    public void isAllowedPayment(Bill bill) {
        verifyUser(bill.getSysuser().getId());

        if (!bill.getStatus().equals(BillingStatus.AWAITING_PAYMENT)) {
            throw new IllegalArgumentException("É permitido pagar apenas contas com status 'Aguardando pagamento'.");
        }

        if (bill.getExpirationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("É permitido pagar apenas contas com status 'Aguardando pagamento'.");
        }
    }

    public void isAllowedToDelete(Bill bill) {
        verifyUser(bill.getSysuser().getId());

        if (!bill.getStatus().equals(BillingStatus.AWAITING_PAYMENT)) {
            throw new IllegalArgumentException("É permitido excluir apenas contas com status 'Aguardando pagamento'.");
        }
    }

    private void verifyUser(UUID userId) {
        if (isLoggedUser(userId)) {
            throw new IllegalArgumentException("A conta não pertence ao usuário logado.");
        }
    }
}
