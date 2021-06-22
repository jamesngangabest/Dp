package factory;

public abstract class AbstractMonster {

    protected String name;

    protected int hp;
    protected int mp;

    protected int lvl;

    protected boolean isTamed = false;

    public abstract String getName();
    public abstract void setName(String name);

    public abstract int getHP();
    public abstract void setHP(int hp);

    public abstract int getMP();
    public abstract void setMP(int mp);

    public abstract void setIsTamed(boolean isTamed);
    public abstract boolean isTamed();

    public abstract int getLvl();
    public abstract void setLvl(int lvl);

    public abstract String statsToString();
}
