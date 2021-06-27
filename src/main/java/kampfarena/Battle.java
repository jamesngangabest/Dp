package kampfarena;

import decorator.monster.CodeAMon;
import decorator.monster.MonsterDecorator;
import factory.product.Monster;
import factory.product.Trainer;
import mediator.WildeLandMediator;
import singleton.Player;

import java.util.Queue;
import java.util.Random;

/**
 * Battle (Battle.java)
 *
 * This class is where the battle takes place.
 *
 * @author Ryan Meneses
 * @version 1.0
 * @since June 24, 2021
 */
public class Battle {
    private static final Player player = Player.getPlayer();
    private static final WildeLandMediator mediator = WildeLandMediator.getMediator();
    private static final Kampfarena kampfarena = Kampfarena.getKampfarena();
    private Trainer trainer1;
    private Trainer trainer2;

    /**
     * Constructor takes the trainers in queue for battle.
     *
     * @param registry Registration Queue
     */
    public Battle(Queue<Registration> registry) {
        this.trainer1 = registry.remove().getTrainer();
        this.trainer2 = registry.remove().getTrainer();

        System.out.println("   " + trainer1.getName().toUpperCase() + " vs " + trainer2.getName().toUpperCase() + "\n");
    }

    /**
     * This method provides the battle logic. The first trainer attacks, followed up by the second trainer. The type
     * of attack and the damage it deals are calculated in the damageTaken and attack methods.
     *
     * @throws InterruptedException Thread.sleep
     */
    public void battle() throws InterruptedException {
        while (true) {
            System.out.println(trainer1.getName() + " is up!");
            System.out.println(getStats(trainer1));
            Thread.sleep(new Random().nextInt(2000) + 2000);
            damageTaken(attack(trainer1), trainer1, trainer2);

            if (isTheBattleOver()) {
                kampfarena.isABattleOngoing(false);
                break;
            }

            System.out.println(trainer2.getName() + " is up!");
            System.out.println(getStats(trainer2));
            Thread.sleep(new Random().nextInt(2000) + 2000);
            damageTaken(attack(trainer2), trainer2, trainer1);

            if (isTheBattleOver()) {
                kampfarena.isABattleOngoing(false);
                break;
            }
        }
    }

    /**
     * This method prints the stats of the entity that is up in battle.
     *
     * @param entity Object is either a Trainer or CodeAMon
     * @return String
     */
    private String getStats(Object entity) {
        StringBuilder sb = new StringBuilder();
        if (entity instanceof Trainer) {
            Trainer trainer = (Trainer) entity;

            sb.append("\n<<< ").append(trainer.getName()).append(" >>>\n");
            sb.append("  HP: ").append(trainer.getHp()).append("/").append(trainer.getMaxHp()).append("\n");
            sb.append("  MP: ").append(trainer.getMp()).append("/").append(trainer.getMaxMp()).append("\n");

        } else if (entity instanceof CodeAMon) {
            Monster codeAMon = (Monster) (((CodeAMon) entity).getMonster());

            sb.append("<<< ").append(codeAMon.getName()).append(" >>>\n");
            sb.append("  HP: ").append(codeAMon.getHp()).append("/").append(codeAMon.getMaxHp()).append("\n");
            sb.append("  MP: ").append(codeAMon.getMp()).append("/").append(codeAMon.getMaxMp()).append("\n");
        }

        return sb.toString();
    }

    /**
     * This method performs an attack, and returns a value based on the attackers strength and various environmental
     * effects.
     *
     * @param trainer A trainer is the only entity that issues an attack
     * @return attack damage
     */
    public int attack(Trainer trainer) {
        Object trainerSelection = player.getMenuSelection(trainer);

        // A trainer may choose to make an attack
        if (trainerSelection instanceof String) {
        // System.out.println("   " + trainer.getName() + " is going for an " + trainerSelection.toString() + "!\n");
            System.out.println("   " + trainer.getName() + " is going for an attack!\n");
            return trainer.getStrength();

            // A trainer may choose to select a Code-a-mon
        } else {
            CodeAMon codeAMon = ((CodeAMon) trainerSelection);
            System.out.println("\n   Go " + codeAMon.getMonster().getName() + "!\n");

            // If a trainer chooses to use a Code-a-mon, this builds the Code-a-mon's battle menu
            Object codeAMonSelection = player.getMenuSelection(codeAMon);

            // A Code-a-mon may attack based on the simulation
            if (codeAMonSelection instanceof String) {
            // System.out.println("   " + codeAMon.getMonster().getName() + " is going for an "
            //       + codeAMonSelection.toString() + "!\n");

                System.out.println("   " + codeAMon.getMonster().getName() + " is going for an attack!\n");
                return codeAMon.getMonster().getStrength();

                // A Code-a-mon may use a skill based on the simulation
            } else {
                if (codeAMon.getType().equals(((MonsterDecorator.Skill) codeAMonSelection).getType())) {
                    System.out.println("\n   " + codeAMon.getMonster().getName()
                            + " is using a skill that matches its type!\n");
                    // TODO: Add a 2x bonus, subtract MP

                    return 50;
                } else {
                    // TODO: Add 1.5x bonus, subtract MP

                    return 25;
                }
            }
        }
    }

