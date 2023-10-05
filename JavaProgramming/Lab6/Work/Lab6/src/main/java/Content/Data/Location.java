package Content.Data;

import jakarta.xml.bind.annotation.XmlRootElement;

import javax.management.InvalidAttributeValueException;
import java.util.Objects;

@XmlRootElement
public class Location {

    private Long z;
    private Float y;
    private Float x;
    private String name; //Длина строки не должна быть больше 870, Поле не может быть null

    public Float getX() {
        return x;
    }

    public void setX(String x) throws InvalidAttributeValueException {
        setX(Float.parseFloat(x));
    }

    public void setX(Float x) throws InvalidAttributeValueException {
        if (!CheckData.checkFloat(x)){
            throw new InvalidAttributeValueException("");
        } else
            this.x = x;
    }

    public Float getY() {
        return y;
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

    public Long getZ() {
        return z;
    }

    public void setZ(String z) throws InvalidAttributeValueException {
        setZ(Long.parseLong(z));
    }

    public void setZ(Long z) throws InvalidAttributeValueException{
        if (!CheckData.checkLong(z)){
            throw new InvalidAttributeValueException("");
        } else
            this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidAttributeValueException {
        if (name == null || name.length() > 870)
            throw new InvalidAttributeValueException("Название локации не может отсутствовать.");
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location that = (Location) o;
        return Float.compare(that.x, x) == 0 && y == that.y && name.equals(that.name);
    } //???

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }

    @Override
    public String toString() {
        return  "x = " + x + ", y = " + y + ", z = " + z;
    }
}