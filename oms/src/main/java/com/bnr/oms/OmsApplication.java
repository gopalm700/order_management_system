package com.bnr.oms;

import com.bnr.oms.persistence.repo.OrderRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = OrderRepository.class)
public class OmsApplication {
  public static void main(String[] args) {
    SpringApplication.run(OmsApplication.class, args);
  }
}
