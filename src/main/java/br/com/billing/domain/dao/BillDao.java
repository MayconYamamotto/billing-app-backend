package br.com.billing.domain.dao;

import br.com.billing.domain.dto.Bill;
import br.com.billing.domain.record.TotalBillsPeriodInput;
import br.com.billing.domain.record.TotalBillsPeriodOutput;
import br.com.billing.infrastructure.pageable.CustomFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BillDao {

    Page<Bill> list(List<CustomFilter> filters, Pageable pageable);
    TotalBillsPeriodOutput totalBillsPeriod(TotalBillsPeriodInput filters);

}
