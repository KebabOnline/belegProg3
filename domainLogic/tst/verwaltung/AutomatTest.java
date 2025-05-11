package verwaltung;

import kuchen.Allergen;
import kuchen.Kuchen;
import kuchen.ObstkuchenImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AutomatTest {

    @Test
    public void testAddKuchen() {
        Automat automat = new Automat(20);
        HerstellerImpl hersteller = new HerstellerImpl("Bäcker");
        automat.addHersteller(hersteller.getName());
        Set<Allergen> allergene = EnumSet.of(Allergen.Gluten, Allergen.Haselnuss);

        ObstkuchenImpl kuchen = new ObstkuchenImpl(hersteller, allergene, 20, Duration.ofDays(5), new BigDecimal("1.99"), "Apfel");
        boolean result = automat.addKuchen(kuchen);
        assertTrue(result);
    }

    @Test
    public void testAddKuchenAutomatVoll() {
        Automat automat = new Automat(1);
        HerstellerImpl hersteller = new HerstellerImpl("Bäcker");
        automat.addHersteller(hersteller.getName());
        Set<Allergen> allergene = EnumSet.of(Allergen.Gluten, Allergen.Haselnuss);

        ObstkuchenImpl kuchen = new ObstkuchenImpl(hersteller, allergene, 20, Duration.ofDays(5), new BigDecimal("1.99"), "Apfel");
        automat.addKuchen(kuchen);
        boolean result = automat.addKuchen(kuchen);
        assertFalse(result);
    }

    @Test
    public void testAddKuchenUnbekannterHersteller() {
        Automat automat = new Automat(3);
        HerstellerImpl hersteller = new HerstellerImpl("Bäcker");
        Set<Allergen> allergene = EnumSet.of(Allergen.Gluten, Allergen.Haselnuss);

        ObstkuchenImpl kuchen = new ObstkuchenImpl(hersteller, allergene, 20, Duration.ofDays(5), new BigDecimal("1.99"), "Apfel");
        boolean result = automat.addKuchen(kuchen);
        assertFalse(result);
    }

    @Test
    public void testGetAlleKuchenMitFilter() {
        Automat automat = new Automat(10);
        HerstellerImpl hersteller = new HerstellerImpl("Bäcker");
        automat.addHersteller(hersteller.getName());
        Set<Allergen> allergene = EnumSet.of(Allergen.Gluten, Allergen.Haselnuss);

        ObstkuchenImpl kuchen1 = new ObstkuchenImpl(hersteller, allergene, 20, Duration.ofDays(5), new BigDecimal("1.99"), "Apfel");
        ObstkuchenImpl kuchen2 = new ObstkuchenImpl(hersteller, allergene, 15, Duration.ofDays(3), new BigDecimal("0.99"), "Banane");
        automat.addKuchen(kuchen1);
        automat.addKuchen(kuchen2);

        List<ObstkuchenImpl> liste = automat.getAlleKuchen("ObstkuchenImpl");
        assertEquals(2, liste.size());
    }

    @Test
    public void testGetAlleKuchenOhneFilter() {
        Automat automat = new Automat(10);
        HerstellerImpl hersteller = new HerstellerImpl("Bäcker");
        automat.addHersteller(hersteller.getName());
        Set<Allergen> allergene = EnumSet.of(Allergen.Gluten, Allergen.Haselnuss);

        ObstkuchenImpl kuchen1 = new ObstkuchenImpl(hersteller, allergene, 20, Duration.ofDays(5), new BigDecimal("1.99"), "Apfel");
        ObstkuchenImpl kuchen2 = new ObstkuchenImpl(hersteller, allergene, 15, Duration.ofDays(3), new BigDecimal("0.99"), "Banane");
        automat.addKuchen(kuchen1);
        automat.addKuchen(kuchen2);

        List<ObstkuchenImpl> liste = automat.getAlleKuchen(null);
        assertEquals(2, liste.size());
    }

    @Test
    public void testGetAlleKuchenLeer() {
        Automat automat = new Automat(10);
        List<ObstkuchenImpl> liste = automat.getAlleKuchen(null);
        assertEquals(0, liste.size());
    }

    @Test
    public void testUpdateDatum() {
        Automat automat = new Automat(10);
        HerstellerImpl hersteller = new HerstellerImpl("Bäcker");
        automat.addHersteller(hersteller.getName());
        Set<Allergen> allergene = EnumSet.of(Allergen.Gluten, Allergen.Haselnuss);

        ObstkuchenImpl kuchen = new ObstkuchenImpl(hersteller, allergene, 20, Duration.ofDays(5), new BigDecimal("1.99"), "Apfel");
        automat.addKuchen(kuchen);

        int fachnummer = kuchen.getFachnummer();
        Date neuesDatum = new Date(System.currentTimeMillis() + 999999999);

        boolean aktualisiert = automat.updateDatum(fachnummer, neuesDatum);
        assertTrue(aktualisiert);
        assertEquals(neuesDatum, kuchen.getInspektionsdatum());
    }

    @Test
    public void testUpdateDatumNichtVorhanden() {
        Automat automat = new Automat(10);
        Date neuesDatum = new Date();
        boolean result = automat.updateDatum(123, neuesDatum);
        assertFalse(result);
    }

    @Test
    public void testRemoveKuchen() {
        Automat automat = new Automat(3);
        HerstellerImpl hersteller = new HerstellerImpl("Bäcker");
        automat.addHersteller(hersteller.getName());
        Set<Allergen> allergene = EnumSet.of(Allergen.Gluten, Allergen.Haselnuss);

        ObstkuchenImpl kuchen = new ObstkuchenImpl(hersteller, allergene, 20, Duration.ofDays(5), new BigDecimal("1.99"), "Apfel");
        automat.addKuchen(kuchen);

        int fachnummer = kuchen.getFachnummer();
        boolean entfernt = automat.removeKuchen(fachnummer);
        assertTrue(entfernt);

        List<ObstkuchenImpl> alleKuchen = automat.getAlleKuchen(null);
        assertTrue(alleKuchen.isEmpty());
    }

    @Test
    public void testRemoveKuchenNichtVorhanden() {
        Automat automat = new Automat(3);
        boolean entfernt = automat.removeKuchen(1234);
        assertFalse(entfernt);
    }
}
