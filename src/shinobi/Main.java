package shinobi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shinobi.model.DataSource;

public class Main extends Application {


    // Run Front
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {

        // Start DB connection
        DataSource dataSource = new DataSource();
        if(!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

                launch(args);

        dataSource.close();
    }
}
