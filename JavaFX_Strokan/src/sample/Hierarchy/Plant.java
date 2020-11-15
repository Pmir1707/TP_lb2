package sample.Hierarchy;

public abstract class Plant implements Request{
    private String name;

    public Plant(String name) {
        this.name = name;
    }

    public String info() {
        return name + ", ";
    }

    public Plant() {
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int countConsonantsInThePlantName(){
        int countOfAlphbetic = 0; //кол-во букв в названии
        for (char ch: // считаем кол-во букв
                name.toCharArray()) {
            if (Character.isAlphabetic(ch))
                countOfAlphbetic++;
        }

        // считаем все гласные и отнимаем их от общего кол-во букв
        String vowels = name.toLowerCase().replaceAll( "(?i)[^aeiouыаоэяиюуе]+", "" );
        return countOfAlphbetic - vowels.length();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
