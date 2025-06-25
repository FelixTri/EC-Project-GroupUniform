package at.fhtw.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EnergyUserUniformApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyUserUniformApplication.class, args);
    }
}
