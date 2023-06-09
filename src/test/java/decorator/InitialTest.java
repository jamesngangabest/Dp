package decorator;

import decorator.monster.CodeAMon;
import decorator.monster.Initial;
import decorator.monster.MonsterDecorator;
import factory.product.AbstractMonster;
import factory.product.AbstractTrainer;
import factory.product.Monster;
import factory.product.Trainer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InitialTest {
    AbstractMonster monster;
    AbstractTrainer trainer;

    /**
     * Setup a new monster and trainer for every unit test.
     */
    @Before
    public void setUp() {
        System.out.println("\n===============================");
        System.out.println("    Initial.java Test Suite");
        System.out.println("===============================");

        monster = new Monster(null);
        trainer = new Trainer(null);
    }

    /**
     * Set class attributes to null after every test.
     */
    @After
    public void tearDown() {
        monster = null;
        trainer = null;
    }

    /**
     * This unit test tests the initSkill method with the code-a-mon Wale. The initSkill method
     * decorates a tamed monster, i.e. a code-a-mon, with an initial skill provided a type
     * Testing initSkill tests the inner class Skill.
     */
    @Test
    public void testInitSkillMethodWithWale() {
        System.out.println("+TEST: testInitSkillMethodWithWale\n");

        CodeAMon codeAMon = (Monster) monster;
        codeAMon.getMonster().setName("Wale");

        Initial initial = new Initial(codeAMon);
        initial.init((Trainer) trainer);

        MonsterDecorator.Skill skill = initial.initSkill("Splash", MonsterDecorator.Type.WATER, 4);

        System.out.println("Expected: Splash Water 4");
        System.out.println("Actual: " + skill.getName()
                + " " + skill.getType()
                + " " + skill.getCost() + "\n");

        Assert.assertEquals("Splash", skill.getName());
        Assert.assertEquals(MonsterDecorator.Type.WATER, skill.getType());
        Assert.assertEquals(4, skill.getCost());
    }

    /**
     * This unit test tests the initSkill method with the code-a-mon Kaht. The initSkill method
     * decorates a tamed monster, i.e. a code-a-mon, with an initial skill provided a type
     * Testing initSkill tests the inner class Skill.
     */
    @Test
    public void testInitSkillMethodWithKaht() {
        System.out.println("+TEST: testInitSkillMethodWithKaht\n");

        CodeAMon codeAMon = (Monster) monster;
        codeAMon.getMonster().setName("Kaht");

        Initial initial = new Initial(codeAMon);
        initial.init((Trainer) trainer);

        MonsterDecorator.Skill skill = initial.initSkill("Scratch", MonsterDecorator.Type.BIO, 4);

        System.out.println("Expected: Scratch Poison 4");
        System.out.println("Actual: " + skill.getName()
                + " " + skill.getType()
                + " " + skill.getCost()
                + "\n");

        Assert.assertEquals("Scratch", skill.getName());
        Assert.assertEquals(MonsterDecorator.Type.BIO, skill.getType());
        Assert.assertEquals(4, skill.getCost());
    }

    /**
     * This unit test tests the init method with the code-a-mon Wale. The init method decorates a
     * tamed monster, i.e. a code-a-mon with an initial skill, type, and skill level.
     * In the process of testing init the following are also tested: the two methods
     * declareSkill, and giveMonsterType, the inner class Skill, and the HashMap skills.
     */
    @Test
    public void testInitMethodWithWale() {
        System.out.println("+TEST: testInitMethodWithWale\n");

        CodeAMon codeAMon = (Monster) monster;
        codeAMon.getMonster().setName("Wale");

        Initial initial = new Initial(codeAMon);
        initial.init((Trainer) trainer);

        Initial temp = new Initial(null);
        MonsterDecorator.Skill skill = temp.initSkill("Splash", MonsterDecorator.Type.WATER, 4);

        System.out.println("Expected: Wale WATER 4 Splash");
        System.out.println("Actual: "
                + codeAMon.getMonster().getName()
                + " " + initial.getType()
                + " " + initial.getSkills().get("Splash").getCost()
                + " " +initial.getSkills().get(initial.initSkill("Splash", MonsterDecorator.Type.WATER, 4).getName()).getName()
                + "\n");

        Assert.assertEquals("Wale", codeAMon.getMonster().getName());
        Assert.assertEquals(MonsterDecorator.Type.WATER, initial.getType());
        Assert.assertEquals(skill.getName(),
                initial.getSkills().get(initial.initSkill("Splash", MonsterDecorator.Type.WATER, 4).getName()).getName());
    }

    /**
     * This unit test tests the init method with the code-a-mon Kaht. The init method decorates a
     * tamed monster, i.e. a code-a-mon with an initial skill, type, and skill level.
     * In the process of testing init the following are also tested: the two methods
     * declareSkill, and giveMonsterType, the inner class Skill, and the HashMap skills.
     */
    @Test
    public void testInitMethodWithKaht() {
        System.out.println("+TEST: testInitMethodWithKaht\n");

        CodeAMon codeAMon = (Monster) monster;
        codeAMon.getMonster().setName("Kaht");

        Initial initial = new Initial(codeAMon);
        initial.init((Trainer) trainer);

        Initial temp = new Initial(null);
        MonsterDecorator.Skill skill = temp.initSkill("Scratch", MonsterDecorator.Type.BIO, 4);

        System.out.println("Expected: Kaht BIO 4 Scratch");
        System.out.println("Actual: "
                + codeAMon.getMonster().getName()
                + " " + initial.getType() + " "
                + " " + initial.getSkills().get("Scratch").getCost()
                + " " +initial.getSkills().get(initial.initSkill("Scratch", MonsterDecorator.Type.BIO, 4).getName()).getName()
                + "\n");

        Assert.assertEquals("Kaht", codeAMon.getMonster().getName());
        Assert.assertEquals(MonsterDecorator.Type.BIO, initial.getType());
        Assert.assertEquals(skill.getName(),
                initial.getSkills().get(initial.initSkill("Scratch", MonsterDecorator.Type.BIO, 4).getName()).getName());
    }

    /**
     * This unit test tests the init method with an unknown code-a-mon. It is expected for an
     * unknown code-a-mon to not gain any skills. Without any skills, the HashMap size is 0.
     */
    @Test
    public void testInitMethodWithUnknownMonster() {
        System.out.println("+TEST: testInitMethodWithUnknownMonster\n");

        CodeAMon codeAMon = (Monster) monster;
        codeAMon.getMonster().setName("Unknown");

        MonsterDecorator initial = new Initial(codeAMon);
        initial.init((Trainer) trainer);

        System.out.println("\nExpected: skills.size == 0");
        System.out.println("Actual: skills.size == " + initial.getSkills().size() + "\n");

        Assert.assertEquals(0, initial.getSkills().size());
    }

    /**
     * This unit test tests the statsToString method to check proper format
     */
    @Test
    public void testStatsToString() {
        System.out.println("+TEST: testStatsToString\n");

        Initial initial = new Initial((Monster) monster);

        StringBuilder sb = new StringBuilder("   >>> " + null + " <<<\n");
        sb.append("   Type: ").append("null").append("\n");
        sb.append("   Lvl:  ").append("0").append("\n");
        sb.append("   HP:   ").append("0/0").append("\n");
        sb.append("   MP:   ").append("0/0\n");

        System.out.println("\nExpected: " + sb.toString());
        System.out.println("Actual: " + initial.statsToString());

        Assert.assertEquals(sb.toString(), initial.statsToString());
    }
}
