package sample.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PersonModel {
    ArrayList<Person> personList = new ArrayList<>();
    private int counter = 1;
    Class<? extends Person> personFilter = Person.class;

    public void saveToFile(String path) {
        try (Writer writer =  new FileWriter(path)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerFor(new TypeReference<ArrayList<Person>>() { })
                    .withDefaultPrettyPrinter()
                    .writeValue(writer, personList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadToFile(String path) {
        try (Reader reader =  new FileReader(path)) {
            ObjectMapper mapper = new ObjectMapper();
            personList = mapper.readerFor(new TypeReference<ArrayList<Person>>() { })
                    .readValue(reader);
            this.counter = personList.stream()
                    .map(person -> person.id)
                    .max(Integer::compareTo)
                    .orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.emitDataChanged();
    }

    public interface DataChangedListener {
        void dataChanged(ArrayList<Person> personList, ArrayList<String> sp);
    }

    private ArrayList<DataChangedListener> dataChangedListener = new ArrayList<>();

    public void addDataChangedListener(DataChangedListener listener) {
        this.dataChangedListener.add(listener);
    }

    public void load() {

        personList.clear();
        this.add(new Man("Блажев", "Евгений", "01.01.2000", "не женат", "м", 60, true), false);
        this.add(new Family("Ведерников", "Даниил", "02.01.2000", "женат", "м", 1, Family.Temperament.choleric), false);
        this.add(new Woman("Артемьева", "Ольга", "03.01.2000", "не замужем", "ж", 61, 80, 88), false);
        this.add(new Man("Гордин", "Андрей", "04.01.2000", "не женат", "м", 110, true), false);
        this.add(new Family("Богданова", "Татьяна", "05.01.2000", "замужем", "ж", 1, Family.Temperament.sanguine), false);
        this.add(new Woman("Лоншакова", "Кристина", "06.01.2000", "замужем", "ж", 53, 82, 81), false);
        this.add(new Man("Губарев", "Михаил", "07.01.2000", "женат", "м", 57, true), false);
        this.add(new Family("Попова", "Валерия", "08.01.2000", "замужем", "ж", 2, Family.Temperament.phlegmatic), false);
        this.add(new Woman("Портенко", "Анастасия", "09.01.2000", "не замужем", "ж", 55, 80, 80), false);
        this.add(new Family("Задбоев", "Илья", "10.01.2000", "женат", "м", 5, Family.Temperament.melancholic), false);

        this.emitDataChanged();
    }

    public void delete(int id)
    {
        for (int i = 0; i< this.personList.size(); ++i) {
            if (this.personList.get(i).id == id) {
                this.personList.remove(i);
                break;
            }
        }

        this.emitDataChanged();
    }

    private void emitDataChanged() {
        for (DataChangedListener listener: dataChangedListener) {
            ArrayList<Person> filteredList = new ArrayList<>(
                    personList.stream() // запускаем стрим
                            .filter(person -> personFilter.isInstance(person)) // фильтруем по типу
                            .collect(Collectors.toList()) // превращаем в список
            );
            listener.dataChanged(filteredList, this.getSp());
        }
    }

    public ArrayList<String> getSp() {
        ArrayList<String> sp = this.personList.stream()
                .map(persons -> persons.getSp())
                .distinct()
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
        sp.add(0,null);
        return sp;
    }

    public void add(Person person, boolean emit) {
        person.id = counter;
        counter += 1;
        this.personList.add(person);
        if (emit) {
            this.emitDataChanged();
        }
    }
    public void add(Person person) {
        add(person, true);
    }

    public void edit(Person person) {
        for (int i = 0; i< this.personList.size(); ++i) {
            if (this.personList.get(i).id == person.id) {
                this.personList.set(i, person);
                break;
            }
        }
        this.emitDataChanged();
    }

    public void setPersonFilter(Class<? extends Person> personFilter) {
        this.personFilter = personFilter;
        this.emitDataChanged();
    }

}

