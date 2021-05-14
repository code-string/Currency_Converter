package com.currency.converter.domain;

import com.currency.converter.mapper.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeRateApiResponse {
//    private Map<String, String> motd;
    private String success;
    private String base;

//    @JsonIgnore
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date date;
    private Map<String, Float> rates;

}
