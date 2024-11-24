package org.poo.card;

import java.util.ArrayList;

public class Minion extends Card {
    private int health;
    private int attackDamage;
    private boolean frozen;
    private int doneAttack = 0;

    public Minion(final int mana, final int health, final int attackDamage,
    final String description, final ArrayList<String> colors,
    final String name, final boolean frozen) {
        super(mana, description, colors, name);
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
        this.frozen = frozen;
    }

    /**
     *
     * @param doneAttack
     * set the doneAttack field
     */
    public void setDoneAttack(final int doneAttack) {
        this.doneAttack = doneAttack;
    }

    /**
     *
     * @return doneAttack
     */
    public int getDoneAttack() {
        return doneAttack;
    }

    /**
     *
     * @param health
     * set the health field
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     *
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     *
     * @param attackDamage
     * set the attackDamage field
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     *
     * @return attackDamage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * set frozen to true
     */
    public void setFrozen() {
        frozen = true;
    }

    /**
     * set fronze to false
     */
    public void unsetFrozen() {
        frozen = false;
    }

    /**
     *
     * @return frozen
     */
    public boolean isFrozen() {
        return this.frozen;
    }

    /**
     *
     * @return true if the minion is Tank
     */
    public boolean isTank() {
        if (this.getName().equals("Goliath")
        || this.getName().equals("Warden")) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param minion
     * This functions represent the abilities of minions
     */
    public void useAbility(final Minion minion) {
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
            default:
                break;
        }
    }
}
