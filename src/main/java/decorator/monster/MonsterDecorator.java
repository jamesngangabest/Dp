package decorator.monster;

import factory.product.AbstractMonster;
import factory.product.Monster;
import factory.product.Trainer;

import java.util.HashMap;

/**
 * MonsterDecorator (MonsterDecorator.java)
 *
 * MonsterDecorator decorates a monster or Code-a-mon by their level. A monster becoming a Code-a-mon is decorated
 * with the Initial child class, and Code-a-mon are further decorated when they evolve.
 *
 * @author Ryan Meneses
 * @version 1.0
 * @since June 21, 2021
 */
public abstract class MonsterDecorator implements CodeAMon {
    private CodeAMon codeAMon;
    protected HashMap<String, Skill> skills; // Initialized in child class Initial
    protected double weatherBonus; // A multiplier for weather bonuses: 0.5, 1, or 1.5
    protected double typeBonus; // A multiplier for weather bonuses: 0.25, 1, or 2.0
    protected int exp;

    protected abstract void boostStats();

    protected abstract void giveType();

    protected abstract void declareSkill();

    /**
     * Constructor builds the shared attributes between all evolutions.
     *
     * @param codeAMon Code-a-mon
     */
    public MonsterDecorator(CodeAMon codeAMon) {
        this.codeAMon = codeAMon;
        this.weatherBonus = 1;
        this.typeBonus = 1;
    }

    @Override
    public AbstractMonster getMonster() {
        return (Monster) codeAMon;
    }

    @Override
    public void init(Trainer trainer) {
        codeAMon.init(trainer); // Invokes the init method in factory.Monster

        System.out.println("   " + trainer.getName() + " is trying to form a bond with "
                + codeAMon.getMonster().getName() + ".\n");

        if (codeAMon.getMonster().isWild()) {

            System.out.println(codeAMon.getMonster().getName()
                    + " is forming a bond with "
                    + trainer.getName()
                    + "...");

        } else {
            System.out.println(codeAMon.getMonster().getName()
                    + " is already bonded with "
                    + codeAMon.getMonster().getTrainer().getName() + ".\n");
        }
    }

    @Override
    public HashMap<String, Skill> getSkills() {
        return skills;
    }

    public void setSkills(Skill skill) {
        skills.put(skill.getName(), skill);
        ;
    }

    @Override
    public int getExp() {
        return exp;
    }

    @Override
    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public void setWeatherBonus(double weatherBonus) {
        this.weatherBonus = weatherBonus;
    }

    @Override
    public double getWeatherBonus() {
        return this.weatherBonus;
    }

    /**
     * This inner class provides the data structure for a skill.
     */
    public static class Skill {
        private final String name;
        private final Type type;
        private final int cost;

        /**
         * Skill constructor.
         *
         * @param name String
         * @param type String
         * @param cost int
         */
        public Skill(String name, Type type, int cost) {
            this.name = name;
            this.type = type;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

        public Type getType() {
            return type;
        }

        public int getCost() {
            return cost;
        }
    }

    /**
     * This enum lists the elemental types.
     */
    public enum Type {
        FIRE,
        LIGHTNING,
        BLIZZARD,
        WATER,
        BIO,
        DARK,
        LIGHT,
        NONE
    }
}