    /**
     * This method takes an attack int and calculates the amount of damage taken based on the entities stats. If an
     * attack is landed, the method returns true, else false.
     *
     * @param attack   Attack taken
     * @param attacker Object can be either the Trainer or Code-a-mon that is attacking
     * @param defender Object can be either the Trainer or Code-a-mon that is defending
     */
    public boolean damageTaken(int attack, Object attacker, Object defender) {
        int damage = 0;
        if (defender instanceof Trainer) {

            // This branch is Trainer attacker vs Trainer defender
            if (attacker instanceof Trainer) {
                /*
                 * CALCULATE MISS
                 *    If the at hit/evasion == 0, return false
                 *    If Random(hit/evasion) == hit/evasion, return false
                 *    Otherwise, continue to calculate damage
                 */
                int evade = new Random().nextInt((((Trainer) attacker).getHit()) / (((Trainer) defender).getEvasion()));
                if (((Trainer) attacker).getHit() / (((Trainer) defender).getEvasion()) == 0) {
                    return false;

                } else if (evade == ((Trainer) attacker).getHit() / (((Trainer) defender).getEvasion())) {
                    return false;
                }

                damage = attack - (((Trainer) attacker).getStrength() / ((Trainer) defender).getDefense());

                // This branch is CodeAMon attacker vs Trainer defender
            } else if (attacker instanceof CodeAMon) {

            }

        } else if (defender instanceof CodeAMon) {

            // This branch is Trainer attacker vs CodeAMon defender
            if (attacker instanceof Trainer) {

                // This branch is CodeAMon attacker vs CodeAMon defender
            } else if (attacker instanceof CodeAMon) {

            }
        }

        // If the damage is less than 0, total damage taken is 1
        if (damage < 0) {
            damage = 1;
        }

        adjustHealth(damage, defender);
        return true;
    }

    /**
     * This method accesses the entities stats and adjusts their HP. Formula: HP = HP - damage
     *
     * @param damage int
     * @param entity Trainer or CodeAMon
     */
    private void adjustHealth(int damage, Object entity) {
        if (entity instanceof Trainer) {
            Trainer trainer = (Trainer) entity;

            int hp = trainer.getHp() - damage;
            if (hp < 0) {
                System.out.println("\n   OVERKILL!\n");
                hp = 0;
            }

            trainer.setHp(hp);
            System.out.println(trainer.getName() + " has taken " + damage + " damage!\n");

        } else {
            Monster codeAMon = (Monster) ((CodeAMon) entity).getMonster();

            int hp = codeAMon.getHp() - damage;
            if (hp < 0) {
                hp = 0;
            }

            codeAMon.setHp(hp);
            System.out.println(codeAMon.getName() + " has taken " + damage + " damage!\n");
        }
    }

    private boolean isTheBattleOver() {
        // If the battle ends due to it being late
        if (mediator.getDate().contains("3t")) {
            int tomorrow = mediator.getDay() + 1;

            if (isDead(trainer1)) {
                System.out.println("\nCONGRATULATIONS " + trainer2.getName().toUpperCase() + "!!\n");
                System.out.println("   You shall live to see another day.\n");

            } else if (isDead(trainer2)) {
                System.out.println("\nCONGRATULATIONS " + trainer1.getName().toUpperCase() + "!!\n");
                System.out.println("   You shall live to see another day.\n");

            } else {
                System.out.println("\nThe battle could not be resolved...");
                System.out.println("   Join us tomorrow at " + tomorrow + "d:1t:0c, the battle shall continue!\n");
            }

            return true;
        }

        if (trainer1.getHp() == 0) {
            return isDead(trainer1);

        } else if (trainer2.getHp() == 0) {
            return isDead(trainer2);
        }

        return false;
    }

    /**
     * Checks if a Trainer has fallen.
     *
     * @return boolean
     */
    public boolean isDead(Trainer trainer) {
        if (trainer.getHp() == 0) {
            System.out.println("\n\n" + trainer.getName() + " is dead.\n\n");
            player.getTrainers().remove(trainer.getName());

            return true;

        } else {
            return false;
        }
    }
}
