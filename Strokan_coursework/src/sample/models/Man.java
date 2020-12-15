package sample.models;

public class Man extends Person {
    public int weight; // вес
    public boolean beard; // наличие бороды

    public Man() {};

    public Man(String surname, String name, String bday, String sp, String gender, int weight, boolean beard) {
        super(surname, name, bday, sp, gender);
        this.weight = weight;
        this.beard = beard;
    }

    @Override
    public String getDescription() {
        String weightString = "Вес "+this.weight+" кг.";
        String beardString = this.beard ? " Есть борода." : " Нет бороды.";
        return String.format("Мужчина. %s%s", weightString, beardString);
    }
}
