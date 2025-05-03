package verwaltung;

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
