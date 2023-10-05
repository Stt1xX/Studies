package Storage.StorageData;

import java.io.Serializable;

public class Person implements Serializable {
    public Person(){

    }
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Country nationality; //Поле не может быть null
    private Location location; //Поле может быть null

    public Person(String name, Float height, Color eyeColor, Country nationality, Location location){
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
        this.nationality = nationality;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Float getHeight() {
        return height;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return getName() + "\nРост: " + getHeight() + "\nЦвет глаз: " + getEyeColor() + "\nРодная страна: " +
                getNationality() + "\nЛокация: " + getLocation();
    }
}
