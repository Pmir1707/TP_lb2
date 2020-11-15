package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Hierarchy.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ArrayList<Plant> plants = new ArrayList<>(); // список растений

    @FXML
    private ListView<Plant> plantListView; // listView из fxml для отображения

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // добавляем
        try {
            // создаем поток для чтения из файла
            FileInputStream fis = new FileInputStream("settings.xml");
            // создаем xml декодер из файла
            XMLDecoder decoder = new XMLDecoder(fis);
            Settings settings = (Settings) decoder.readObject();

            // а теперь заполняем форму
            plants = settings.plants; // считываем тот список из settings
            plantListView.setItems( // заполняет listview
                    FXCollections.observableArrayList(settings.plants));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStageClose() {
        // создали экземпляр класса
        Settings settings = new Settings();

        settings.plants = plants;

        // добавляем
        try {
            // создаем поток для записи в файл settings.xml
            FileOutputStream fos = new FileOutputStream("settings.xml");
            // создали энкодер, которые будет писать в поток
            XMLEncoder encoder = new XMLEncoder(fos);

            // записали настройки
            encoder.writeObject(settings);

            // закрыли энкодер и поток для записи
            // если не закрыть, то файл будет пустой
            encoder.close();
            fos.close();
        } catch (Exception e) {
            // на случай ошибки
            e.printStackTrace();
        }
    }

    public void onClickAdd(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addView.fxml"));
        // а затем уже непосредственно вызываем загрузку дерева разметки из файла
        Parent root = loader.load();

        AddController addController = loader.getController(); // считываем контроллер для addView.fxml

        stage.setScene(new Scene(root)); // ставим эту сцену
        stage.setTitle("Добавить");
        stage.initModality(Modality.WINDOW_MODAL); // делаем модальным
        stage.initOwner( // определяем владельца (главную форму)
                ((Node)event.getSource()).getScene().getWindow() );

        stage.showAndWait(); // показываем

        if (addController.isClickedOk()) { // если нажали Добавить
            Plant plantForAdding = addController.getPlant(); // считываем растение
            plants.add(plantForAdding); // добавляем в список

            plantListView.getItems().add(plantForAdding); // отображаем в listView
        }
    }

    public void onClickRemove(){
        Plant selectedPlant = plantListView.getSelectionModel().getSelectedItem();
        if (selectedPlant != null) {
            final int selectedIdx = plantListView.getSelectionModel().getSelectedIndex();
            System.out.println(selectedIdx);
            plantListView.getItems().remove(selectedIdx);
            plants.remove(selectedIdx);
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ничего не выбрано");
            alert.setContentText("Выберите растение из списка.");

            alert.showAndWait();
        }
    }

    public void onClickChange(ActionEvent event) throws IOException{
        Plant selectedPlant = plantListView.getSelectionModel().getSelectedItem();
        if (selectedPlant != null) {
            final int selectedIdx = plantListView.getSelectionModel().getSelectedIndex();
            plantListView.getItems().remove(selectedIdx);
            plants.remove(selectedIdx);

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addView.fxml"));
            // а затем уже непосредственно вызываем загрузку дерева разметки из файла
            Parent root = loader.load();

            AddController addController = loader.getController();
            addController.setPlant(selectedPlant);

            stage.setScene(new Scene(root));
            stage.setTitle("Изменить");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
            stage.showAndWait();

            Plant changedPlant = addController.getPlant(); // считываем работника с формы добавления

            plantListView.getItems().add(selectedIdx, changedPlant);
            plants.add(selectedIdx, changedPlant);

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ничего не выбрано");
            alert.setContentText("Выберите растение из списка.");

            alert.showAndWait();
        }
    }

    public void onClickRequest(ActionEvent actionEvent) {
        Plant selectedPlant = plantListView.getSelectionModel().getSelectedItem();
        if (selectedPlant != null) {
            final int selectedIdx = plantListView.getSelectionModel().getSelectedIndex();

            Request requestInterface = plants.get(selectedIdx);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запрос");
            alert.setHeaderText("Количество согласных букв в названии:");
            alert.setContentText("" + requestInterface.countConsonantsInThePlantName());

            alert.showAndWait();

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ничего не выбрано");
            alert.setContentText("Выберите растение из списка.");

            alert.showAndWait();
        }
    }

}
