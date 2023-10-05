package Lab4.People;

public abstract class GameObject {
    protected int hp, hunger, intellect;
    public abstract void getspecifications();

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public int getHunger() {
        return hunger;
    }

    public int getIntellect() {
        return intellect;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }
}
