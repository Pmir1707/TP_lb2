package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.*;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    Stack<String> stackOfCards = new Stack<>();

    @FXML
    Label currentCardLabel; // Надпись для отображения колоды карт.

    @FXML
    ListView<String> hearts; // Черви ListView.

    @FXML
    ListView<String> spades; // Пики ListView.

    @FXML
    ListView<String> diamonds; // Буби ListView.

    @FXML
    ListView<String> clubs; // Крести ListView.

    // Создаем коллекции-хранилища для каждого ListView.
    ObservableList<String> heartsItems = FXCollections.observableArrayList();
    ObservableList<String> spadesItems = FXCollections.observableArrayList();
    ObservableList<String> diamondsItems = FXCollections.observableArrayList();
    ObservableList<String> clubsItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Соединяем наши ListView с соответствующими хранилищами.
        hearts.setItems(heartsItems);
        spades.setItems(spadesItems);
        diamonds.setItems(diamondsItems);
        clubs.setItems(clubsItems);

        // Привязываем обработчик зажатия мыши над списком.
        currentCardLabel.setOnDragDetected(this::onDragDetected);

        // Привязываем обработчик движения мыши над списком.
        hearts.setOnDragOver(this::onListViewDragOver);
        spades.setOnDragOver(this::onListViewDragOver);
        diamonds.setOnDragOver(this::onListViewDragOver);
        clubs.setOnDragOver(this::onListViewDragOver);

        // Привязываем обработчик отпускания мыши над списком.
        hearts.setOnDragDropped(this::onListViewDragDropped);
        spades.setOnDragDropped(this::onListViewDragDropped);
        diamonds.setOnDragDropped(this::onListViewDragDropped);
        clubs.setOnDragDropped(this::onListViewDragDropped);

        fillCardDeck();
        setCurrentCardLabel();
    }

    public void onDragDetected(MouseEvent mouseEvent) {
        Node sourceNode = (Node) mouseEvent.getSource();
        Dragboard db = sourceNode.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString("");
        db.setContent(content);

        mouseEvent.consume();
    }

    public void onListViewDragOver(DragEvent dragEvent) {
        String labelText = currentCardLabel.getText();
        if (
                labelText.contains("Пики") && dragEvent.getSource() == spades ||
                labelText.contains("Черви") && dragEvent.getSource() == hearts ||
                labelText.contains("Буби") && dragEvent.getSource() == diamonds ||
                labelText.contains("Крести") && dragEvent.getSource() == clubs) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void onListViewDragDropped(DragEvent dragEvent) {
        // Над каким listView отпустили.
        ListView<String> targetListView = (ListView) dragEvent.getGestureTarget();

        // C какой надписи тянули.
        Label sourceLabel = (Label) dragEvent.getGestureSource();

        // Добавляем текст надписи в ListView.
        targetListView.getItems().add(sourceLabel.getText());

        // Берем из колоды следующую карту и кладем ее в надпись.
        setCurrentCardLabel();
    }

    public void setCurrentCardLabel() {

        // Перемешиваем нашу колоду.
        currentCardLabel.setText("  Колода пуста!  ");
        Collections.shuffle(stackOfCards);

        // Кладем 1 карту в надпись, не удаляя ее из колоды.
        currentCardLabel.setText("  " + stackOfCards.pop() + "  ");
    }

    public void fillCardDeck() {

        stackOfCards.add("6 Черви");
        stackOfCards.add("6 Пики");
        stackOfCards.add("6 Буби");
        stackOfCards.add("6 Крести");

        stackOfCards.add("7 Черви");
        stackOfCards.add("7 Пики");
        stackOfCards.add("7 Буби");
        stackOfCards.add("7 Крести");

        stackOfCards.add("8 Черви");
        stackOfCards.add("8 Пики");
        stackOfCards.add("8 Буби");
        stackOfCards.add("8 Крести");

        stackOfCards.add("9 Черви");
        stackOfCards.add("9 Пики");
        stackOfCards.add("9 Буби");
        stackOfCards.add("9 Крести");

        stackOfCards.add("10 Черви");
        stackOfCards.add("10 Пики");
        stackOfCards.add("10 Буби");
        stackOfCards.add("10 Крести");

        stackOfCards.add("Валет Черви");
        stackOfCards.add("Валет Пики");
        stackOfCards.add("Валет Буби");
        stackOfCards.add("Валет Крести");

        stackOfCards.add("Дама Черви");
        stackOfCards.add("Дама Пики");
        stackOfCards.add("Дама Буби");
        stackOfCards.add("Дама Крести");

        stackOfCards.add("Король Черви");
        stackOfCards.add("Король Пики");
        stackOfCards.add("Король Буби");
        stackOfCards.add("Король Крести");

        stackOfCards.add("Туз Черви");
        stackOfCards.add("Туз Пики");
        stackOfCards.add("Туз Буби");
        stackOfCards.add("Туз Крести");
    }
}
