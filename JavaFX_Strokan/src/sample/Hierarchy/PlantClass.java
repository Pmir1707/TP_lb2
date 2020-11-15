package sample.Hierarchy;

import java.util.ArrayList;

public class PlantClass {
    private ArrayList<Plant> plants;//лист со всеми объектами

    public PlantClass() {
    }

    public PlantClass(ArrayList<Plant> plants) {
        this.plants = plants;
    }

    public void addPlant(Plant plant){
        plants.add(plant);
    }

    public void changePlant(int id, Plant changedPlant){
        plants.set(id, changedPlant);
    }

    public void removePlant(Plant plant){
        plants.remove(plant);
    }

    public void removePlant(int id){
        plants.remove(id);
    }

    public boolean isPlant(Plant plant){
        for (Plant el:
                plants) {
            if (el == plant)
                return true;
        }
        return false;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
    }
}
