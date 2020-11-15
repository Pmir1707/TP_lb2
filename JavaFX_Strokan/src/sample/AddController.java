package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Hierarchy.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    private TextField txtName, txtAge, txtMonthOfFlowering;

    @FXML
    private RadioButton rbShrub, rbTree;

    @FXML
    private Button btnOk;

    @FXML
    private Label lFlowering, lAge;

    private boolean isClickedOk; // нажата ли кнопка ОК

    @Override
    public void initialize(URL location, ResourceBundle resources){
        isClickedOk = false; // ставим false, т.к. еще не нажата кнопка

        // Помещаем радиобаттоны в группу (чтобы можно было кликнуть только один)
        ToggleGroup group = new ToggleGroup();

        rbShrub.setToggleGroup(group);
        rbTree.setToggleGroup(group);

        lFlowering.setVisible(false);
        lAge.setVisible(false);
        txtMonthOfFlowering.setVisible(false);
        txtAge.setVisible(false);
    }

    public void addPlant(){
        // проверяем на корректный ввод
        if (!checkCorrectInput().equals("")){
            // показываем окно с  ошибкой
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Некорректный ввод");
            alert.setContentText(checkCorrectInput());

            alert.showAndWait();
            return;
        }

        isClickedOk = true; // ставим true, т.к. нажали "Ок"
        // закрываем
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    private String checkCorrectInput(){
        String res ="";

        if (isEmpty(txtName))
            res = "Введите Имя";

        // если выбрано дерево
        if (rbTree.isSelected()){
            if (isEmpty(txtAge))
                res = "Введите возраст дерева";

            else if (!isDouble(txtAge.getText()))
                res = "Некорректный ввод возраста дерева";
        }
        // если выбран кустарник
        else if (rbShrub.isSelected()){
            if (isEmpty(txtMonthOfFlowering))
                res = "Введите месяц цветения";

            else if (!isDouble(txtMonthOfFlowering.getText()))
                res = "Некорректный ввод месяца цветения";

        } else
            res = "Выберите вид растения";

        return res;
    }

    private boolean isEmpty(TextField txt){
        if (txt.getText().equals(""))
            return true;
        return false;
    }

    public Plant getPlant(){
        String name = txtName.getText();

        if (rbTree.isSelected()){
            double age = Double.parseDouble(txtAge.getText());
            return new Trees(name, age);
        } else {
            double floweringMonth = Double.parseDouble(txtMonthOfFlowering.getText());
            return new Shrubs(name, floweringMonth);
        }
    }

    public void setPlant(Plant plant){
        txtName.setText(plant.getName());

        if (plant instanceof Trees){ // если это объект класса Trees
            rbTree.setSelected(true);
            lAge.setVisible(true);
            txtAge.setVisible(true);
            Trees trees = (Trees) plant;
            txtAge.setText(trees.getAge() + "");
        } else { // если это объект класса Shrubs
            rbShrub.setSelected(true);
            lFlowering.setVisible(true);
            txtMonthOfFlowering.setVisible(true);
            Shrubs shrubs = (Shrubs) plant;
            txtMonthOfFlowering.setText(shrubs.getFloweringMonth() + "");
        }
    }

    private static boolean isDouble(String str) // функция для проверки на возможность перевода строки в значение типа double
    {
        try
        {
            double d = Double.parseDouble(str); //преобразовываем
        }
        catch(NumberFormatException nfe) //если возникает исключение
        {
            return false;
        }
        return true;
    }

    public boolean isClickedOk() {
        return isClickedOk;
    }

    public void setClickedOk(boolean clickedOk) {
        isClickedOk = clickedOk;
    }

    public void onClickRb(ActionEvent actionEvent) {
        RadioButton clickedRb = (RadioButton) actionEvent.getSource(); // кликнутый батон

        // в соответствии с кликнутой кнопкой показываем/скрываем поля
        if (clickedRb.getId().equals("rbTree")){
            lFlowering.setVisible(false);
            lAge.setVisible(true);
            txtMonthOfFlowering.setVisible(false);
            txtAge.setVisible(true);
        } else  if (clickedRb.getId().equals("rbShrub")){
            lFlowering.setVisible(true);
            lAge.setVisible(false);
            txtMonthOfFlowering.setVisible(true);
            txtAge.setVisible(false);
        }

    }
}
