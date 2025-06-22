package domainLogic;

import kuchen.Allergen;
import kuchen.Obstkuchen;
import verwaltung.Hersteller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ObstkuchenImpl implements Obstkuchen, Serializable {
    private static final long serialVersionUID = 1L;
    private Hersteller hersteller;
    private Collection<Allergen> allergene;
    private int naehrwert;
    private Duration haltbarkeit;
    private BigDecimal preis;
    private Date inspektionsdatum;
    private int fachnummer;
    private String obstsorte;

    public ObstkuchenImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, String obstsorte) {
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;
        this.obstsorte = obstsorte;
    }

    // Konstruktor für JBP
    public ObstkuchenImpl() {
        this.allergene = new ArrayList<>();
    }

    @Override
    public String getObstsorte() {
        return obstsorte;
    }

    @Override
    public Hersteller getHersteller() {
        return hersteller;
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return allergene;
    }

    @Override
    public int getNaehrwert() {
        return naehrwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        return haltbarkeit;
    }

    @Override
    public BigDecimal getPreis() {
        return preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return inspektionsdatum;
    }

    @Override
    public int getFachnummer() {
        return fachnummer;
    }

    public void setInspektionsdatum(Date inspektionsdatum) {
        this.inspektionsdatum = inspektionsdatum;
    }

    public void setFachnummer(int fachnummer) {
        this.fachnummer = fachnummer;
    }

    // Setter für JBP
    public void setObstsorte(String obstsorte) {
        this.obstsorte = obstsorte;
    }

    public void setHersteller(Hersteller hersteller) {
        this.hersteller = hersteller;
    }

    public void setAllergene(Collection<Allergen> allergene) {
        this.allergene = allergene != null ? allergene : new ArrayList<>();
    }

    public void setNaehrwert(int naehrwert) {
        this.naehrwert = naehrwert;
    }

    public void setHaltbarkeit(Duration haltbarkeit) {
        this.haltbarkeit = haltbarkeit;
    }

    public void setPreis(BigDecimal preis) {
        this.preis = preis;
    }
}
