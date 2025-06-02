import cli.AutomatCLI;
import domainLogic.Automat;
import sim.Simulation1;

public class MainSimulation1 {
    public static void main(String[] args) {
        int kapazitaet;

        if (args.length > 0) {
            kapazitaet = Integer.parseInt(args[0]);
        } else {
            kapazitaet = 50; // Standardwert
        }

        Automat automat = new Automat(kapazitaet);
        Simulation1 simulation = new Simulation1(automat);
        simulation.start();
    }
}
