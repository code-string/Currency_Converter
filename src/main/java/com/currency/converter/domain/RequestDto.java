package com.currency.converter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class RequestDto {

    private Integer id;

    @NotBlank
    private Short statusCode;

    @NotBlank
    private String url;

    @NotBlank
    private RequestInfoType requestInfoType;

    @NotBlank
    private Integer count;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public RequestDto(Short statusCode, String url){
        this.statusCode = statusCode;
        this.url = url;
        count = 0;
    }
    public int incrementCount(){
        return count += 1;
    }
}
