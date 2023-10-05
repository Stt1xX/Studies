package Lab4.Actions;

import Lab4.People.Human;

public class LeisureActivitys {


    public static void watchTV(Human human){

        class TV{
            final String[] ArrOfProgram = {"передачу Пусть говорят", "Смешариков", "Лунтика",
                    "передачу Мужское & Женское"};
            final int plusFatigue;
            final int plusMood;
            final int plusIntellect;
            public TV(int plusFatigue, int plusMood, int plusIntellect){

                this.plusFatigue = plusFatigue;
                this.plusIntellect = plusIntellect;
                this.plusMood = plusMood;
            }
        }
        TV tv = new TV(2 + (int) (Math.random()* 2), 2 + (int) (Math.random() * 2),
                2 + (int) (Math.random() * 2));

        human.setFatigue(human.getFatigue() + tv.plusFatigue);
        human.setMood(human.getMood() + tv.plusMood);
        human.setIntellect(human.getIntellect() + tv.plusIntellect);
        System.out.printf("Сегодня этот плут %s посмотрел на телевизоре  %s%n",
                human.getName(), tv.ArrOfProgram[(int) (Math.random() * 3)]);

    }

    public static void readbook(Human human){

        class Book{
            final String[] ArrOfBook= {"роман Льва Толстого \"Война и мир\"",
                    "Повесть Николая Гоголя \"Тарас Бульба\"", "Исторический роман Александра Пушкина " +
                    "\"Капитанская дочка\"", " Роман Михаила Лермонтова \"Герой нашего времени\""};

            final int plusFatigue;
            final int plusMood;
            final int plusIntellect;
            public Book(int plusFatigue, int plusMood, int plusIntellect){

                this.plusFatigue = plusFatigue;
                this.plusIntellect = plusIntellect;
                this.plusMood = plusMood;
            }
        }
        Book book = new Book(1 + (int) (Math.random()* 2), (int) (Math.random() * 2),
                2 + (int) (Math.random() * 2));

        human.setFatigue(human.getFatigue() + book.plusFatigue);
        human.setMood(human.getMood() + book.plusMood);
        human.setIntellect(human.getIntellect() + book.plusIntellect);
        System.out.printf("Сегодня этот комочек %s почитал  %s%n", human.getName(),
                book.ArrOfBook[(int) (Math.random() * 3)]);

    }

    public static void programming(Human human){

        class Language{
            final String[] ArrOfBook= {"java", "python", "C++", "Assembler"};

            final int plusFatigue;
            final int plusMood;
            final int plusIntellect;
            public Language(int plusFatigue, int plusMood, int plusIntellect){

                this.plusFatigue = plusFatigue;
                this.plusIntellect = plusIntellect;
                this.plusMood = plusMood;
            }
        }
        Language language = new Language(8 + (int) (Math.random()* 4), 8 + (int) (Math.random() * 4),
                12 + (int) (Math.random() * 6));

        human.setFatigue(human.getFatigue() + language.plusFatigue);
        human.setMood(human.getMood() + language.plusMood);
        human.setIntellect(human.getIntellect() + language.plusIntellect);
        System.out.printf("Сегодня этот комочек %s почитал  %s%n", human.getName(),
                language.ArrOfBook[(int) (Math.random() * 3)]);
    }
}
