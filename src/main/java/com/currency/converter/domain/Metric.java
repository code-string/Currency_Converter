package com.currency.converter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;

    private String ipAddress;

    @OneToMany(fetch = FetchType.LAZY)
    private List<FailedRequest> failedRequestStatusCode = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<SuccessRequest> successRequestStatusCode = new ArrayList<>();

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    private Date timestamp;

}
