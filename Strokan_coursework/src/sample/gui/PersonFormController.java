package sample.gui;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonFormController implements Initializable{

    public PersonModel personModel;

    public ChoiceBox cmbPersonType;
    public TextField txtSurname;
    public TextField txtName;
    public TextField txtBday;
    public TextField txtSp;
    public TextField txtGender;

    public VBox vboxMan;
    public TextField txtWeight;
    public CheckBox chkBeard;

    public VBox vboxFamily;
    public TextField txtChildren;
    public ChoiceBox cmbTemperamentType;

    public VBox vboxWoman;
    public TextField txtSWaist;
    public TextField txtSBreast;
    public TextField txtSHips;

    final String PERSON_MAN = "Мужчина";
    final String PERSON_FAMILY = "Семейный человек";
    final String PERSON_WOMAN = "Женщина";

    private Integer id = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cmbPersonType.setItems(FXCollections.observableArrayList(
                PERSON_MAN,
                PERSON_FAMILY,
                PERSON_WOMAN
        ));

        cmbPersonType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.vboxMan.setVisible(newValue.equals(PERSON_MAN));
            this.vboxMan.setManaged(newValue.equals(PERSON_MAN));
            this.vboxFamily.setVisible(newValue.equals(PERSON_FAMILY));
            this.vboxFamily.setManaged(newValue.equals(PERSON_FAMILY));
            this.vboxWoman.setVisible(newValue.equals(PERSON_WOMAN));
            this.vboxWoman.setManaged(newValue.equals(PERSON_WOMAN));
            updatePanes((String) newValue);
        });

        cmbTemperamentType.getItems().setAll(
                Family.Temperament.choleric,
                Family.Temperament.sanguine,
                Family.Temperament.phlegmatic,
                Family.Temperament.melancholic
        );

        cmbTemperamentType.setConverter(new StringConverter<Family.Temperament>() {
            @Override
            public String toString(Family.Temperament object) {
                switch (object){
                    case choleric:
                        return "Холерик";
                    case sanguine:
                        return "Сангвиник";
                    case phlegmatic:
                        return "Флегматик";
                    case melancholic:
                        return "Меланхолик";
                }
                return null;
            }

            @Override
            public Family.Temperament fromString(String s) {
                return null;
            }
        });
        cmbPersonType.setValue(PERSON_MAN);
        cmbTemperamentType.setValue(Family.Temperament.choleric);
        updatePanes(PERSON_MAN);
    }

    public void updatePanes(String value) {
        this.vboxMan.setVisible(value.equals(PERSON_MAN));
        this.vboxMan.setManaged(value.equals(PERSON_MAN));
        this.vboxFamily.setVisible(value.equals(PERSON_FAMILY));
        this.vboxFamily.setManaged(value.equals(PERSON_FAMILY));
        this.vboxWoman.setVisible(value.equals(PERSON_WOMAN));
        this.vboxWoman.setManaged(value.equals(PERSON_WOMAN));
    }

    public void onCancelClick(javafx.event.ActionEvent actionEvent) {
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void onSaveClick(javafx.event.ActionEvent actionEvent) {
        if (!checkCorrectInput().equals("")){
            // показываем окно с  ошибкой
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Некорректный ввод");
            alert.setContentText(checkCorrectInput());

            alert.showAndWait();
            return;
        } else {
            if (this.id != null) {
                Person person = getPerson();
                person.id = this.id;
                this.personModel.edit(person);
            } else {
                this.personModel.add(getPerson());
            }
        }
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void setPerson(Person person) {
        this.cmbPersonType.setDisable(person != null);
        this.id = person != null ? person.id : null;
        if (person != null) {
            this.txtSurname.setText(String.valueOf(person.getSurname()));
            this.txtName.setText(person.getName());
            this.txtBday.setText(person.getBday());
            this.txtSp.setText(person.getSp());
            this.txtGender.setText(person.getGender());

            if (person instanceof Man) { // если мужчина
                this.cmbPersonType.setValue(PERSON_MAN);
                this.txtWeight.setText(Integer.toString(((Man)person).weight)) ;
                this.chkBeard.setSelected(((Man) person).beard);
            } else if (person instanceof Family) { // если семейный человек
                this.cmbPersonType.setValue(PERSON_FAMILY);
                this.txtChildren.setText(Integer.toString(((Family)person).children));
                this.cmbTemperamentType.setValue(((Family) person).temperament);
            } else if (person instanceof Woman) { // если женщина
                this.cmbPersonType.setValue(PERSON_WOMAN);
                this.txtSWaist.setText(Integer.toString((int) ((Woman)person).sWaist));
                this.txtSBreast.setText(Integer.toString((int) ((Woman)person).sBreast));
                this.txtSHips.setText(Integer.toString((int) ((Woman)person).sHips));
            }
        }
    }

    public Person getPerson() {
        Person result = null;
        String surname = this.txtSurname.getText();
        String name = this.txtName.getText();
        String bday = this.txtBday.getText();
        String sp = this.txtSp.getText();
        String gender = this.txtGender.getText();

        switch ((String)this.cmbPersonType.getValue()) {
            case PERSON_MAN:
                Integer weight = Integer.valueOf(this.txtWeight.getText()) ;
                result = new Man(surname, name, bday, sp, gender, weight, this.chkBeard.isSelected());
                break;
            case PERSON_FAMILY:
                Integer children = Integer.valueOf(this.txtChildren.getText());
                result = new Family(surname, name, bday, sp, gender, children, (Family.Temperament)this.cmbTemperamentType.getValue());
                break;
            case PERSON_WOMAN:
                Integer sWaist =  Integer.valueOf(this.txtSWaist.getText());
                Integer sBreast =  Integer.valueOf(this.txtSBreast.getText());
                Integer sHips =  Integer.valueOf(this.txtSHips.getText());
                result = new Woman(surname, name, bday, sp, gender, sWaist, sBreast, sHips);
                break;
        }
        return result;
    }

    private String checkCorrectInput(){
        String res ="";

        if (isEmpty(txtSurname))
            res = "Введите фамилию";
        if (isEmpty(txtName))
            res = "Введите имя";
        if (isEmpty(txtBday))
            res = "Введите дату рождения";
        if (isEmpty(txtSp))
            res = "Введите семейное положение";
        if (isEmpty(txtGender))
            res = "Введите пол";

        // если выбран мужчина
        if (this.cmbPersonType.getValue() == PERSON_MAN){
            if (isEmpty(txtWeight))
                res = "Введите вес";
            else if (!isInteger(txtWeight.getText()))
                res = "Некорректный ввод веса";
        }
        // если выбран семейный человек
        else if (this.cmbPersonType.getValue() == PERSON_FAMILY) {
            if (isEmpty(txtChildren))
                res = "Введите кол-во детей";
            else if (!isInteger(txtChildren.getText()))
                res = "Некорректный ввод кол-ва детей";
        }
        // если выбрана женщина
        else if (this.cmbPersonType.getValue() == PERSON_WOMAN){
            if (isEmpty(txtSWaist))
                res = "Введите объем талии";
            else if (!isInteger(txtSWaist.getText()))
                res = "Некорректный ввод объема талии";
            if (isEmpty(txtSBreast))
                res = "Введите объем груди";
            else if (!isInteger(txtSBreast.getText()))
                res = "Некорректный ввод объема груди";
            if (isEmpty(txtSHips))
                res = "Введите объем бедер";
            else if (!isInteger(txtSHips.getText()))
                res = "Некорректный ввод объема бедер";
        }
        return res;
    }

    private boolean isEmpty(TextField txt){
        if (txt.getText().equals(""))
            return true;
        return false;
    }

    private static boolean isInteger(String str)
    {
        try
        {
            int d = Integer.parseInt(str); //преобразовываем
        }
        catch(NumberFormatException nfe) //если возникает исключение
        {
            return false;
        }
        return true;
    }

}
