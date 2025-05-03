package kuchen;

import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class ObstkuchenImpl implements Obstkuchen {
    private final Hersteller hersteller;
    private final Collection<Allergen> allergene;
    private final int naehrwert;
    private final Duration haltbarkeit;
    private final BigDecimal preis;
    private Date inspektionsdatum;
    private int fachnummer;
    private final String obstsorte;

    public ObstkuchenImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, String obstsorte) {
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;
        this.obstsorte = obstsorte;
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
}
