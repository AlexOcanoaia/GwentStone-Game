package org.poo.table;

import java.util.ArrayList;

import org.poo.card.Minion;
import org.poo.player.Player;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class Table {
    private ArrayList<ArrayList<Minion>> table = new ArrayList<>();
    final int number_rows = 4;
    final int number_columns = 5;

    public ArrayList<ArrayList<Minion>> getTable() {
        return table;
    }

    public void initializeTable() {
        for (byte i = 0; i < number_rows; i++) {
            ArrayList<Minion> row = new ArrayList<>();
            for (byte j = 0; j < number_columns; j++) {
                row.add(null);
            }
            table.add(row);
        }
    }

    public int findSpot(int x) {
        for (byte i = 0; i < number_columns; i++) {
            if (table.get(x).get(i) == null) {
                return i;
            }
        }
        return -1;
    }

    public String addCardtoTable(Minion minion, Player player) {
        String name = minion.getName();
        if (player.getMana() < minion.getMana()) {
            return "Not enough mana to place card on table.";
        }
        if (name.equals("Sentinel") || name.equals("Berserker") || name.equals("The Cursed One") || name.equals("Disciple")) {
            if (player.getId() == 1) {
                if (findSpot(3) != -1) {
                    table.get(3).set(findSpot(3), minion);
                    player.setMana(player.getMana() - minion.getMana());
                    return null;
                } else {
                    return "Cannot place card on table since row is full.";
                }
            } else {
                if (findSpot(0) != -1) {
                    table.get(0).set(findSpot(0), minion);
                    player.setMana(player.getMana() - minion.getMana());
                    return null;
                } else {
                    return "Cannot place card on table since row is full.";
                }
            }
        } else if (name.equals("Goliath") || name.equals("Warden") || name.equals("The Ripper") || name.equals("Miraj")) {
            if (player.getId() == 1) {
                if (findSpot(2) != -1) {
                    table.get(2).set(findSpot(2), minion);
                    player.setMana(player.getMana() - minion.getMana());
                    return null;
                } else {
                    return "Cannot place card on table since row is full.";
                }
            } else {
                if (findSpot(1) != -1) {
                    table.get(1).set(findSpot(1), minion);
                    player.setMana(player.getMana() - minion.getMana());
                    return null;
                } else {
                    return "Cannot place card on table since row is full.";
                }
            }
        }
        return null;
    }

    public void eliminateCard(int x, int y) {
        table.get(x).set(y, null);
        for (int i = y; i < number_columns - 1; i++) {
            Minion current = table.get(x).get(i);
            Minion next = table.get(x).get(i + 1);
            if (table.get(x).get(i + 1) != null) {
                Minion tmp = current;
                current = next;
                next = tmp;
            }
        }
    }

    public Minion getCard(int x, int y) {
        return table.get(x).get(y);
    }

    public Minion checkTank(int id) {
        int row = 0;
        if (id == 1) {
            row = 1;
        } else {
            row = 2;
        }
        for (int i = 0; i < 5; i++) {
            if (table.get(row).get(i) != null) {
                if (table.get(row).get(i).isTank() == true) {
                    return table.get(row).get(i);
                }
            }
        }
        return null;
    }

    public String attackCard(int x1, int y1, int x2, int y2) {
        Minion tank = null;
        int id = 0;
        if (x1 == 2 || x1 == 3) {
            id = 1;
            if (x2 == 2 || x2 == 3) {
                return "Attacked card does not belong to the enemy.";
            }
            tank = checkTank(1);
        } else {
            id = 2;
            if (x2 == 0 || x2 == 1) {
                return "Attacked card does not belong to the enemy.";
            }
            tank = checkTank(2);
        }

        Minion minion1 = getCard(x1, y1);
        Minion minion2 = getCard(x2, y2);

        if (minion1 == null) {
            return "Minion 1 is null";
        }
        if (minion2 == null) {
            return "Minion 2 is null";
        }

        if (minion1.getDoneAttack() == 1) {
            return "Attacker card has already attacked this turn.";
        }
        if (minion1.isFrozen() == true) {
            return "Attacker card is frozen.";
        }

        if (tank != null) {
            if (tank.equals(minion2) == false) {
                return "Attacked card is not of type 'Tank'.";
            }
        }

        int newHealth = minion2.getHealth() - minion1.getAttackDamage();
        minion1.setDoneAttack(1);
        if (newHealth <= 0) {
            eliminateCard(x2, y2);
        } else {
            minion2.setHealth(newHealth);
        }
        return null;
    }

    public void showCards(ArrayNode output) {
        for (int i = 0; i < number_rows; i++) {
            for (int j = 0; j < number_columns; j++) {
                if (table.get(i).get(j) != null) {
                    System.out.println("The minion at row " + i + " and column " + j + " is " + table.get(i).get(j).getName());
                    output.add("The minion at row " + i + " and column " + j + " is " + table.get(i).get(j).getName());
                }
            }
        }
    }

    public void setMinions() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (table.get(i).get(j) != null) {
                    table.get(i).get(j).setDoneAttack(0);
                }
            }
        }
    }

    public void unfrozenMinions(int id) {
        int start;
        int max;
        if (id == 1) {
           start = 2;
           max = 4;
        } else {
            start = 0;
            max = 2;
        }
        for (int i = start; i < max; i++) {
            for (int j = 0; j < number_columns; j++) {
                if (table.get(i).get(j) != null) {
                    table.get(i).get(j).unsetFrozen();
                }
            }
        }
    }
}
