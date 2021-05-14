package com.currency.converter;

import com.currency.converter.domain.Rate;
import com.currency.converter.repository.RateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ConverterApplication {

//    @Autowired
//    private RateRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ConverterApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner data(RateRepository repository){
//        return (args) -> {
//            repository.save(new Rate("EUR", 0.88857F, new Date()));
//            repository.save(new Rate("JPY", 102.17F, new Date()));
//            repository.save(new Rate("MXN", 19.232F, new Date()));
//            repository.save(new Rate("GBP", 0.75705F, new Date()));
//        };
//    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

//    @Override
//    public void run(String... args) throws Exception {
//        Rate rate1 = new Rate("EUR", 0.88857F, new Date());
//        Rate rate2 = new Rate("JPY", 102.17F, new Date());
//        Rate rate3 = new Rate("MXN", 19.232F, new Date());
//        Rate rate4 = new Rate("GBP", 0.75705F, new Date());
//
//        repository.saveAll(List.of(rate1, rate2, rate3, rate4));
//    }
}

@EnableScheduling
@EnableAsync
@Configuration
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class ScheduleConfiguration{

}
