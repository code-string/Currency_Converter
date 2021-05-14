package com.currency.converter.domain;

import com.currency.converter.mapper.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RateDto implements Comparable<RateDto> {
    @Id
    private String code;
    @NotBlank
    private Float rate;

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    private Date date;

    public RateDto(String code, Date date) {
        this.code = code;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateDto rateDto = (RateDto) o;
        return getCode().equals(rateDto.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public String toString() {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return "Rate [code=" + ", rate=" + rate + ", date=" + format + "] \n";
    }

    @Override
    public int compareTo(RateDto rateDto) {
        return Integer.compare(this.getCode().compareToIgnoreCase(rateDto.getCode()), 0);
    }
}
