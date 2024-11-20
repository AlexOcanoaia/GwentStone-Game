package org.poo.table;

import java.util.ArrayList;

import org.poo.card.Minion;
import org.poo.player.Player;

public class Table {
    ArrayList<ArrayList<Minion>> table = new ArrayList<>();
    final int number_rows = 4;
    final int number_columns = 5;

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

    public void addCardtoTable(Minion minion, int x, int y, Player player) {
        String name = minion.getName();
        if (player.getMana() < minion.getMana()) {
            System.out.println("There isn't enough mana");
            return;
        }
        if (name.equals("Sentinel") || name.equals("Berserker") || name.equals("The Cursed One") || name.equals("Disciple")) {
            if (player.getId() == 1) {
                table.get(0).set(findSpot(0), minion);
            } else {
                table.get(3).set(findSpot(3), minion);
            }
        } else if (name.equals("Goliath") || name.equals("Warden") || name.equals("The Ripper") || name.equals("Miraj")) {
            if (player.getId() == 1) {
                table.get(1).set(findSpot(1), minion);
            } else {
                table.get(2).set(findSpot(2), minion);
            }
        }
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
}
