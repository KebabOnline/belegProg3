package cli;

import domainLogic.Automat;
import domainLogic.HerstellerImpl;
import kuchen.Allergen;
import domainLogic.ObstkuchenImpl;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class AutomatCLI {
    private final Scanner scanner = new Scanner(System.in);
    private final Automat automat;

    public AutomatCLI(Automat automat) {
        this.automat = automat;
    }

    public void start() {
        automat.addHersteller("Froggy"); // temp

        System.out.println("Eingaben: add | list | update | remove | exit");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            switch (input.toLowerCase()) {
                case "add":
                    addKuchen();
                    break;
                case "list":
                    listKuchen();
                    break;
                case "update":
                    updateInspektionsdatum();
                    break;
                case "remove":
                    removeKuchen();
                    break;
                case "exit":
                    System.out.println("Programm beendet.");
                    return;
                default:
                    System.out.println("Unbekannter Befehl.");
            }
        }
    }

    private void addKuchen() {
        Hersteller hersteller = new HerstellerImpl("Froggy");
        Set<Allergen> allergene = EnumSet.of(Allergen.Gluten, Allergen.Haselnuss);

        ObstkuchenImpl kuchen = new ObstkuchenImpl(hersteller, allergene, 20, Duration.ofDays(5), new BigDecimal("1.99"), "Apfel");
        if (automat.addKuchen(kuchen)) {
            System.out.println("Kuchen hinzugefügt.");
        } else {
            System.out.println("Kuchen konnte nicht hinzugefügt werden.");
        }
    }

    private void listKuchen() {
        List<ObstkuchenImpl> kuchenList = automat.getAlleKuchen(null);
        for (ObstkuchenImpl kuchen : kuchenList) {
            System.out.println("Fach: " + kuchen.getFachnummer()
                    + ", Hersteller: " + kuchen.getHersteller().getName()
                    + ", Allergene: " + kuchen.getAllergene()
                    + ", Nährwert: " + kuchen.getNaehrwert()
                    + ", Haltbarkeit: " + kuchen.getHaltbarkeit()
                    + ", Preis: " + kuchen.getPreis()
                    + ", Obstsorte: " + kuchen.getObstsorte()
                    + ", Letzte Inspektion: " + kuchen.getInspektionsdatum());
        }
    }

    private void updateInspektionsdatum() {
        System.out.print("Fachnummer zum Aktualisieren: ");
        int fachnummer = Integer.parseInt(scanner.nextLine());
        if (automat.updateDatum(fachnummer, new Date())) {
            System.out.println("Datum aktualisiert.");
        } else {
            System.out.println("Kein Kuchen unter dieser Fachnummer gefunden.");
        }
    }

    private void removeKuchen() {
        System.out.print("Fachnummer zum Entfernen: ");
        int fachnummer = Integer.parseInt(scanner.nextLine());
        if (automat.removeKuchen(fachnummer)) {
            System.out.println("Kuchen entfernt.");
        } else {
            System.out.println("Kein Kuchen unter dieser Fachnummer gefunden.");
        }
    }
}
