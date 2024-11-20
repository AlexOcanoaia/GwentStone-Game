package org.poo.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.poo.card.Hero;
import org.poo.card.Minion;

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

    public void shuffleDeck(int seed) {
        Collections.shuffle(deck, new Random(seed));
    }
}
