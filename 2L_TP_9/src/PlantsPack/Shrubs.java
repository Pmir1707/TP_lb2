package PlantsPack;

public class Shrubs extends Plants{ //кустарники
    private int floweringMonth; // месяц цветения

    public Shrubs(String name, int floweringMonth) {
        super(name);
        this.floweringMonth = floweringMonth;
    }

    @Override
    public String info() {
        return super.info()
                + " месяц цветения - "
                + floweringMonth;
    }
}
