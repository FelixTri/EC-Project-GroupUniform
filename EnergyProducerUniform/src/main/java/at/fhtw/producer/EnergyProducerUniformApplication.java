package at.fhtw.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EnergyProducerUniformApplication {
	public static void main(String[] args) {
		SpringApplication.run(EnergyProducerUniformApplication.class, args);
	}
}
