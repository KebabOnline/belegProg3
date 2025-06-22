package domainLogic;

import verwaltung.Hersteller;

import java.io.Serializable;

public class HerstellerImpl implements Hersteller, Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;

    public HerstellerImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
