package MenuPack;

import InputInterface.InputInterface;
import PlantsPack.Plants;
import PlantsPack.Shrubs;
import PlantsPack.Trees;
import WorkWithListPack.WorkWithList;

import java.util.ArrayList;

public class Menu{

    public static void start(){

        ArrayList<Plants> plants = new ArrayList<>();

        WorkWithList work = new WorkWithList(plants);

        work.addPlant(new Trees("Дерево", 20));
        work.addPlant(new Shrubs("Кустарник", 4));

        boolean flag = false; // переменная для цикла. Если = true, то выход из программы
        while (!flag) {
            System.out.println("\nВыберите задание: \n[1] - добавить\n" +
                    "[2] - изменить\n[3] - просмотр\n" +
                    "[4] - запрос\n[5] - выход");

            switch (InputInterface.inputInteger("")) {
                case 1://добавить
                    work.addPlant(fill());
                    System.out.println("Растение добавлено");
                    break;
                case 2://изменить
                    work.change(findPlant(plants), fill());
                    break;
                case 3://просмотр
                    System.out.println("Все растения:");
                    System.out.println(work.printPlantsList());
                    break;
                case 4://запрос
                    System.out.println("Количество согласных в названии = "
                            + work.executeRequest(findPlant(plants)));
                    break;
                case 5://выxод
                    System.out.println("[Выход]");
                    flag = true;
                    break;
                default:
                    System.out.println("Ошибка. Повторите ввод.");
                    break;
            }

        }
    }

    /*Ввод объекта*/
    public static Plants fill(){
        String name = InputInterface.inputString("Название: ");

        int type = InputInterface.inputInteger("Нажмите [1], если это дерево, [2] - кустарник: ");
        if (type == 1){
            int age = InputInterface.inputInteger("возраст: ");
            return new Trees(name, age);

        } else {
            int month = InputInterface.inputInteger("месяц цветения: ");
            return new Shrubs(name, month);
        }
    }

    /* Поиск растения */
    public static int findPlant(ArrayList<Plants> plants){
        int id = InputInterface.inputInteger("Введите номер растения: ");

        if (id > plants.size())
            id = InputInterface.inputInteger("Такого номера нет.");

        System.out.println("Искомое растение: " + plants.get(id-1).info());

        return id-1;
    }

}
