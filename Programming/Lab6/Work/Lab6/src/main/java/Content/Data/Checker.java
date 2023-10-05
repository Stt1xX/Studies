package Content.Data;

import javax.management.InvalidAttributeValueException;

/**
 * Setter validation class
 * @author FergieDoigrales/Vitya
 * @version 0.1
 * @throws InvalidAttributeValueException
 * @exceptions IllegalArgumentException, NumberFormatException, InvalidAttributeValueException
 */
public class Checker {
    public interface Setter {
        void set() throws InvalidAttributeValueException;
    }

    public static void checkData(Setter setter) {
        while (true) {
            try {
                setter.set();
                break;
            } catch (InvalidAttributeValueException e) {
                System.out.println(e.getMessage());
                System.out.println("Введите правильные данные: ");
            } catch (NumberFormatException e) {
                System.out.println("Введен неправильный тип данных.");
            } catch (IllegalArgumentException e) {
                System.out.println("Введено неправильное значение из справочника.");
            }
        }
    }

}