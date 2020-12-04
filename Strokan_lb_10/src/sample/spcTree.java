package sample;

import javafx.scene.control.TreeItem;
import java.util.LinkedList;
import java.util.Queue;

public class spcTree extends TreeItem<Integer> {

    spcTree rightSide;
    spcTree leftSide;

    public spcTree(int value) {
        super(value);
        getChildren().add(0, new TreeItem<>(-1));
        getChildren().add(1, new TreeItem<>(-1));
    }

    public static void tryToDeleteEmptyNodule(TreeItem origin) {

        Queue<TreeItem> queue = new LinkedList<>();
        queue.add(origin);
        while (!queue.isEmpty()) {

            TreeItem<Integer> nodule = queue.remove();

            if (
                    !nodule.getChildren().isEmpty()
                            && (nodule.getChildren().get(0).getValue() != -1
                            || nodule.getChildren().get(1).getValue() != -1))
            {
                queue.add(nodule.getChildren().get(0));
                queue.add(nodule.getChildren().get(1));
            }

            if (
                    !nodule.getChildren().isEmpty()
                            && nodule.getChildren().get(0).getValue() == -1
                            && nodule.getChildren().get(1).getValue() == -1)
            {
                nodule.getChildren().clear();
            }
        }
    }

    public static spcTree tryToAddNodule(spcTree treeList, int value) {

        if (treeList == null) {
            return new spcTree(value);
        }

        if (
                value < treeList.getValue() &&
                        !treeList.getChildren().isEmpty())
        {
            treeList.leftSide = tryToAddNodule(treeList.leftSide, value);
            treeList.getChildren().remove(0);
            treeList.getChildren().add(0, treeList.leftSide);
        }

        else if (
                value > treeList.getValue() &&
                        !treeList.getChildren().isEmpty())
        {
            treeList.rightSide = tryToAddNodule(treeList.rightSide, value);
            treeList.getChildren().remove(1);
            treeList.getChildren().add(1, treeList.rightSide);
        }

        else if (
                value < treeList.getValue() &&
                        treeList.getChildren().isEmpty())
        {
            treeList.getChildren().add(0, new TreeItem<>(-1));
            treeList.getChildren().add(1, new TreeItem<>(-1));
            treeList.leftSide = tryToAddNodule(treeList.leftSide, value);
            treeList.getChildren().remove(0);
            treeList.getChildren().add(0, treeList.leftSide);
        }

        else if (
                value > treeList.getValue() &&
                        treeList.getChildren().isEmpty())
        {
            treeList.getChildren().add(0, new TreeItem<>(-1));
            treeList.getChildren().add(1, new TreeItem<>(-1));
            treeList.rightSide = tryToAddNodule(treeList.rightSide, value);
            treeList.getChildren().remove(1);
            treeList.getChildren().add(1, treeList.rightSide);
        }

        else {
            return treeList;
        }
        return treeList;
    }
}