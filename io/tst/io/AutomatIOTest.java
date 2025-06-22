package io;

import domainLogic.Automat;
import domainLogic.HerstellerImpl;
import domainLogic.ObstkuchenImpl;
import kuchen.Allergen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

public class AutomatIOTest {

    private Automat automat;

    @BeforeEach
    void setUp() {
        automat = new Automat(5);
        automat.addHersteller("Anton");

        ObstkuchenImpl kuchen = new ObstkuchenImpl(new HerstellerImpl("Anton"), Arrays.asList(Allergen.Gluten, Allergen.Haselnuss), 250, Duration.ofDays(1), new BigDecimal("4.50"), "Apfel");
        automat.addKuchen(kuchen);
    }

    @AfterEach
    void tearDown() {
        new File("test.jos").delete();
        new File("test.xml").delete();
    }

    @Test
    void testSpeichernJOS() throws IOException {
        String filename = "test.jos";

        AutomatIO.speichernJOS(automat, filename);

        assertTrue(new File(filename).length() > 0);
    }

    @Test
    void testLadenJOS() throws IOException, ClassNotFoundException {
        String filename = "test.jos";
        AutomatIO.speichernJOS(automat, filename);

        Automat geladenerAutomat = AutomatIO.ladenJOS(filename);

        assertNotNull(geladenerAutomat);
    }

    @Test
    void testSpeichernJBP() throws IOException {
        String filename = "test.xml";

        AutomatIO.speichernJBP(automat, filename);

        assertTrue(new File(filename).exists());
    }

    @Test
    void testLadenJBP() throws IOException {
        String filename = "test.xml";
        AutomatIO.speichernJBP(automat, filename);

        Automat geladenerAutomat = AutomatIO.ladenJBP(filename);

        assertNotNull(geladenerAutomat);
    }
}
