import domainLogic.*;
import io.AutomatIO;
import kuchen.Allergen;
import verwaltung.Hersteller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class MainIODemo {

    public static void main(String[] args) {
        try {
            Automat automat = createAutomat();

            demoJOS(automat);

            //demoJBP(automat); // Funktioniert nicht

        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    private static void demoJOS(Automat automat) throws IOException, ClassNotFoundException {
        System.out.println("######################");
        System.out.println("#      JOS Demo      #");
        System.out.println("######################\n");


        // Zustand vor dem Speichern anzeigen
        zeigeZustand(automat, "Zustand vor dem Speichern");

        // Speichern
        String dateiname = "automat_zustand.ser";
        System.out.println("Speichere Zustand in " + dateiname);
        AutomatIO.speichernJOS(automat, dateiname);
        System.out.println("Zustand gespeichert\n");

        // Laden
        System.out.println("Lade Zustand aus " + dateiname);
        Automat geladenerAutomat = AutomatIO.ladenJOS(dateiname);
        System.out.println("Zustand geladen\n");

        // Zustand nach dem Laden anzeigen
        zeigeZustand(geladenerAutomat, "Zustand nach dem Laden");

        // Zustand vergleichen
        vergleicheAutomaten(automat, geladenerAutomat);
    }

    private static void demoJBP(Automat automat) throws IOException {
        System.out.println("######################");
        System.out.println("#      JBP Demo      #");
        System.out.println("######################\n");

        // Zustand vor dem Speichern anzeigen
        zeigeZustand(automat, "Zustand vor dem Speichern");

        // Speichern
        String dateiname = "automat_zustand.xml";
        System.out.println("Speichere Zustand in " + dateiname);
        AutomatIO.speichernJBP(automat, dateiname);
        System.out.println("Zustand gespeichert\n");

        // Laden
        System.out.println("Lade Zustand aus " + dateiname);
        Automat geladenerAutomat = AutomatIO.ladenJBP(dateiname);
        System.out.println("Zustand geladen\n");

        // Zustand nach dem Laden anzeigen
        zeigeZustand(geladenerAutomat, "Zustand nach dem Laden");

        // Zustand vergleichen
        vergleicheAutomaten(automat, geladenerAutomat);
    }

    private static Automat createAutomat() {
        Automat automat = new Automat(10);

        automat.addHersteller("Anton");
        automat.addHersteller("Alina");
        automat.addHersteller("Luca");

        ObstkuchenImpl apfelkuchen = new ObstkuchenImpl(new HerstellerImpl("Anton"), Arrays.asList(Allergen.Gluten, Allergen.Haselnuss), 250, Duration.ofDays(1), new BigDecimal("4.50"), "Apfel");
        apfelkuchen.setInspektionsdatum(new Date());

        ObstkuchenImpl bananenkuchen = new ObstkuchenImpl(new HerstellerImpl("Alina"), Arrays.asList(Allergen.Gluten, Allergen.Erdnuss), 280, Duration.ofDays(2), new BigDecimal("5.50"), "Banane");
        bananenkuchen.setInspektionsdatum(new Date());

        ObstkuchenImpl kirschkuchen = new ObstkuchenImpl(new HerstellerImpl("Luca"), Arrays.asList(Allergen.Haselnuss, Allergen.Erdnuss), 300, Duration.ofDays(3), new BigDecimal("6.50"), "Kirsche");
        kirschkuchen.setInspektionsdatum(new Date());

        automat.addKuchen(apfelkuchen);
        automat.addKuchen(bananenkuchen);
        automat.addKuchen(kirschkuchen);

        return automat;
    }

    public static void zeigeZustand(Automat automat, String titel) {
        System.out.println("=== " + titel + " ===");
        System.out.println("Kapazität: " + automat.getKapazitaet());

        System.out.println("\nHersteller:");
        for (Hersteller hersteller : automat.getHersteller()) {
            System.out.println("- " + hersteller.getName());
        }

        System.out.println("\nKuchen:");
        for (ObstkuchenImpl kuchen : automat.getAlleKuchen(null)) {
            System.out.println("- Fach: " + kuchen.getFachnummer()
                    + ", Hersteller: " + kuchen.getHersteller().getName()
                    + ", Allergene: " + kuchen.getAllergene()
                    + ", Nährwert: " + kuchen.getNaehrwert()
                    + ", Haltbarkeit: " + kuchen.getHaltbarkeit()
                    + ", Preis: " + kuchen.getPreis()
                    + ", Obstsorte: " + kuchen.getObstsorte()
                    + ", Letzte Inspektion: " + kuchen.getInspektionsdatum());
        }

        System.out.println("=== \n");
    }

    public static void vergleicheAutomaten(Automat original, Automat geladen) {
        System.out.println("=== Vergleich ===");
        System.out.println("Kapazität gleich: " + (original.getKapazitaet() == geladen.getKapazitaet()));
        System.out.println("Anzahl Hersteller gleich: " + (original.getHersteller().size() == geladen.getHersteller().size()));
        System.out.println("Anzahl Kuchen gleich: " + (original.getAlleKuchen(null).size() == geladen.getAlleKuchen(null).size()));
        System.out.println("=== \n");
    }
}