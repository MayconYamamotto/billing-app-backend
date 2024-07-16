package br.com.billing.domain.exception;

public class BusinessException extends IllegalStateException {
    public BusinessException(String message) {
        super(message);
    }
}
