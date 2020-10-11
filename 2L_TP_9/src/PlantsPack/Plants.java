package PlantsPack;

public abstract class Plants implements RequestInterface{
    private String name;

    public Plants(String name) {
        this.name = name;
    }

    public String info() {
        return name + ", ";
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

}
