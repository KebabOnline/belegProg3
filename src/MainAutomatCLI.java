import verwaltung.Automat;
import verwaltung.AutomatCLI;

public class MainAutomatCLI {
    public static void main(String[] args) {
        Automat automat = new Automat(10); // oder aus args lesen
        AutomatCLI cli = new AutomatCLI(automat);
        cli.start();
    }
}
