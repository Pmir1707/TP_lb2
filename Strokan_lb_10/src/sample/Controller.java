package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    spcTree origin;
    @FXML
    Button searchButton, addButton, customNumbersButton, randomNumbersButton, buildButton;
    @FXML
    TextField textFieldAddValue, textFieldNodeValue, textFieldSetSize;
    @FXML
    Label minimalChild, wideSearch, size, path;
    @FXML
    TreeView<Integer> treeList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        treeList.getSelectionModel().selectedItemProperty().addListener((changed, oldNode, newNode) -> {

            path.setText("");

            if (newNode != null && newNode.getValue() != -1) {

                String path = String.valueOf(newNode.getValue());
                TreeItem<Integer> parent = newNode.getParent();

                while (parent != null) {

                    path = parent.getValue() + " / " + path;
                    parent = parent.getParent();
                }

                this.path.setText("Путь до узла - " + path);
                this.path.setVisible(true);
            }
        });

        treeList.getSelectionModel().selectedItemProperty().addListener((changed, oldNode, newNode) -> {

            minimalChild.setText("");

            if (newNode != null && newNode.getValue() != -1) {

                if (newNode.getChildren().isEmpty()) {
                    minimalChild.setText("У данного узла нет потомков меньших по значению");
                }
                else {

                    TreeItem<Integer> currItem = newNode;
                    TreeItem<Integer> currItemCheck = newNode;

                    while (!currItem.getChildren().isEmpty()) {

                        if (currItem.getChildren().get(0).getValue() != -1)

                            currItem = currItem.getChildren().get(0);
                        else {

                            minimalChild.setText("У данного узла нет потомков меньших по значению");
                            break;
                        }
                    }

                    if (currItem != currItemCheck)

                        minimalChild.setText("Потомок с минимальным значением - " + String.valueOf(currItem.getValue()));

                    else minimalChild.setText("У данного узла нет потомков меньших по значению");
                }
            }
        });

        treeList.getSelectionModel().selectedItemProperty().addListener((changed, oldNode, newNode) -> {

            wideSearch.setText("");

            if (newNode != null && newNode.getValue() != -1) {

                int i = 0;

                TreeItem<Integer> parent = newNode.getParent();

                while (parent != null) {
                    i++;
                    parent = parent.getParent();
                }

                Queue<TreeItem> queue = new LinkedList<>();

                if (i == 0) {
                    wideSearch.setText(String.valueOf(origin.getValue()));
                }
                else {

                    queue.add(treeList.getRoot());
                    ArrayList<TreeItem> values = new ArrayList<>();

                    while (!queue.isEmpty()) {

                        TreeItem<Integer> item = queue.remove();

                        if (item.getValue() == -1) continue;

                        if (!item.getChildren().isEmpty()) {

                            queue.add(item.getChildren().get(0));
                        }

                        if (item.getChildren().size() > 1) {

                            queue.add(item.getChildren().get(1));
                        }

                        if (returnParentAmount(item) == i) {

                            values.add(item);
                        }
                    }

                    wideSearch.setText("Узлы того же уровня: " + returnElements(values));
                }
            }
        });
    }

    private int returnParentAmount(TreeItem item) {

        int i = 0;
        TreeItem<Integer> parent = item.getParent();

        while (parent != null) {

            i++;
            parent = parent.getParent();
        }
        return i;
    }

    private String returnElements(List<TreeItem> q) {

        String s = "";

        for (TreeItem el : q) {

            s += el.getValue() + " ";
        }
        return s;
    }

    public void tryToFind(ActionEvent actionEvent) {

        try {

            if (Integer.parseInt(textFieldNodeValue.getText()) == origin.getValue()) {

                treeList.getSelectionModel().select(origin);

            } else if (Integer.parseInt(textFieldNodeValue.getText()) < origin.getValue()) {

                tryToFindElement(origin.getChildren().get(0), Integer.parseInt(textFieldNodeValue.getText()));
            }
            else if (Integer.parseInt(textFieldNodeValue.getText()) > origin.getValue()) {

                tryToFindElement(origin.getChildren().get(1), Integer.parseInt(textFieldNodeValue.getText()));
            }

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Введите число!");
            alert.showAndWait();
        }
    }

    public void tryToAdd(ActionEvent actionEvent) {

        try {

            spcTree.tryToAddNodule(origin, Integer.parseInt(textFieldAddValue.getText()));
            spcTree.tryToDeleteEmptyNodule(origin);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Узел " + textFieldAddValue.getText() + " добавлен!");
            alert.showAndWait();
            textFieldAddValue.setText("");

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Введите число!");
            alert.showAndWait();
        }
    }

    public void tryToCreateForRandomNumbers(ActionEvent actionEvent) {

        size.setVisible(true);
        textFieldSetSize.setVisible(true);
        buildButton.setVisible(true);
    }

    public void tryToCreateForThisNumbers(ActionEvent actionEvent) {

        Queue<Integer> items = new LinkedList<>();
        Integer[] array = new Integer[]{50, 100, 150, 200, 250, 300, 350};

        for (int el : array) {

            items.add(el);
        }
        origin = new spcTree(items.remove());

        while (!items.isEmpty()) {

            spcTree.tryToAddNodule(origin, items.remove());
        }

        size.setVisible(false);
        textFieldSetSize.setVisible(false);
        buildButton.setVisible(false);
        spcTree.tryToDeleteEmptyNodule(origin);
        treeList.setRoot(origin);
        origin.setExpanded(true);
        customNumbersButton.setDisable(true);
        randomNumbersButton.setDisable(false);
        textFieldAddValue.setVisible(true);
        addButton.setVisible(true);
        textFieldNodeValue.setVisible(true);
        searchButton.setVisible(true);
    }

    public void tryToCreateTree(ActionEvent actionEvent) {

        try {

            Integer[] arr = new Integer[Integer.parseInt(textFieldSetSize.getText())];
            Random rand = new Random();

            for (int i = 0; i < arr.length; i++){

                arr[i] = rand.nextInt(31);
            }

            Queue<Integer> itemsValue = new LinkedList<>();

            for (int el : arr) {

                itemsValue.add(el);
            }
            origin = new spcTree(itemsValue.remove());

            while (!itemsValue.isEmpty()) {

                spcTree.tryToAddNodule(origin, itemsValue.remove());
            }

            spcTree.tryToDeleteEmptyNodule(origin);
            treeList.setRoot(origin);
            origin.setExpanded(true);
            randomNumbersButton.setDisable(true);
            customNumbersButton.setDisable(false);
            textFieldSetSize.setText("");
            textFieldSetSize.setVisible(false);
            buildButton.setVisible(false);
            size.setVisible(false);
            textFieldAddValue.setVisible(true);
            addButton.setVisible(true);
            textFieldNodeValue.setVisible(true);
            searchButton.setVisible(true);

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Введите число!");
            alert.showAndWait();
        }
    }

    private void tryToFindElement(TreeItem<Integer> treeItem, int value) {

        try {

            if (value == treeItem.getValue()) {

                treeList.getSelectionModel().select(treeItem);
            }
            else if (value < treeItem.getValue()) {

                tryToFindElement(treeItem.getChildren().get(0), value);
            }
            else if (value > treeItem.getValue()) {

                tryToFindElement(treeItem.getChildren().get(1), value);
            }
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Элемент с значением " + String.valueOf(value) + " не найден!");
            alert.showAndWait();
        }
    }
}