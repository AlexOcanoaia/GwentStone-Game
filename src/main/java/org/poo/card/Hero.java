package org.poo.card;

import java.util.ArrayList;

public class Hero extends Card{
    static int health = 30;

    public void useAbility(Minion minion, ArrayList<Minion> list) {
        switch (this.getName()) {
            case "Lord Royce":
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isFrozen() == false) {
                        list.get(i).setFrozen();
                    }
                }
                break;
            case "Empress Thorina":
                int index = -1;
                int max = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getHealth() > max) {
                        max = list.get(i).getHealth();
                        index = i;
                    }
                }
                if (index != -1) {
                    list.remove(index);
                }
                break;
            case "King Mudface":
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setHealth(list.get(i).getHealth() + 1);
                }
                break;
            case "General Kocioraw":
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setAttackDamage(list.get(i).getAttackDamage() + 1);
                }
                break;
        }
    }
}
