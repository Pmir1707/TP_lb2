package sample.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public TableView mainTable;
    public ChoiceBox cmbPersonType;
    ObservableList<Class<? extends Person>> personTypes = FXCollections.observableArrayList(
            Person.class,
            Man.class,
            Family.class,
            Woman.class
    );
    PersonModel personModel = new PersonModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        personModel.addDataChangedListener((persons, sp) -> {
            mainTable.setItems(FXCollections.observableArrayList(persons));
            ObservableList<String> newList = FXCollections.observableArrayList(sp);
        });

        TableColumn<Person, String> surnameColumn = new TableColumn<>("Фамилия");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Person, String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Person, String> bdayColumn = new TableColumn<>("Дата рождения");
        bdayColumn.setCellValueFactory(new PropertyValueFactory<>("bday"));

        TableColumn<Person, String> spColumn = new TableColumn<>("Семейное положение");
        spColumn.setCellValueFactory(new PropertyValueFactory<>("sp"));

        TableColumn<Person, String> genderColumn = new TableColumn<>("Пол");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Person, String> descriptionColumn = new TableColumn<>("Описание");
        descriptionColumn.setPrefWidth(600);
        descriptionColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDescription());
        });

        cmbPersonType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.personModel.setPersonFilter((Class<? extends Person>) newValue);
        });

        mainTable.getColumns().addAll(surnameColumn, nameColumn, bdayColumn, spColumn,genderColumn, descriptionColumn);

        cmbPersonType.setItems(personTypes);
        cmbPersonType.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class object) {
                if (Person.class.equals(object)) {
                    return "Все";
                } else if (Man.class.equals(object)) {
                    return "Мужчина";
                } else if (Family.class.equals(object)) {
                    return "Семейный человек";
                } else if (Woman.class.equals(object)) {
                    return "Женщина";
                }
                return null;
            }

            @Override
            public Class fromString(String string) {
                return null;
            }
        });
    }

    public void onAddClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PersonForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.mainTable.getScene().getWindow());
        PersonFormController controller = loader.getController();
        controller.personModel = personModel;
        stage.showAndWait();
    }

    public void onEditClick(ActionEvent actionEvent) throws IOException {
        Person person = (Person) this.mainTable.getSelectionModel().getSelectedItem();
        if (person != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PersonForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.mainTable.getScene().getWindow());

            PersonFormController controller = loader.getController();
            controller.setPerson((Person) this.mainTable.getSelectionModel().getSelectedItem());
            controller.personModel = personModel;

            stage.showAndWait();
        } else {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Ошибка");
            alert1.setHeaderText("Ничего не выбрано");
            alert1.setContentText("Выберите человека из списка.");

            alert1.showAndWait();
        }
    }

    public void onDeleteClick(ActionEvent actionEvent) {
        Person person = (Person) this.mainTable.getSelectionModel().getSelectedItem();
        if (person != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(String.format("Точно удалить %s?", person.getSurname()));
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                personModel.delete(person.id);
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Ошибка");
            alert1.setHeaderText("Ничего не выбрано");
            alert1.setContentText("Выберите человека из списка.");

            alert1.showAndWait();
        }
    }

    public void onSaveToFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные");
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showSaveDialog(mainTable.getScene().getWindow());

        if (file != null) {
            personModel.saveToFile(file.getPath());
        }
    }

    public void onLoadFromFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить данные");
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog(mainTable.getScene().getWindow());

        if (file != null) {
            personModel.loadToFile(file.getPath());
        }
    }

}
