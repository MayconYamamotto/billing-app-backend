package br.com.billing.domain.dao.impl;

import br.com.billing.domain.dao.BillDao;
import br.com.billing.domain.dto.Bill;
import br.com.billing.domain.entity.BillEntity;
import br.com.billing.domain.entity.QBillEntity;
import br.com.billing.domain.record.TotalBillsPeriodInput;
import br.com.billing.domain.record.TotalBillsPeriodOutput;
import br.com.billing.infrastructure.UserContext;
import br.com.billing.infrastructure.pageable.CustomFilter;
import br.com.billing.utils.ObjectConverter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BillDaoImpl implements BillDao {

    private static final QBillEntity BILL_ENTITY = QBillEntity.billEntity;

    @PersistenceContext
    private EntityManager entityManager;

    private final ObjectConverter<Bill, BillEntity> objectConverter;

    public Page<Bill> list(List<CustomFilter> filters, Pageable pageable) {
        var query = new JPAQueryFactory(entityManager)
                .selectFrom(QBillEntity.billEntity)
                .where(getWhere(filters))
                .orderBy(QBillEntity.billEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        var content = query.fetch();
        var total = query.fetchCount();

        return new PageImpl<>(objectConverter.convertEntityListToDtoList(content, Bill.class), pageable, total);
    }

    @Override
    public TotalBillsPeriodOutput totalBillsPeriod(TotalBillsPeriodInput filters) {
        var query = new JPAQueryFactory(entityManager)
                .select(BILL_ENTITY.amount.sum().coalesce(BigDecimal.ZERO))
                .from(BILL_ENTITY)
                .where(BILL_ENTITY.sysuser.id.eq(UserContext.getUser().getId())
                        .and(BILL_ENTITY.paymentDate.between(filters.initialDate(), filters.finalDate())));

        var total = query.fetchOne();

        return new TotalBillsPeriodOutput(total);
    }

    private static BooleanBuilder getWhere(List<CustomFilter> filters) {
        var where = new BooleanBuilder();

        where.and(BILL_ENTITY.sysuser.id.eq(UserContext.getUser().getId()));

        for (CustomFilter filter : filters) {
            if (filter.getOperation() != null) {
                if (filter.getField().equals("description")) {
                    where.and(BILL_ENTITY.description.eq(filter.getValues().get(0)));
                }
                if (filter.getField().equals("expirationDate")) {
                    where.and(BILL_ENTITY.expirationDate.eq(LocalDate.parse(filter.getValues().get(0))));
                }
            }
        }

        return where;
    }

}
