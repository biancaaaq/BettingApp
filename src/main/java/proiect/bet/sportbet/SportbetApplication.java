package proiect.bet.sportbet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("proiect.bet.sportbet.models")
public class SportbetApplication {
    public static void main(String[] args) {
        SpringApplication.run(SportbetApplication.class, args);
    }
}