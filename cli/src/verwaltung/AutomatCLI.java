package verwaltung;

import kuchen.Allergen;
import kuchen.Kuchen;
import kuchen.ObstkuchenImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class AutomatCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Automat automat = new Automat(10);

    public static void main(String[] args) {
        automat.addHersteller("Froggy"); // für Testzwecke

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

    private static void addKuchen() {
        Hersteller hersteller = new HerstellerImpl("Froggy");
        Set<Allergen> allergene = new HashSet<>();
        allergene.add(Allergen.Gluten);

        ObstkuchenImpl kuchen = new ObstkuchenImpl(hersteller, allergene, 20, Duration.ofDays(5), new BigDecimal("1.99"), "Apfel");
        if (automat.addKuchen(kuchen)) {
            System.out.println("Kuchen hinzugefügt.");
        } else {
            System.out.println("Kuchen konnte nicht hinzugefügt werden.");
        }
    }

    private static void listKuchen() {
        List<ObstkuchenImpl> kuchenList = automat.getAlleKuchen(null);
        for (ObstkuchenImpl k : kuchenList) {
            System.out.println("Fach: " + k.getFachnummer()
                    + ", Hersteller: " + k.getHersteller().getName()
                    + ", Allergene: " + k.getAllergene()
                    + ", Nährwert: " + k.getNaehrwert()
                    + ", Haltbarkeit: " + k.getHaltbarkeit()
                    + ", Preis: " + k.getPreis()
                    + ", Obstsorte: " + k.getObstsorte()
                    + ", Inspektion: " + k.getInspektionsdatum());
        }
    }

    private static void updateInspektionsdatum() {
        System.out.print("Fachnummer zum Aktualisieren: ");
        int fachnummer = Integer.parseInt(scanner.nextLine());
        if (automat.updateDatum(fachnummer, new Date())) {
            System.out.println("Datum aktualisiert.");
        } else {
            System.out.println("Kein Kuchen unter dieser Fachnummer gefunden.");
        }
    }

    private static void removeKuchen() {
        System.out.print("Fachnummer zum Entfernen: ");
        int fachnummer = Integer.parseInt(scanner.nextLine());
        if (automat.removeKuchen(fachnummer)) {
            System.out.println("Kuchen entfernt.");
        } else {
            System.out.println("Kein Kuchen unter dieser Fachnummer gefunden.");
        }
    }
}
