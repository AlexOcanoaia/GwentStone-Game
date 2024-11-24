package org.poo.card;

import java.util.ArrayList;

public class Hero extends Card {
    private final int maxHealth = 30;
    private int health = maxHealth;
    private int doneAttack = 0;

    public Hero() {
        super();
    }

    public Hero(final int mana, final String description,
    final ArrayList<String> colors, final String name) {
       super(mana, description, colors, name);
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
     * @return health
     */
    public int getHealth() {
        return health;
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
     * @param list
     * This functions represent the abilities for heroes
     */
    public void useAbility(final ArrayList<Minion> list) {
        switch (this.getName()) {
            case "Lord Royce":
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != null) {
                        list.get(i).setFrozen();
                    }
                }
                break;
            case "Empress Thorina":
                int index = -1;
                int max = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != null) {
                        if (list.get(i).getHealth() > max) {
                            max = list.get(i).getHealth();
                            index = i;
                        }
                    }
                }
                if (index != -1) {
                    list.set(index, null);
                }
                final int numberRows = 4;
                for (int i = index; i < numberRows; i++) {
                    Minion current = list.get(i);
                    Minion next = list.get(i + 1);
                    if (next != null) {
                        Minion tmp = current;
                        current = next;
                        next = tmp;
                    }
                }
                break;
            case "King Mudface":
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != null) {
                        list.get(i).setHealth(list.get(i).getHealth() + 1);
                    }
                }
                break;
            case "General Kocioraw":
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != null) {
                        list.get(i).setAttackDamage(list.get(i).getAttackDamage() + 1);
                    }
                }
                break;
            default:
                break;
        }
    }
}
