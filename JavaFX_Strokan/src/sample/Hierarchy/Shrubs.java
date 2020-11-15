package sample.Hierarchy;

public class Shrubs extends Plant{ //кустарники
    private double floweringMonth; // месяц цветения

    public Shrubs(String name, double floweringMonth) {
        super(name);
        this.floweringMonth = floweringMonth;
    }

    public Shrubs() {
    }

    @Override
    public String toString() {
        return super.toString()
                + ", месяц цветения - "
                + floweringMonth;
    }

    @Override
    public String info() {
        return super.info()
                + " месяц цветения - "
                + floweringMonth;
    }

    public double getFloweringMonth() {
        return floweringMonth;
    }

    public void setFloweringMonth(double floweringMonth) {
        this.floweringMonth = floweringMonth;
    }
}
