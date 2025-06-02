package sim;

import domainLogic.Automat;
import domainLogic.HerstellerImpl;
import domainLogic.ObstkuchenImpl;
import kuchen.Allergen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Simulation1 {

    // Konstanten für die zufälligen Kuchen
    private static final String[] OBSTSORTEN = {
            "Apfel", "Banane", "Erdbeere", "Himbeere", "Blaubeere"
    };

    private static final String[] HERSTELLER_NAMEN = {
            "Anton", "Luca", "Alina"
    };

    private final Automat automat;
    private final Random random = new Random();

    public Simulation1(Automat automat) {
        this.automat = automat;
        for (String herstellerName : HERSTELLER_NAMEN) {
            this.automat.addHersteller(herstellerName);
        }
    }

    public void start() {
        automat.addListener(new ConsoleBeobachter());

        // Fügt Kuchen hinzu
        Thread addThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Zufälligen Kuchen erzeugen
                    ObstkuchenImpl neuerKuchen = createRandomKuchen();
                    boolean hinzugefuegt = automat.addKuchen(neuerKuchen);

                    String timestamp = java.time.LocalTime.now().toString();

                    if (hinzugefuegt) {
                        System.out.printf("[%s] ADD THREAD: Kuchen hinzugefügt (%s, %s)%n",
                                timestamp, neuerKuchen.getObstsorte(), neuerKuchen.getHersteller().getName());
                    } else {
                        System.out.printf("[%s] ADD THREAD: Kuchen nicht hinzugefügt%n",
                                timestamp);
                    }

                    Thread.sleep(0);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        // Löscht Kuchen
        Thread removeThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    List<ObstkuchenImpl> alleKuchen = automat.getAlleKuchen(null);
                    String timestamp = java.time.LocalTime.now().toString();

                    if (!alleKuchen.isEmpty()) {
                        // Zufälligen Kuchen auswählen und entfernen
                        ObstkuchenImpl randomKuchen = alleKuchen.get(random.nextInt(alleKuchen.size()));
                        int fachnummer = randomKuchen.getFachnummer();
                        boolean entfernt = automat.removeKuchen(fachnummer);

                        if (entfernt) {
                            System.out.printf("[%s] REM THREAD: Kuchen entfernt (Fachnummer: %d, %s, %s)%n",
                                    timestamp, fachnummer, randomKuchen.getObstsorte(), randomKuchen.getHersteller().getName());
                        } else {
                            System.out.printf("[%s] REM THREAD: Kuchen nicht entfernt (Fachnummer: %d)%n",
                                    timestamp, fachnummer);
                        }
                    } else {
                        System.out.printf("[%s] REM THREAD: Keine Kuchen im Automat%n",
                                timestamp);
                    }

                    Thread.sleep(0);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        addThread.start();
        removeThread.start();
    }

    private ObstkuchenImpl createRandomKuchen() {
        // Hersteller
        String herstellerName = HERSTELLER_NAMEN[random.nextInt(HERSTELLER_NAMEN.length)];
        Hersteller hersteller = new HerstellerImpl(herstellerName);

        // 2 Allergene
        Collection<Allergen> allergene = new ArrayList<>();
        Allergen[] alleAllergene = Allergen.values();
        allergene.add(alleAllergene[random.nextInt(alleAllergene.length)]);
        allergene.add(alleAllergene[random.nextInt(alleAllergene.length)]);

        int naehrwert = 200 + random.nextInt(300); // 200-500 Nährwert
        Duration haltbarkeit = Duration.ofDays(3 + random.nextInt(5)); // 3-7 Tage Haltbarkeit
        BigDecimal preis = BigDecimal.valueOf(2.50 + random.nextDouble() * 7.50); // 2.50-10.00 Euro Preis
        String obstsorte = OBSTSORTEN[random.nextInt(OBSTSORTEN.length)]; // Obstsorte

        return new ObstkuchenImpl(hersteller, allergene, naehrwert, haltbarkeit, preis, obstsorte);
    }
}