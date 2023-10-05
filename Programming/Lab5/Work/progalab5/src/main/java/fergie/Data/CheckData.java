package fergie.Data;

public class CheckData {
    public static boolean checkFloat(Float f) {
        String IAVE;
        if (f == null) {
            System.out.println("Значение поля не может быть null");
            return false;
        } else if (f.equals(Float.POSITIVE_INFINITY)) {
            IAVE = "Число слишком большое, введите число меньшее, чем " + Float.MAX_VALUE;
            System.out.println(IAVE);
            return false;
        } else if ((f.equals(Float.NEGATIVE_INFINITY))) {
            IAVE = "Число слишком маленькое, введите число меньшее, чем " + Float.MIN_VALUE;
            System.out.println(IAVE);
            return false;
        } else
            return true;
    }
    public static boolean checkDouble(Double f) {
        String IAVE;
        if (f == null) {
            System.out.println("Значение поля не может быть null");
            return false;
        } else if (f.equals(Double.POSITIVE_INFINITY)) {
            IAVE = "Число слишком большое, введите число меньшее, чем " + Double.MAX_VALUE;
            System.out.println(IAVE);
            return false;
        } else if ((f.equals(Double.NEGATIVE_INFINITY))) {
            IAVE = "Число слишком маленькое, введите число меньшее, чем " + Double.MIN_VALUE;
            System.out.println(IAVE);
            return false;
        } else
            return true;
    }

    public static boolean checkLong(Long f) {
        if (f == null || f > Long.MAX_VALUE || f < Long.MIN_VALUE) {
            System.out.println("Значение поля не может быть null");
            return false;
        } else
            return true;
    }
}