package org.poo.card;

import java.util.ArrayList;

public class Hero extends Card{
    int health = 30;
    int doneAttack = 0;

    public Hero() {
        
    }

    public Hero(int mana, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    public void setDoneAttack(int doneAttack) {
        this.doneAttack = doneAttack;
    }

    public int getDoneAttack() {
        return doneAttack;
    }

    public int getHealth() {
        return health;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }

    public void useAbility(ArrayList<Minion> list) {
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
                for (int i = index; i < 4; i++) {
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
        }
    }
}
