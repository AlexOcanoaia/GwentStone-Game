package org.poo.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.poo.card.Hero;
import org.poo.card.Minion;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.Input;
import org.poo.player.Player;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Game {
    private int number_of_turn = 1;
    private int starting_player;
    private Player player1 = new Player();
    private Player player2 = new Player();
    ObjectMapper map = new ObjectMapper();
    
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

        starting_player = input.getGames().get(numberOfGames).getStartGame().getStartingPlayer();
    }

    public void startTurn() {
        if (player1.getDeck() != null) {
            player1.addCardinHand(player1.getDeck().get(0));
        }
        if (player2.getDeck() != null) {
            player2.addCardinHand(player2.getDeck().get(0));
        }
        player1.setMana(player1.getMana() + number_of_turn);
        player2.setMana(player2.getMana() + number_of_turn);
        number_of_turn++;
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
        node.put("health", Hero.getHealth());
        // array.add(node);
        tmp.set("output", node);
        output.add(tmp);
    }

    public void getPlayerTurn(ArrayNode output) {
        ObjectNode node = map.createObjectNode();
        node.put("command", "getPlayerTurn");
        node.put("output", starting_player);
        output.add(node);
    }

    public void output(Input input, int numberOfGame, final ArrayNode output) {
        ArrayList<ActionsInput> actions = input.getGames().get(numberOfGame).getActions();
        for (int i = 0; i < actions.size(); i++) {
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
            }
        }
    }
}
