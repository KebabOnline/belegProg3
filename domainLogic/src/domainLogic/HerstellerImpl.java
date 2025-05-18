package domainLogic;

import verwaltung.Hersteller;

public class HerstellerImpl implements Hersteller {

    private final String name;

    public HerstellerImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
