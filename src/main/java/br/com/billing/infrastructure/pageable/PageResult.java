package br.com.billing.infrastructure.pageable;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PageResult<T> {
    private final List<T> content;

    @Builder.Default
    private final int pageNumber = 0;

    @Builder.Default
    private final int pageSize = 10;

    @Builder.Default
    private final Long totalElements = 0L;
}
