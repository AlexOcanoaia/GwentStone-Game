package org.poo.card;

import java.util.ArrayList;

public class Minion extends Card{
    private int health;
    private int attackDamage;
    private boolean frozen;
    private int doneAttack = 0;

    public Minion(int mana, int health, int attackDamage, String description, ArrayList<String> colors, String name, boolean frozen) {
        this.setMana(mana);
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
        this.frozen = frozen;
    }

    public void setDoneAttack(int doneAttack) {
        this.doneAttack = doneAttack;
    }

    public int getDoneAttack() {
        return doneAttack;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setFrozen() {
        frozen = true;
    }

    public void unsetFrozen() {
        frozen = false;
    }

    public boolean isFrozen() {
        return this.frozen;
    }

    public boolean isTank() {
        if (this.getName().equals("Goliath") 
        || this.getName().equals("Warden") ) {
            return true;
        }
        return false;
    }

    public void useAbility(Minion minion) {
        switch (this.getName()) {
            case "The Ripper":
                if (minion.getAttackDamage() < 2) {
                    minion.setAttackDamage(0);
                } else {
                    minion.setAttackDamage(minion.getAttackDamage() - 2);
                }
                break;
            case "Miraj":
                int aux = minion.getHealth();
                minion.setHealth(this.health);
                this.setHealth(aux);
                break;
            case "The Cursed One":
                int tmp = minion.getHealth();
                minion.setHealth(minion.getAttackDamage());
                minion.setAttackDamage(tmp);
                break;
            case "Disciple":
                minion.setHealth(minion.getHealth() + 2);
                break;
        }
    }
}
