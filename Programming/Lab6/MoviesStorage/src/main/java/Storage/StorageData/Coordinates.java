package Storage.StorageData;

import java.io.Serializable;

public class Coordinates implements Serializable {

    public Coordinates(){

    }
    private int x; //Максимальное значение поля: 349
    private int y;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "\nx = " + getX() + ", y = " + getY();
    }
}
