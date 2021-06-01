package com.currency.converter.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    @Autowired
    private ModelMapper mapper;

    public <D> D mapDtoToEntity(Object dto, Class<D> entityClass){
        return mapper.map(dto, entityClass);
    }

    public <D> D mapEntityToDto(Object entity, Class<D> dtoClass){
        return mapper.map(entity, dtoClass);
    }
}
