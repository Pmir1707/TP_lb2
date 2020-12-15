package sample.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonIgnoreProperties({"description"})
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")

public class Person {
    private String surname; //фамилия
    private String name; // имя
    private String bday; // дата рождения
    private String sp; // семейное положение
    private String gender; // пол
    public Integer id = null; //идентификатор

    public Person() {};

    public Person (String surname, String name, String bday, String sp, String gender) {
        this.setSurname(surname);
        this.setName(name);
        this.setBday(bday);
        this.setSp(sp);
        this.setGender(gender);
    }

    @Override
    public String toString() {
        return  String.format(this.getSurname(), this.getName(), this.getBday(), this.getSp(), this.getGender());
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return "";
    }
}
