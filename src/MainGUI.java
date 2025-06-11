import domainLogic.Automat;
import gui.KuchenverwaltungController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Automat automat = new Automat(20);

        automat.addHersteller("Anton");
        automat.addHersteller("Alina");
        automat.addHersteller("Luca");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Kuchenverwaltung.fxml"));
        Parent root = loader.load();

        KuchenverwaltungController controller = loader.getController();
        //controller.setAutomat(automat); // Automat an Controller übergeben

        stage.setTitle("Kuchen Control Terminal");
        Scene scene = new Scene(root, 1000, 700);

        stage.setScene(scene);
        stage.show();
    }
}
