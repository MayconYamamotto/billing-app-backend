package br.com.billing.domain.service.impl;

import br.com.billing.domain.dao.BillDao;
import br.com.billing.domain.dto.Bill;
import br.com.billing.domain.dto.UserLookupResult;
import br.com.billing.domain.entity.BillEntity;
import br.com.billing.domain.record.TotalBillsPeriodInput;
import br.com.billing.domain.record.TotalBillsPeriodOutput;
import br.com.billing.domain.repository.BillRepository;
import br.com.billing.domain.service.BillService;
import br.com.billing.domain.strategy.BillStategy;
import br.com.billing.infrastructure.UserContext;
import br.com.billing.infrastructure.pageable.CustomFilter;
import br.com.billing.infrastructure.pageable.PageResult;
import br.com.billing.utils.ObjectConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillDao billDao;
    private final ObjectConverter<Bill, BillEntity> objectConverter;
    private final BillStategy billStategy;

    @Override
    public Bill create(Bill bill) {
        billStategy.isAllowedToCreate(bill);

        bill.setSysuser(UserLookupResult.builder().id(UserContext.getUser().getId()).build());

        var entity = objectConverter.convertDtoToEntity(bill, BillEntity.class);
        return objectConverter.convertEntityToDto(billRepository.save(entity), Bill.class);
    }

    @Override
    public Bill pay(UUID id) {
        Bill bill = readAsDto(id);

        bill = bill.pay();

        return update(bill);
    }

    @Override
    public Bill update(Bill bill) {
        return objectConverter.convertEntityToDto(billRepository.save(objectConverter.convertDtoToEntity(bill, BillEntity.class)), Bill.class);
    }

    @Override
    public void delete(UUID id) {
        Bill bill = readAsDto(id);

        billStategy.isAllowedToDelete(bill);

        billRepository.deleteById(id);
    }

    @Override
    public Bill readAsDto(UUID id) {
        return objectConverter.convertEntityToDto(billRepository.findBillByIdAndSysuserId(id, UserContext.getUser().getId()), Bill.class);
    }

    @Override
    public PageResult<Bill> listAsDTO(List<CustomFilter> customFilters, int page, int size) {
        var pageBill = billDao.list(customFilters, Pageable.ofSize(size).withPage(page));

        return PageResult.<Bill>builder()
                .content(pageBill.getContent())
                .pageNumber(pageBill.getNumber())
                .pageSize(pageBill.getSize())
                .totalElements(pageBill.getTotalElements())
                .build();
    }

    @Override
    public TotalBillsPeriodOutput totalBillsPeriod(TotalBillsPeriodInput input) {
        return billDao.totalBillsPeriod(input);
    }
}
