package org.poo.table;

import java.util.ArrayList;

import org.poo.card.Minion;
import org.poo.player.Player;

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
                if (findSpot(0) != -1) {
                    table.get(0).set(findSpot(0), minion);
                    player.setMana(player.getMana() - minion.getMana());
                    return null;
                } else {
                    return "Cannot place card on table since row is full.";
                }
            } else {
                if (findSpot(3) != -1) {
                    table.get(3).set(findSpot(3), minion);
                    player.setMana(player.getMana() - minion.getMana());
                    return null;
                } else {
                    return "Cannot place card on table since row is full.";
                }
            }
        } else if (name.equals("Goliath") || name.equals("Warden") || name.equals("The Ripper") || name.equals("Miraj")) {
            if (player.getId() == 1) {
                if (findSpot(1) != -1) {
                    table.get(1).set(findSpot(1), minion);
                    player.setMana(player.getMana() - minion.getMana());
                    return null;
                } else {
                    return "Cannot place card on table since row is full.";
                }
            } else {
                if (findSpot(2) != -1) {
                    table.get(2).set(findSpot(2), minion);
                    player.setMana(player.getMana() - minion.getMana());
                    return null;
                } else {
                    return "Cannot place card on table since row is full.";
                }
            }
        }
        return null;
    }

    public void eliminateCard(Minion minion) {
        int row = -1;
        int column = 0;
        for (byte i = 0; i < number_rows; i++) {
            column = table.get(i).indexOf(minion);
            if (column != -1) {
                row = i;
                break;
            }
        }
        if (row != -1) {
            table.get(row).remove(minion);
        } else {
            System.out.println("Card wasn't found");
            return;
        }
        for (int i = column; i < number_columns - 1; i++) {
            Minion current = table.get(row).get(i);
            Minion next = table.get(row).get(i + 1);
            if (table.get(row).get(i + 1) != null) {
                Minion tmp = current;
                current = next;
                next = tmp;
            }
        }
    }

    public void showCards() {
        for (int i = 0; i < number_rows; i++) {
            for (int j = 0; j < number_columns; j++) {
                if (table.get(i).get(j) != null) {
                    System.out.println("The minion at row " + i + " and column " + j + " is " + table.get(i).get(j).getName());
                }
            }
        }
    }

    public void unfrozenMinions(int id) {
        int start;
        int max;
        if (id == 1) {
           start = 0;
           max = 2;
        } else {
            start = 2;
            max = 4;
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
