package domainLogic;

import verwaltung.Hersteller;

import java.io.Serializable;

public class HerstellerImpl implements Hersteller, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    public HerstellerImpl(String name) {
        this.name = name;
    }

    // Konstruktor für JBP
    public HerstellerImpl() {
        this("");
    }

    @Override
    public String getName() {
        return name;
    }

    // Setter für JBP
    public void setName(String name) {
        this.name = name;
    }

}
