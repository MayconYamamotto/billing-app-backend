package br.com.billing.domain.service;

import br.com.billing.domain.dto.Bill;
import br.com.billing.domain.record.TotalBillsPeriodInput;
import br.com.billing.domain.record.TotalBillsPeriodOutput;
import br.com.billing.infrastructure.pageable.CustomFilter;
import br.com.billing.infrastructure.pageable.PageResult;

import java.util.List;
import java.util.UUID;

public interface BillService {

    Bill create(Bill bill);

    Bill pay(UUID id);

    Bill readAsDto(UUID id);

    PageResult<Bill> listAsDTO(List<CustomFilter> customFilters, int page, int size);

    TotalBillsPeriodOutput totalBillsPeriod(TotalBillsPeriodInput input);

    Bill update(Bill bill);

    void delete(UUID id);
}
