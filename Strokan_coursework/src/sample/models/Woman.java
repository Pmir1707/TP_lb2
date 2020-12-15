package sample.models;

public class Woman extends Person {
    public double sWaist;
    public double sBreast;
    public double sHips;

    public Woman() {};

    public Woman(String surname, String name, String bday, String sp, String gender, double sWaist, double sBreast, double sHips) {
        super(surname, name, bday, sp, gender);
        this.sWaist = sWaist;
        this.sBreast = sBreast;
        this.sHips = sHips;
    }

    @Override
    public String getDescription() {
        String sWaistString = "Объем талии: "+this.sWaist+" см.";
        String sBreastString = " Объем груди: "+this.sBreast+" см.";
        String sHipsString = " Объем бедер: "+this.sHips+" см.";
        return String.format("Женщина. %s%s%s", sWaistString, sBreastString, sHipsString);
    }
}
