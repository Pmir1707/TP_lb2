package sample.Hierarchy;

public class Trees extends Plant{
    private double age;

    public Trees(String name, double age) {
        super(name);
        this.age = age;
    }

    public Trees() {
    }

    @Override
    public String toString() {
        return super.toString()
                + ", возраст - "
                + age;
    }

    @Override
    public String info() {
        return super.info()
                + " возраст - "
                + age;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }
}
