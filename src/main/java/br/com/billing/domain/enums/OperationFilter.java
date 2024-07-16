package br.com.billing.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationFilter {

    EQUALS("eq");

    private final String operation;

}
