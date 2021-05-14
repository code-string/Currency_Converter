package com.currency.converter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    @Id
    private String code;
    private Float rate;

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    private Date date;

    @Override
    public String toString() {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return "Rate [code=" + ", rate=" + rate + ", date=" + format + "]";
    }
}
