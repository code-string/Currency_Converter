package com.currency.converter.repository;

import com.currency.converter.domain.RequestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestInfoRepository extends JpaRepository<RequestInfo, Integer> {
    Optional<RequestInfo> findRequestInfoByUrlAndStatusCode(String url, short statusCode);
}
