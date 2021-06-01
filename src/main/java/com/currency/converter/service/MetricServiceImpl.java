package com.currency.converter.service;

import com.currency.converter.domain.RequestDto;
import com.currency.converter.domain.RequestInfo;
import com.currency.converter.domain.RequestInfoType;
import com.currency.converter.mapper.EntityMapper;
import com.currency.converter.repository.RequestInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MetricServiceImpl implements MetricService{


    @Autowired
    private EntityMapper mapper;

    @Autowired
    private RequestInfoRepository requestRepository;


    @Override
    public void increment(String url, int statusCode, long timestamp) {
        Optional<RequestInfo> requestInfo = requestRepository.findRequestInfoByUrlAndStatusCode(url, (short) statusCode);
        RequestDto dto;
        if(requestInfo.isPresent()){
            if(requestInfo.get().getStatusCode() >= 200 && requestInfo.get().getStatusCode() < 300){
                dto = mapper.mapEntityToDto(requestInfo.get(), RequestDto.class);
                dto.setRequestInfoType(RequestInfoType.SUCCESS);
            }else{
                dto = mapper.mapEntityToDto(requestInfo.get(), RequestDto.class);
                dto.setRequestInfoType(RequestInfoType.FAILED);
            }
            dto.incrementCount();
            dto.setTimestamp(new Date(timestamp));
            RequestInfo requestInfo1 = mapper.mapDtoToEntity(dto, RequestInfo.class);
            requestRepository.save(requestInfo1);

        }else{
            dto = new RequestDto((short)statusCode, url);
            if(statusCode >= 200 && statusCode < 300){
                dto.setRequestInfoType(RequestInfoType.SUCCESS);
            }else{
                dto.setRequestInfoType(RequestInfoType.FAILED);
            }
            dto.incrementCount();
            dto.setTimestamp(new Date(timestamp));
            RequestInfo requestInfo1 = mapper.mapDtoToEntity(dto, RequestInfo.class);
            requestRepository.save(requestInfo1);

        }

    }
}
