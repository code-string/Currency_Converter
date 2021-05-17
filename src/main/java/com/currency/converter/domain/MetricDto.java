package com.currency.converter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricDto {

    @NotBlank
    private String url;

    @NotBlank
    private String ipAddress;

    private List<FailedRequest> failedRequestStatusCode = new ArrayList<>();

    private List<SuccessRequest> successRequestStatusCode = new ArrayList<>();

    @JsonIgnore
    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date timestamp;
}
