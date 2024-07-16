package br.com.billing.infrastructure.pageable;

import lombok.Data;

import java.util.List;

@Data
public class Pagination {

    private List<CustomFilter> customFilters;
    private int pageNumber;
    private int pageSize;

}
