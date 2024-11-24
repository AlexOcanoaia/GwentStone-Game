package org.poo.table;

import java.util.ArrayList;

import org.poo.card.Hero;
import org.poo.card.Minion;
import org.poo.player.Player;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Table {
    private ArrayList<ArrayList<Minion>> table = new ArrayList<>();
    private final int numberRows = 4;
    private final int numberColumns = 5;
    private final int row3 = 3;
    /**
     *
     * @return the table
     */
    public ArrayList<ArrayList<Minion>> getTable() {
        return table;
    }

    /**
     *
     *  Allocate memory for the table
     */
    public void initializeTable() {
        for (byte i = 0; i < numberRows; i++) {
            ArrayList<Minion> row = new ArrayList<>();
            for (byte j = 0; j < numberColumns; j++) {
                row.add(null);
            }
            table.add(row);
        }
    }

    /**
     *
     * @param x
     * @return if there is a free spot on the row
     */
    public int findSpot(final int x) {
        for (byte i = 0; i < numberColumns; i++) {
            if (table.get(x).get(i) == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param minion
     * @param player
     * @return null if the card is add to table,
     *          otherwise returns error
     */
    public String addCardtoTable(final Minion minion, final Player player) {
        String name = minion.getName();
        if (player.getMana() < minion.getMana()) {
            return "Not enough mana to place card on table.";
        }
        if (name.equals("Sentinel") || name.equals("Berserker")
        || name.equals("The Cursed One") || name.equals("Disciple")) {
            if (player.getId() == 1) {
                if (findSpot(row3) != -1) {
                    table.get(row3).set(findSpot(row3), minion);
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
        } else if (name.equals("Goliath") || name.equals("Warden")
        || name.equals("The Ripper") || name.equals("Miraj")) {
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

    /**
     *
     * @param x
     * @param y
     *  This function eliminate a card from the table
     */
    public void eliminateCard(final int x, final int y) {
        table.get(x).set(y, null);
        for (int i = y; i < numberColumns - 1; i++) {
            Minion current = table.get(x).get(i);
            Minion next = table.get(x).get(i + 1);
            if (table.get(x).get(i + 1) != null) {
                Minion tmp = current;
                current = next;
                next = tmp;
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return the minion at row x and column y
     */
    public Minion getCard(final int x, final int y) {
        return table.get(x).get(y);
    }

    /**
     *
     * @param id
     * @return if the player has a Tank card
     */
    public Minion checkTank(final int id) {
        int row = 0;
        if (id == 1) {
            row = 1;
        } else {
            row = 2;
        }
        for (int i = 0; i < numberColumns; i++) {
            if (table.get(row).get(i) != null) {
                if (table.get(row).get(i).isTank()) {
                    return table.get(row).get(i);
                }
            }
        }
        return null;
    }

    /**
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return null if the attack is successful, otherwise
     *          returns error
     */
    public String attackCard(final int x1, final int y1, final int x2, final int y2) {
        Minion tank = null;
        if (x1 == 2 || x1 == row3) {
            if (x2 == 2 || x2 == row3) {
                return "Attacked card does not belong to the enemy.";
            }
            tank = checkTank(1);
        } else {
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
        if (minion1.isFrozen()) {
            return "Attacker card is frozen.";
        }

        if (tank != null) {
            if (!tank.equals(minion2)) {
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

    /**
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return null if the ability is successful, otherwise
     *          returns error
     */
    public String cardAbility(final int x1, final int y1, final int x2, final int y2) {
        Minion card1 = getCard(x1, y1);
        Minion card2 = getCard(x2, y2);

        if (card1 == null || card2 == null) {
            return "card is null";
        }

        if (card1.isFrozen()) {
            return "Attacker card is frozen.";
        }

        if (card1.getDoneAttack() == 1) {
            return "Attacker card has already attacked this turn.";
        }
        Minion tank = null;
        if (x1 == 0 || x1 == 1) {
            if (card1.getName().equals("Disciple")) {
                if (x2 == 2 || x2 == row3) {
                    return "Attacked card does not belong to the current player.";
                }
                card1.useAbility(card2);
                card1.setDoneAttack(1);
                return null;
            } else {
                if (x2 == 0 || x2 == 1) {
                    return "Attacked card does not belong to the enemy.";
                }
            }
            tank = checkTank(2);
        } else {
            if (card1.getName().equals("Disciple")) {
                if (x2 == 0 || x2 == 1) {
                    return "Attacked card does not belong to the current player.";
                }
                card1.useAbility(card2);
                card1.setDoneAttack(1);
                return null;
            } else {
                if (x2 == 2 || x2 == row3) {
                    return "Attacked card does not belong to the enemy.";
                }
            }
            tank = checkTank(1);
        }

        if (tank != null) {
            if (!tank.equals(card2)) {
                return "Attacked card is not of type 'Tank'.";
            }
        }

        card1.useAbility(card2);
        card1.setDoneAttack(1);
        if (card2.getHealth() <= 0) {
            eliminateCard(x2, y2);
        }
        return null;
    }

    /**
     *
     * @param output
     * It add in output the frozen cards on the table
     */
    public void getFrozenCardsOnTable(final ArrayNode output) {
        ObjectMapper tmpMap = new ObjectMapper();
        ObjectNode tmp = tmpMap.createObjectNode();
        ArrayNode result = tmpMap.createArrayNode();
        for (int i = 0; i < numberRows; i++) {
            for (int j = 0; j < numberColumns; j++) {
                Minion aux = getCard(i, j);
                if (aux != null && aux.isFrozen()) {
                    ObjectNode node = tmpMap.createObjectNode();
                    node.put("mana", aux.getMana());
                    node.put("attackDamage", aux.getAttackDamage());
                    node.put("health", aux.getHealth());
                    node.put("description", aux.getDescription());
                    ArrayNode arrayColors = tmpMap.createArrayNode();
                    for (int k = 0; k < aux.getColors().size(); k++) {
                        arrayColors.add(aux.getColors().get(k));
                    }
                    node.set("colors", arrayColors);
                    node.put("name", aux.getName());
                    result.add(node);
                }
            }
        }
        tmp.put("command", "getFrozenCardsOnTable");
        tmp.set("output", result);
        output.add(tmp);
    }

    /**
     *
     * @param row
     * @return a row from the table
     */
    public ArrayList<Minion> getRow(final int row) {
        return table.get(row);
    }

    /**
     *
     * @param player
     * @param row
     * @return null if the ability is successful, otherwise
     *          returns error
     */
    public String heroAbility(final Player player, final int row) {
        Hero hero = player.getHero();
        if (player.getMana() < hero.getMana()) {
            return "Not enough mana to use hero's ability.";
        }

        if (hero.getDoneAttack() == 1) {
            return "Hero has already attacked this turn.";
        }

        String name = hero.getName();
        if (name.equals("Lord Royce") || name.equals("Empress Thorina")) {
            if (player.getId() == 1) {
                if (row == 2 || row == row3) {
                    return "Selected row does not belong to the enemy.";
                }
            } else {
                if (row == 0 || row == 1) {
                    return "Selected row does not belong to the enemy.";
                }
            }
        }

        if (name.equals("General Kocioraw") || name.equals("King Mudface")) {
            if (player.getId() == 1) {
                if (row == 0 || row == 1) {
                    return "Selected row does not belong to the current player.";
                }
            } else {
                if (row == 2 || row == row3) {
                    return "Selected row does not belong to the current player.";
                }
            }
        }

        ArrayList<Minion> minions = getRow(row);
        hero.useAbility(minions);
        player.setMana(player.getMana() - hero.getMana());
        hero.setDoneAttack(1);
        return null;
    }

    /**
     *
     * @param id
     * set the field doneAttack to 0 for every minion
     *  from a player
     */
    public void setMinions(final int id) {
        int start = 0;
        int max = 0;
        if (id == 1) {
            start = 2;
            max = numberRows;
        } else {
            start = 0;
            max = 2;
        }
        for (int i = start; i < max; i++) {
            for (int j = 0; j < numberColumns; j++) {
                if (table.get(i).get(j) != null) {
                    table.get(i).get(j).setDoneAttack(0);
                }
            }
        }
    }

    /**
     *
     * @param id
     *  unfrozen the minions for a player
     */
    public void unfrozenMinions(final int id) {
        int start;
        int max;
        if (id == 1) {
           start = 2;
           max = numberRows;
        } else {
            start = 0;
            max = 2;
        }
        for (int i = start; i < max; i++) {
            for (int j = 0; j < numberColumns; j++) {
                if (table.get(i).get(j) != null) {
                    table.get(i).get(j).unsetFrozen();
                }
            }
        }
    }
}
