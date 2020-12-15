package sample.models;

public class Family extends Person{
    public int children; // число детей
    public enum Temperament {choleric, sanguine, phlegmatic, melancholic}
    public Temperament temperament;

    public Family() {};

    public Family(String surname, String name, String bday, String sp, String gender, int children, Temperament temperament) {
        super(surname, name, bday, sp, gender);
        this.children = children;
        this.temperament = temperament;
    }

    @Override
    public String getDescription() {
        String childrenString = " Число детей: "+this.children+".";
        String temperamentString = "";
        switch (this.temperament)
        {
            case choleric:
                temperamentString = " Холерик.";
                break;
            case sanguine:
                temperamentString = " Сангвиник.";
                break;
            case phlegmatic:
                temperamentString = " Флегматик.";
                break;
            case melancholic:
                temperamentString = " Меланхолик.";
                break;
        }
        return String.format("Семейный человек. %s%s", childrenString, temperamentString);
    }
}
