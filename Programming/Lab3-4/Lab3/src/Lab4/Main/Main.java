package Lab4.Main;

import Lab4.Exception.AllDieException;
import Lab4.Exception.NoPersonException;
import Lab4.SpaceShip.SpaceShip;
import Lab4.Time.Time;
import Lab4.People.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException, NoPersonException {

        ArrayList<Human> Humans = new ArrayList<>();
        Neznayka neznayka = new Neznayka("Незнайка", 100, 0, 0, -10, 20);
        Znayka znayka = new Znayka("Знайка", 100, 0,0, 10, 20);
        Znayka.Encyclopedia encyclopedia = new Znayka.Encyclopedia(4, -1);
        Neznayka.Glue glue = new Neznayka.Glue(-2,7);
        Kantik kantik = new Kantik("Кантик", 120, 0, 0, 3, 25);
        Kvantik kvantik = new Kvantik("Квантик", 120, 0, 0, 3, 25);
        Alpha alpha = new Alpha("Альфа", 75, 0, 0,8,20);
        Momega momega = new Momega("Момега", 150, 0,0,5,20);

        SpaceShip spaceShip = new SpaceShip("Салют-7", "белого", 1978,  true);
        SpaceShip.Drawing drawing = new SpaceShip.Drawing("С.П.Королева", 1);

        Time time = new Time();

        Humans.add(kantik);
        Humans.add(kvantik);
        Humans.add(alpha);
        Humans.add(momega);
        Humans.add(znayka);
        Humans.add(neznayka);
        Time.Humans =  Humans;

        if (Time.Humans.size() == 0){
            throw new NoPersonException("На кого то глазеть то?");
        }


        BeginEndSpaceTrip StartModule = new BeginEndSpaceTrip() {

            @Override
            public void description() {
                System.out.printf("Привет, дорогой друг! Добро пожаловать на корабль %s. Меня зовут Том, " +
                        "я введу тебя в курс дела.\nЭтот космический корабль %s цвета был построен в %s году" +
                        " по чертежу номер %d %s\nВот некоторые характеристики корабля:\nВес: %s кг\nГабориты: %s\n" +
                                "Максимальная вместимость пассажиров: %s чел\nСреднее потреблене топлива " +
                                "на 100 километров:%s кг\nМаксимальная скорость:%s км/ч\nНаличие тюнинга:%b\n" +
                                "Цена:%d млн\nВот и все, что я хотел тебе рассказать!\nУдачного полета!!!\n",
                        spaceShip.getName(), spaceShip.getColor(), spaceShip.getManufactureYear(),drawing.getId(),
                        drawing.getAuthor(), SpaceShip.Drawing.getWeight(), SpaceShip.Drawing.getSize(),
                        SpaceShip.Drawing.getPasssengersCount(), SpaceShip.Drawing.getFuelUse(),
                        SpaceShip.Drawing.getMaxSpeed(), spaceShip.getTuning(), SpaceShip.Drawing.getPrice());
            }
        };

        BeginEndSpaceTrip EndModule = new BeginEndSpaceTrip() {

            @Override
            public void description() {
                System.out.printf("Вы успешно продержались 100 дней на корабле %s, продолжайте" +
                        "в том же духе\nУдачи!!!", spaceShip.getName());
            }
        };

       StartModule.description();
        time.MainTimer(neznayka, znayka, kantik, kvantik, alpha, momega, encyclopedia, glue);
        EndModule.description();

    }
}
