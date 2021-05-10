package com.currency.converter.repository;

import com.currency.converter.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, String> {
    List<Rate> findByDate(Date date);
    Optional<Rate> findByDateAndCode(Date date, String code);

}
