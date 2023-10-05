package Storage.StorageData;

import java.io.Serializable;

public class Location implements Serializable {
    public Location(){

    }
    private float x;
    private float y;
    private Integer z; //Поле не может быть null
    private String name; //Длина строки не должна быть больше 870, Поле не может быть null

    public Location(float x, float y, Integer z, String name){
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Integer getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName() + "\nКоординаты локации:\nx = " + getX() + " y = " + getY() + " z = " + getZ();
    }
}
