package vn.edu.topica.crawler.caresoft;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = {"vn.edu.topica.crawler.caresoft.entities"})
@EnableJpaRepositories(basePackages = {"vn.edu.topica.crawler.caresoft.repositories"})
@EnableTransactionManagement
@Slf4j
@EnableScheduling
public class CaresoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaresoftApplication.class, args);
    }

}
