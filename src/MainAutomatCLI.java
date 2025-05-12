import domainLogic.Automat;
import cli.AutomatCLI;

public class MainAutomatCLI {
    public static void main(String[] args) {
        Automat automat = new Automat(10);
        AutomatCLI cli = new AutomatCLI(automat);
        cli.start();
    }
}
