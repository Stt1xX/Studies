package Content.Data;

import jakarta.xml.bind.annotation.XmlRootElement;

import javax.management.InvalidAttributeValueException;
import java.util.Objects;

@XmlRootElement
public class Coordinates {

    private Double x;

    private Float y; //Поле не может быть null

    public Double getX() {
        return x;
    }

    public void setX(String s) throws InvalidAttributeValueException {
        setX(Double.parseDouble(s));
    }

    public void setX(Double x) throws InvalidAttributeValueException{
        if (!CheckData.checkDouble(x)){
            throw new InvalidAttributeValueException("");
        } else
            this.x = x;
    }

    public Float getY() {
        return this.y;
    }

    public void setY(String y) throws InvalidAttributeValueException {
        setY(Float.parseFloat(y));
    }

    public void setY(Float y) throws InvalidAttributeValueException {
        if (!CheckData.checkFloat(y)){
            throw new InvalidAttributeValueException("");
        } else
            this.y = y;
    }
   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates th = (Coordinates) o;
        return (Double.compare((double)th, x) == 0) && (y == th.y);
    } */

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "x = " + x + ", y = " + y ;
    }


}