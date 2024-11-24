package org.poo.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.poo.card.Hero;
import org.poo.card.Minion;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.Input;
import org.poo.player.Player;
import org.poo.table.Table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Game {
    private int number_of_turn = 1;
    private int currentPlayer;
    private Player player1 = new Player();
    private Player player2 = new Player();
    ObjectMapper map = new ObjectMapper();
    Table table = new Table();
    int endGame = 0;

    public void shuffleDeck(Player player, long seed) {
        Collections.shuffle(player.getDeck(), new Random(seed));
    }

    public void startGame(Input input, int numberOfGames) {
        player1.setCards_in_deck(input.getPlayerOneDecks().getNrCardsInDeck());
        player1.setNumber_of_decks(input.getPlayerOneDecks().getNrDecks());
        player1.initializeDecks(input.getPlayerOneDecks().getDecks());

        player2.setCards_in_deck(input.getPlayerTwoDecks().getNrCardsInDeck());
        player2.setNumber_of_decks(input.getPlayerTwoDecks().getNrDecks());
        player2.initializeDecks(input.getPlayerTwoDecks().getDecks());

        int indexDeck = input.getGames().get(numberOfGames).getStartGame().getPlayerOneDeckIdx();
        long seed = input.getGames().get(numberOfGames).getStartGame().getShuffleSeed();
        player1.setDeck(player1.getDecks().get(indexDeck));
        shuffleDeck(player1, seed);

        indexDeck = input.getGames().get(numberOfGames).getStartGame().getPlayerTwoDeckIdx();
        player2.setDeck(player2.getDecks().get(indexDeck));
        shuffleDeck(player2, seed);

        player1.initializeHero(input.getGames().get(numberOfGames).getStartGame().getPlayerOneHero());
        player2.initializeHero(input.getGames().get(numberOfGames).getStartGame().getPlayerTwoHero());

        currentPlayer = input.getGames().get(numberOfGames).getStartGame().getStartingPlayer();
        player1.setId(1);
        player2.setId(2);
    }

    public void startRound() {
        if (player1.getDeck().size() != 0) {
            player1.addCardinHand(player1.getDeck().get(0));
        }
        if (player2.getDeck().size() != 0) {
            player2.addCardinHand(player2.getDeck().get(0));
        }
        player1.setMana(player1.getMana() + number_of_turn);
        player2.setMana(player2.getMana() + number_of_turn);
        number_of_turn++;
    }

    public void endTurn() {
        table.unfrozenMinions(currentPlayer);
        if (currentPlayer == 1) {
            player1.setDoneRound(1);
            table.setMinions(1);
            currentPlayer = 2;
        } else {
            player2.setDoneRound(1);
            table.setMinions(2);
            currentPlayer = 1;
        }
        if (player1.getDoneRound() == 1 &&
            player2.getDoneRound() == 1) {
                
            player1.setDoneRound(0);
            player2.setDoneRound(0);
            startRound();
        }
    }

    public void placeCard(int index, ArrayNode output) {
        ObjectNode tmp = map.createObjectNode();
        tmp.put("command", "placeCard");
        tmp.put("handIdx", index);
        String result = null;
        if (currentPlayer == 1) {
            ArrayList<Minion> cardsList = player1.getHand();
            if (index >= cardsList.size() || index < 0) {
                System.out.println("Invalid index");
            } else {
                result = table.addCardtoTable(cardsList.get(index), player1);
                if (result == null) {
                    cardsList.remove(index);
                    player1.setHand(cardsList);
                }
            }
        } else {
            ArrayList<Minion> cardsList = player2.getHand();
            if (index >= cardsList.size() || index < 0) {
                System.out.println("Invalid index");
            } else {
                result = table.addCardtoTable(cardsList.get(index), player2);
                
                if (result == null) {
                    cardsList.remove(index);
                    player2.setHand(cardsList);
                }
            }
        }
        if (result != null) {
            tmp.put("error", result);
            output.add(tmp);
        }
    }

    public void getPlayerDeck(int index, final ArrayNode output) {
        ArrayList<Minion> deck = new ArrayList<>();
        if (index == 1) {
            deck = player1.getDeck();
        } else {
            deck = player2.getDeck();
        }
        ObjectNode tmp = map.createObjectNode();
        tmp.put("command", "getPlayerDeck");
        tmp.put("playerIdx", index);
        ArrayNode array = map.createArrayNode();
        for (int i = 0; i < deck.size(); i++) {
            ObjectNode node = map.createObjectNode();
            node.put("mana", deck.get(i).getMana());
            node.put("attackDamage", deck.get(i).getAttackDamage());
            node.put("health", deck.get(i).getHealth());
            node.put("description", deck.get(i).getDescription());
            ArrayNode arrayColors = map.createArrayNode();
            for (int j = 0; j < deck.get(i).getColors().size(); j++) {
                arrayColors.add(deck.get(i).getColors().get(j));
            }
            node.set("colors", arrayColors);
            node.put("name", deck.get(i).getName());
            array.add(node);
        }
        tmp.set("output", array);
        output.add(tmp);
    }

    public void getPlayerHero(int index, final ArrayNode output) {
        Hero hero = new Hero();
        if (index == 1) {
            hero = player1.getHero();
        } else {
            hero = player2.getHero();
        }
        ObjectNode tmp = map.createObjectNode();
        tmp.put("command", "getPlayerHero");
        tmp.put("playerIdx", index);
        ArrayNode array = map.createArrayNode();
        ObjectNode node = map.createObjectNode();
        node.put("mana", hero.getMana());
        node.put("description", hero.getDescription());
        ArrayNode arrayColors = map.createArrayNode();
        for (int j = 0; j < hero.getColors().size(); j++) {
            arrayColors.add(hero.getColors().get(j));
        }
        node.set("colors", arrayColors);
        node.put("name", hero.getName());
        node.put("health", hero.getHealth());
        // array.add(node);
        tmp.set("output", node);
        output.add(tmp);
    }

    public void getPlayerTurn(ArrayNode output) {
        ObjectNode node = map.createObjectNode();
        node.put("command", "getPlayerTurn");
        node.put("output", currentPlayer);
        output.add(node);
    }

    public void getCardsInHand(int index, ArrayNode output) {
        ArrayList<Minion> deck = new ArrayList<>();
        System.out.println("The index cardinHand is " + index);
        if (index == 1) {
            deck = player1.getHand();
        } else {
            deck = player2.getHand();
        }
        ObjectNode tmp = map.createObjectNode();
        tmp.put("command", "getCardsInHand");
        tmp.put("playerIdx", index);
        ArrayNode array = map.createArrayNode();
        for (int i = 0; i < deck.size(); i++) {
            ObjectNode node = map.createObjectNode();
            node.put("mana", deck.get(i).getMana());
            node.put("attackDamage", deck.get(i).getAttackDamage());
            node.put("health", deck.get(i).getHealth());
            node.put("description", deck.get(i).getDescription());
            ArrayNode arrayColors = map.createArrayNode();
            for (int j = 0; j < deck.get(i).getColors().size(); j++) {
                arrayColors.add(deck.get(i).getColors().get(j));
            }
            node.set("colors", arrayColors);
            node.put("name", deck.get(i).getName());
            array.add(node);
        }
        tmp.set("output", array);
        output.add(tmp);
    }

    public void getPlayerMana(int index, ArrayNode output) {
        ObjectNode node = map.createObjectNode();
        node.put("command", "getPlayerMana");
        node.put("playerIdx", index);
        if (index == 1) {
            node.put("output", player1.getMana());
        } else {
            node.put("output", player2.getMana());
        }
        output.add(node);
    }

    public void getCardsOnTable(ArrayNode output) {
        ObjectNode tmp = map.createObjectNode();
        tmp.put("command", "getCardsOnTable");
        ArrayNode array = map.createArrayNode();
        for (int i = 0; i < 4; i++) {
            ArrayNode rowArray = map.createArrayNode();
            for (int j = 0; j < 5; j++) {
                Minion aux = table.getTable().get(i).get(j);
                if (aux != null) {
                    ObjectNode node = map.createObjectNode();
                    node.put("mana", aux.getMana());
                    node.put("attackDamage", aux.getAttackDamage());
                    node.put("health", aux.getHealth());
                    node.put("description", aux.getDescription());
                    ArrayNode arrayColors = map.createArrayNode();
                    for (int k = 0; k < aux.getColors().size(); k++) {
                        arrayColors.add(aux.getColors().get(k));
                    }
                    node.set("colors", arrayColors);
                    node.put("name", aux.getName());
                    rowArray.add(node);
                }
            }
            array.add(rowArray);
        }
        tmp.set("output", array);
        output.add(tmp);
    }

    public void checkAttack(int x1, int y1, int x2, int y2, ArrayNode output) {
        System.out.println("This is the table");
        // table.showCards(output);
        String result = table.attackCard(x1, y1, x2, y2);
        if (result != null) {
            ObjectNode tmp = map.createObjectNode();
            tmp.put("command", "cardUsesAttack");
            ObjectNode node1 = map.createObjectNode();
            node1.put("x", x1);
            node1.put("y", y1);
            tmp.set("cardAttacker", node1);
            ObjectNode node2 = map.createObjectNode();
            node2.put("x", x2);
            node2.put("y", y2);
            tmp.set("cardAttacked", node2);
            tmp.put("error", result);
            output.add(tmp);
        }
    }

    public void getCardAtPosition(int x, int y, ArrayNode output) {
        Minion aux = table.getCard(x, y);
        ObjectNode tmp = map.createObjectNode();
        tmp.put("command", "getCardAtPosition");
        ObjectNode node1 = map.createObjectNode();
        tmp.put("x", x);
        tmp.put("y", y);
        if (aux != null) {
            ObjectNode node = map.createObjectNode();
            node.put("mana", aux.getMana());
            node.put("attackDamage", aux.getAttackDamage());
            node.put("health", aux.getHealth());
            node.put("description", aux.getDescription());
            ArrayNode arrayColors = map.createArrayNode();
            for (int k = 0; k < aux.getColors().size(); k++) {
                arrayColors.add(aux.getColors().get(k));
            }
            node.set("colors", arrayColors);
            node.put("name", aux.getName());
            tmp.set("output", node);
        } else {
            tmp.put("output", "No card available at that position.");
        }
        output.add(tmp);
    }

    public void cardUsesAbility(int x1, int y1, int x2, int y2, ArrayNode output) {
        String result = table.cardAbility(x1, y1, x2, y2);
        if (result != null) {
            ObjectNode tmp = map.createObjectNode();
            tmp.put("command", "cardUsesAbility");
            ObjectNode node1 = map.createObjectNode();
            node1.put("x", x1);
            node1.put("y", y1);
            tmp.set("cardAttacker", node1);
            ObjectNode node2 = map.createObjectNode();
            node2.put("x", x2);
            node2.put("y", y2);
            tmp.set("cardAttacked", node2);
            tmp.put("error", result);
            output.add(tmp);
        }
    }

    public String attackHero(int x, int y) {
        Minion minion = table.getCard(x, y);
        if (minion.isFrozen() == true) {
            return "Attacker card is frozen.";
        }
        if (minion.getDoneAttack() == 1) {
            return "Attacker card has already attacked this turn.";
        }
        Minion tank = null;
        int id = 0;
        if (x == 0 || x == 1) {
            id = 2;
            tank = table.checkTank(2);
        } else {
            id = 1;
            tank = table.checkTank(1);
        }

        if (tank != null) {
            return "Attacked card is not of type 'Tank'.";
        }

        minion.setDoneAttack(1);
        if (id == 1) {
            int health = player2.getHero().getHealth() - minion.getAttackDamage();
            if (health <= 0) {
                return "Player one killed the enemy hero.";
            }
            player2.getHero().setHealth(health);
        } else {
            int health = player1.getHero().getHealth() - minion.getAttackDamage();
            if (health <= 0) {
                return "Player two killed the enemy hero.";
            }
            player1.getHero().setHealth(health);
        }
        return null;
    }
    
    public void useAttackHero(int x, int y, ArrayNode output) {
        String result = attackHero(x, y);
        ObjectNode tmp = map.createObjectNode();
        if (result != null) {
            if (result.equals("Player one killed the enemy hero.") == true ||
            result.equals("Player two killed the enemy hero.") == true ) {
                tmp.put("gameEnded", result);
                endGame = 1;
            } else {
                tmp.put("command", "useAttackHero");
                ObjectNode node1 = map.createObjectNode();
                node1.put("x", x);
                node1.put("y", y);
                tmp.set("cardAttacker", node1);
                tmp.put("error", result);
            }
            output.add(tmp);
        }
    }

    public void output(Input input, int numberOfGame, final ArrayNode output) {
        table.initializeTable();
        ArrayList<ActionsInput> actions = input.getGames().get(numberOfGame).getActions();
        for (int i = 0; i < actions.size(); i++) {
            System.out.println(actions.get(i).getCommand());
            if (actions.get(i).getCommand().equals("placeCard") == true) {
                System.out.println("Is " + actions.get(i).getHandIdx());
            }
            switch (actions.get(i).getCommand()) {
                case "getPlayerDeck":
                    int index = actions.get(i).getPlayerIdx();
                    getPlayerDeck(index, output);
                    break;
                case "getPlayerHero":
                    int indexHero = actions.get(i).getPlayerIdx();
                    getPlayerHero(indexHero, output);
                    break;
                case "getPlayerTurn":
                    getPlayerTurn(output);
                    break;
                case "endPlayerTurn":
                    endTurn();
                    break;
                case "placeCard":
                    int indexHand = actions.get(i).getHandIdx();
                    placeCard(indexHand, output);
                    break;
                case "getCardsInHand":
                    int indexCards = actions.get(i).getPlayerIdx();
                    getCardsInHand(indexCards, output);
                    break;
                case "getPlayerMana":
                    int indexMana = actions.get(i).getPlayerIdx();
                    getPlayerMana(indexMana, output);
                    break;
                case "getCardsOnTable":
                    getCardsOnTable(output);
                    break;
                case "cardUsesAttack":
                    int x1 = actions.get(i).getCardAttacker().getX();
                    int y1 = actions.get(i).getCardAttacker().getY();
                    int x2 = actions.get(i).getCardAttacked().getX();
                    int y2 = actions.get(i).getCardAttacked().getY();
                    checkAttack(x1, y1, x2, y2, output);
                    break;
                case "getCardAtPosition":
                    int x = actions.get(i).getX();
                    int y = actions.get(i).getY();
                    getCardAtPosition(x, y, output);
                    break;
                case "cardUsesAbility":
                    int abilityX1 = actions.get(i).getCardAttacker().getX();
                    int abilityY1 = actions.get(i).getCardAttacker().getY();
                    int abilityX2 = actions.get(i).getCardAttacked().getX();
                    int abilityY2 = actions.get(i).getCardAttacked().getY();
                    cardUsesAbility(abilityX1, abilityY1, abilityX2, abilityY2, output);
                    break;
                case "useAttackHero":
                    if (endGame == 0) {
                        int newX = actions.get(i).getCardAttacker().getX();
                        int newY = actions.get(i).getCardAttacker().getY();
                        useAttackHero(newX, newY, output);
                    }
                    break;
            }
        }
        System.out.println("\n");
    }
}
