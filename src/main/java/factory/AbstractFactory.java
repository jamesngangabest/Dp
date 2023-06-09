package factory;

import factory.product.AbstractMonster;
import factory.product.AbstractTrainer;

// http://www.blackwasp.co.uk/AbstractFactory.aspx
public abstract class AbstractFactory {
    public abstract AbstractTrainer createTrainer(String name);

    public abstract AbstractMonster createMonster(String name);
}
