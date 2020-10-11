package WorkWithListPack;

import PlantsPack.Plants;
import PlantsPack.RequestInterface;

import java.util.ArrayList;

public class WorkWithList {

    private ArrayList<Plants> plants;//лист со всеми объектами

    public WorkWithList(ArrayList<Plants> plants) {
        this.plants = plants;
    }

    /* Добавление объекта */
    public void addPlant(Plants plantForAdding){
        plants.add(plantForAdding);
    }

    /* Изменение объекта */
    public void change(int idForChanging, Plants changedPlant){
        plants.set(idForChanging, changedPlant);
    }

    /* Запрос */
    public int executeRequest(int id){
        RequestInterface request = plants.get(id);
        return request.countConsonantsInThePlantName();
    }

    /* Вывод всех объектов*/
    public String printPlantsList(){
        int count = 0;
        String result = "";
        for (Plants plant:
             plants) {
            count++;
            result += count + ") " + plant.info() + "\n";
        }
        return result;
    }

}
