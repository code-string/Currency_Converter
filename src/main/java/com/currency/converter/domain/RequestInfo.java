package com.currency.converter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RequestInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String url;

    private Short statusCode;

    @Enumerated(EnumType.STRING)
    private RequestInfoType requestInfoType;

    private Integer count;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public RequestInfo(Short code, String url) {
        statusCode = code;
        this.url = url;
        count = 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FailedRequest{");
        sb.append("statusCode=").append(statusCode);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestInfo that = (RequestInfo) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
