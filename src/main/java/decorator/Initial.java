package decorator;

import factory.Trainer;

import java.util.HashMap;

/**
 * Initial (Initial.java)
 *
 * This class decorates a monster into a Code-a-mon giving them a type and initial skills.
 *
 * @author Ryan Meneses
 * @version 1.0
 * @since June 21, 2021
 */
public class Initial extends MonsterDecorator {
    private String type;

    public Initial(CodeAMon codeAMon) {
        super(codeAMon);
        super.skills = new HashMap<>();
    }

    @Override
    public void init(Trainer trainer) {
        super.init(trainer);
        System.out.println("    "
                + getMonster().getName()
                + " has become code-a-mon!\n");
        giveMonsterType();
        declareSkill();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }


    @Override
    public void giveMonsterType() {
        String name = getMonster().getName();

        switch (name) {
            case "Wale":
                setType("Water");
                break;
            case "Kaht":
                setType("Poison");
                break;
            default:
                System.out.println("Unidentified code-a-mon.");
                break;
        }

        System.out.println(name
                + "'s type is revealed.\n    "
                + getType() + "!\n");
    }

    /**
     * Initialize the new code-a-mon's first skill.
     */
    @Override
    public void declareSkill() {
        String name = getMonster().getName();
        Skill skill = null;

        switch (name) {
            case "Wale":
                skill = initSkill("Splash", "Water");
                break;
            case "Kaht":
                skill = initSkill("Scratch", "Poison");
                break;
            default:
                System.out.println("Unidentified code-a-mon.");
                break;
        }

        if(skill != null) {
            super.skills.put(skill.getName(), skill);

        } else {
            System.out.println("A skill cannot be set for an unidentified code-a-mon.");
        }
    }

    /**
     * Declare the new code-a-mons first skill.
     *
     * @param name Skill name
     * @param type Skill type
     * @return New Skill object
     */
    public Skill initSkill(String name, String type) {
        return new Skill(name, type);
    }

    /**
     * List the new code-a-mons-skills.
     *
     * @return String Skill list
     */
    @Override
    public String listSkills() {
        StringBuilder sb = new StringBuilder("--- " + getMonster().getName() +  " Skills ---\n");

        for(String name : skills.keySet()) {
            sb.append(skills.get(name).getName()).append(" ")
                    .append("[lvl. " + skills.get(name).getLevel()).append(" ")
                    .append(skills.get(name).getType()).append("]\n");
        }

        return sb.toString();
    }

    /**
     * Returns the stats of a code-a-mon.
     *
     * @return String stat list
     */
    @Override
    public String getStats() {
        StringBuilder sb = new StringBuilder("   >>> " + getMonster().getName() + " <<<\n");
        sb.append("   Type: ").append(getType()).append("\n");
        sb.append("   HP:   ").append(getMonster().getHP()).append("\n");
        sb.append("   MP:   ").append(getMonster().getMP());

        return sb.toString();
    }
}