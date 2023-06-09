package kampfarena;

import factory.product.Trainer;
import mediator.Mediator;
import mediator.WildeLandMediator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Kampfarena (Kampfarena.java)
 *
 * This class is battle ground. This is the main point at which a trainer may register to fight in battle. The
 * Kampfarena is only open from 1t-2t. During this time trainers may battle or register for battle.
 *
 * @author Ryan Meneses
 * @version 1.0
 * @since June 24, 2021
 */
public class Kampfarena {
    protected static final Kampfarena kampfarena = new Kampfarena();
    protected static final Mediator mediator = WildeLandMediator.getMediator();
    private Queue<Registration> registry;
    private boolean isABattleOngoing;

    private Kampfarena() {
        System.out.println("\n   An arena has been built off in the distance...");
        this.registry = new LinkedList<>();
        this.isABattleOngoing = false;
    }

    public static Kampfarena getKampfarena() {
        return kampfarena;
    }

    public Queue<Registration> getRegistry() {
        return registry;
    }

    public boolean isABattleOngoing() {
        return isABattleOngoing;
    }

    public void isABattleOngoing(boolean isABattleOngoing) {
        this.isABattleOngoing = isABattleOngoing;
    }

    /**
     * This method makes announcements to the system indicating that the battle arena is open for registration.
     *
     * @return boolean
     */
    public boolean isRegistrationOpen() {
        System.out.println("ANNOUNCEMENT");

        if (mediator.getWildeLand().whatTimeIsIt().contains("1t")
                || mediator.getWildeLand().whatTimeIsIt().contains("2t")) {
            System.out.println("   Registration is now open at the Kampfarena!\n\n");
            return true;

        } else {
            System.out.println("   Registration is open from 1t-2t.\n\n");
            return false;
        }
    }

    /**
     * Register trainers for battle.
     *
     * @param trainers HashMap
     */
    public HashMap<String, Boolean> registerForBattle(HashMap<String, Trainer> trainers) {
        HashMap<String, Boolean> alreadyRegistered = new HashMap<>();

        if (isRegistrationOpen()) {
            for (Map.Entry<String, Trainer> trainer : trainers.entrySet()) {
                boolean isAlreadyRegistered = false;

                for (Registration r : this.registry) {
                    if (r.getTrainer().getName().contains(trainer.getValue().getName())) {
                        isAlreadyRegistered = true;
                        alreadyRegistered.put(r.getTrainer().getName(), true);
                        break;
                    }
                }

                if (!isAlreadyRegistered) {
                    Registration registration = new Registration();
                    this.registry.add(registration.register(trainers.get(trainer.getValue().getName())));

                } else {
                    System.out.println(trainer.getValue().getName() + " is already registered for the next battle.");
                }
            }

            return alreadyRegistered;
        }

        return null;
    }

    /**
     * Initiate a new battle.
     */
    public void initiateBattle() throws InterruptedException {
        if (mediator.getWildeLand().whatTimeIsIt().contains("1t")
                || mediator.getWildeLand().whatTimeIsIt().contains("2t")) {
            if (this.registry.size() >= 2) {
                this.isABattleOngoing = true;
                new Battle(this.registry).battle();

            } else {
                this.isABattleOngoing = false;
                System.out.println("At least two trainers must be registered to commence a battle.");
                System.out.println("   Currently there is " + this.registry.size() + " trainer registered.\n");
            }

        } else {
            this.isABattleOngoing = false;
            System.out.println("The arena is open from 1t-2t.");
            System.out.println("   Get some rest!\n");
        }
    }
}
