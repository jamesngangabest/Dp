package decorator;

import decorator.monster.CodeAMon;
import decorator.monster.Initial;
import factory.product.AbstractMonster;
import factory.product.AbstractTrainer;
import factory.product.Monster;
import factory.product.Trainer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MonsterDecoratorTest {
    AbstractMonster monster;
    AbstractTrainer trainer;

    /**
     * Setup a new monster and trainer for every unit test.
     */
    @Before
    public void setUp() {
        System.out.println("\n===============================");
        System.out.println("MonsterDecorator.java Test Suite");
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
     * This unit test tests the MonsterDecorator constructor. If a wild monster is passed to
     * the constructor, it is expected to have no skills as the constructor decorates only
     * code-e-mon.
     */
    @Test
    public void testMonsterDecoratorWithAWildMonster() {
        System.out.println("+TEST: testMonsterDecoratorWithAWildMonster\n");

        Initial initial = new Initial((Monster) monster);
        initial.getCodeAMon().setName("Wale");

        System.out.println("Expected: "
                + "--- " + initial.getCodeAMon().getName()
                +  " Skills ---");

        System.out.println("Actual: " + initial.listSkills());

        Assert.assertEquals("--- " + initial.getCodeAMon().getName() +  " Skills ---\n",
                initial.listSkills());
    }

    /**
     * This unit test tests the MonsterDecorator constructor. If a code-a-mon is passed to the
     * constructor, it is expected to have skills as the constructor decorates the code-a-mon
     * with initial skills.
     */
    @Test
    public void testMonsterDecoratorWithATamedMonster() {
        System.out.println("+TEST: testMonsterDecoratorWithATamedMonster\n");

        monster.setName("Wale");
        CodeAMon codeAMon = trainer.formBond((Monster) monster);

        System.out.println("Expected:\n"
                + "--- " + codeAMon.getCodeAMon().getName()
                +  " Skills ---\n"
                + "Splash [Type: Water]\n");

        System.out.println("Actual:\n" + codeAMon.listSkills());

        Assert.assertEquals("--- " + codeAMon.getCodeAMon().getName() +  " Skills ---\n"
                        + "Splash [Type: WATER]\n", codeAMon.listSkills());
    }

    @Test
    public void testInitWithAnUntamedMonster() {
        monster = new Monster("Wale");

        trainer = new Trainer("Dock");
        trainer.formBond((Monster) monster);

        System.out.println("Expected: Dock");
        System.out.println("Actual: " + monster.getTrainer().getName() + "\n");

        Assert.assertEquals("Dock", monster.getTrainer().getName());
    }

    @Test
    public void testInitWithATamedMonster() {
        monster = new Monster("Wale");

        trainer = new Trainer("Dock");
        trainer.formBond((Monster) monster);

        trainer = new Trainer("Tomm");
        trainer.formBond((Monster) monster);

        System.out.println("Unexpected: Tomm");
        System.out.println("Actual: " + monster.getTrainer().getName() + "\n");

        Assert.assertNotEquals("Tomm", monster.getTrainer().getName());
    }
}
