package br.com.billing.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ObjectConverter<D, E> {

    private final ObjectMapper objectMapper;

    public ObjectConverter(ObjectMapper objectMapper) {
        super();
        this.objectMapper = objectMapper;
    }

    public D convertEntityToDto(E entity, Class<D> dtoClass) {
        D dto = null;
        try {
            if (entity != null) {
                dto = objectMapper.convertValue(entity, dtoClass);
            }
        } catch (IllegalArgumentException e) {
            log.error("Erro ao converter a Entidade para DTO", e);
        }

        return dto;
    }

    public E convertDtoToEntity(D dto, Class<E> entityClass) {
        E entity = null;
        try {
            if (dto != null) {
                entity = objectMapper.convertValue(dto, entityClass);
            }
        } catch (IllegalArgumentException e) {
            log.error("Erro ao converter o DTO para Entidade", e);
        }

        return entity;
    }

    public List<D> convertEntityListToDtoList(List<E> entityList, Class<D> dtoClass) {
        return entityList.stream().map(entity -> convertEntityToDto(entity, dtoClass)).toList();
    }

    public List<E> convertDtoListToEntityList(List<D> dtoList, Class<E> entityClass) {
        return dtoList.stream().map(dto -> convertDtoToEntity(dto, entityClass)).toList();
    }

}
