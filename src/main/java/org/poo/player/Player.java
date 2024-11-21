package org.poo.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.poo.card.Hero;
import org.poo.card.Minion;
import org.poo.fileio.CardInput;

public class Player {
    private int mana = 0;
    private int id;
    private int number_of_decks;
    private int cards_in_deck;
    ArrayList<ArrayList<Minion>> decks = new ArrayList<>();
    ArrayList<Minion> deck = new ArrayList<>();
    ArrayList<Minion> hand = new ArrayList<>();
    Hero hero = new Hero();

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNumber_of_decks(int number_of_decks) {
        this.number_of_decks = number_of_decks;
    }

    public int getNumber_of_decks() {
        return number_of_decks;
    }

    public void setCards_in_deck(int cards_in_deck) {
        this.cards_in_deck = cards_in_deck;
    }

    public int getCards_in_deck() {
        return cards_in_deck;
    }

    public void setDeck(ArrayList<Minion> deck) {
        this.deck = deck;
    }

    public ArrayList<Minion> getDeck() {
        return deck;
    }

    public void addCardinHand(Minion minion) {
        hand.add(minion);
        deck.remove(minion);
    }

    public void setDecks(ArrayList<ArrayList<Minion>> decks) {
        this.decks = decks;
    }

    public ArrayList<ArrayList<Minion>> getDecks() {
        return decks;
    }

    public void selectDeck(int i) {
        deck = decks.get(i);
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }

    public void initializeDecks(ArrayList<ArrayList<CardInput>> cards) {
        for (int i = 0; i < cards.size(); i++) {
            ArrayList<Minion> row = new ArrayList<>();
            for (int j = 0; j < cards.get(i).size(); j++) {
                int mana = cards.get(i).get(j).getMana();
                int health = cards.get(i).get(j).getHealth();
                int attackDamage =  cards.get(i).get(j).getAttackDamage();
                String description =  cards.get(i).get(j).getDescription();
                ArrayList<String> colors =  cards.get(i).get(j).getColors();
                String name =  cards.get(i).get(j).getName();
                row.add(new Minion(mana, health, attackDamage, description, colors, name));
            }
            decks.add(row);
        }
    } 

    public void initializeHero(CardInput heroCard) {
        this.getHero().setMana(heroCard.getMana());
        this.getHero().setDescription(heroCard.getDescription());
        this.getHero().setColors(heroCard.getColors());
        this.getHero().setName(heroCard.getName());
    }
}
