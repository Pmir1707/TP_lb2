package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.models.Person;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Person p = new Person("123", "234", "345", "343", "12");
        System.out.println(p);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/MainForm.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Строкань Денис. Курсовая.");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
