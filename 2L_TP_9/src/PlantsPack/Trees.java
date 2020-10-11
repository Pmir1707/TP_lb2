package PlantsPack;

public class Trees extends Plants{
    private long age;

    public Trees(String name, long age) {
        super(name);
        this.age = age;
    }

    @Override
    public String info() {
        return super.info()
                + " возраст - "
                + age;
    }
}
