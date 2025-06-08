package proiect.bet.sportbet.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import proiect.bet.sportbet.service.ApiFootballImporterService;

@Component
public class MeciuriInitializer implements CommandLineRunner {

    private final ApiFootballImporterService importerService;

    public MeciuriInitializer(ApiFootballImporterService importerService) {
        this.importerService = importerService;
    }

    @Override
    public void run(String... args) {
        System.out.println("Import automat meciuri din API-Football...");
        importerService.importMeciuri(); // Asigură-te că metoda NU aruncă excepții necontrolate
        System.out.println("Meciuri importate cu succes.");
    }
}
