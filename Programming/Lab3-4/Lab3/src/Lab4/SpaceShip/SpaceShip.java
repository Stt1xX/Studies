package Lab4.SpaceShip;

public class SpaceShip {
    private final int manufactureYear;
    private final String color;
    private final boolean tuning;
    private final String name;

    private static final int maxPassengersCount = 35;
    private static final int maxSpeed = 4000;
    private static final int fuelUse = 80;
    private static final int price = 420;
    private static final int weight = 21000;
    private static final String size = "21м - длина; 4м - ширина; 2м - высота";
    public SpaceShip(String name, String color, int manufactureYear, boolean tuning){
        this.manufactureYear = manufactureYear;
        this.color = color;
        this.tuning = tuning;
        this.name = name;
    }

    public int getManufactureYear() {
        return this.manufactureYear;
    }

    public String getName() {
        return this.name;
    }

    public boolean getTuning(){
        return this.tuning;
    }

    public String getColor(){
        return this.color;
    }

    public static class Drawing {

        private final int id;

        private final String author;

        public Drawing(String author, int id){
            this.id = id;
            this.author = author;
        }

        public int getId(){
            return id;
        }

        public String getAuthor(){
            return author;
        }

        public static int getWeight(){
            return weight;
        }

        public static String getSize() {
            return size;
        }

        public static int getPasssengersCount() {

            return maxPassengersCount;
        }

        public static int getFuelUse() {

            return fuelUse;
        }

        public static int getMaxSpeed(){

            return maxSpeed;
        }

        public static int getPrice(){

            return price;
        }

        @Override
        public String toString() {
            return "Drawing{" +
                    "id=" + id + '}';
        }
    }
}
