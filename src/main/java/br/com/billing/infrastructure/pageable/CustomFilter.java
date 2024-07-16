package br.com.billing.infrastructure.pageable;

import br.com.billing.domain.entity.QBillEntity;
import br.com.billing.domain.enums.OperationFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CustomFilter {

    private static final QBillEntity BILL_ENTITY = QBillEntity.billEntity;

    private String field;
    private List<String> values;
    private OperationFilter operation;

}
