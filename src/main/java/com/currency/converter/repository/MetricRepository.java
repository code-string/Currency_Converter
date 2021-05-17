package com.currency.converter.repository;

import com.currency.converter.domain.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {

    List<Metric> findMetricsByIpAddress(String ipAddress);
}
