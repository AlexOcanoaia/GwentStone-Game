package org.poo.player;

import java.util.ArrayList;

import org.poo.card.Hero;
import org.poo.card.Minion;
import org.poo.fileio.CardInput;

public class Player {
    private int mana = 0;
    private int id;
    private int numberOfDecks;
    private int cardsInDeck;
    private ArrayList<ArrayList<Minion>> decks = new ArrayList<>();
    private ArrayList<Minion> deck = new ArrayList<>();
    private ArrayList<Minion> hand = new ArrayList<>();
    private Hero hero = new Hero();
    private int doneRound = 0;

    /**
     *
     * @param doneRound
     * set doneRound field
     */
    public void setDoneRound(final int doneRound) {
        this.doneRound = doneRound;
    }

    /**
     *
     * @return doneRound
     */
    public int getDoneRound() {
        return doneRound;
    }

    /**
     *
     * @param hand
     *  set hand field
     */
    public void setHand(final ArrayList<Minion> hand) {
        this.hand = hand;
    }

    /**
     *
     * @return hand
     */
    public ArrayList<Minion> getHand() {
        return hand;
    }

    /**
     *
     * @param index
     * remove a card from the hand
     */
    public void removeCard(final int index) {
        hand.remove(index);
    }

    /**
     *
     * @param mana
     * set the mana field
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     *
     * @return mana
     */
    public int getMana() {
        return mana;
    }

    /**
     *
     * @param id
     * set the id field
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param numberOfDecks
     * set the numberOfDecks field
     */
    public void setnumberOfDecks(final int number) {
        this.numberOfDecks = number;
    }

    /**
     *
     * @return numberOfDecks
     */
    public int getnumberOfDecks() {
        return numberOfDecks;
    }

    /**
     *
     * @param cardsInDeck
     * set cardsInDeck field
     */
    public void setcardsInDeck(final int number) {
        this.cardsInDeck = number;
    }

    /**
     *
     * @return cardsInDeck
     */
    public int getcardsInDeck() {
        return cardsInDeck;
    }

    /**
     *
     * @param deck
     * set the deck field
     */
    public void setDeck(final ArrayList<Minion> deck) {
        this.deck = deck;
    }

    /**
     *
     * @return deck
     */
    public ArrayList<Minion> getDeck() {
        return deck;
    }

    /**
     *
     * @param minion
     * add a card in hand
     */
    public void addCardinHand(final Minion minion) {
        hand.add(minion);
        deck.remove(minion);
    }

    /**
     *
     * @param decks
     * set the decks field
     */
    public void setDecks(final ArrayList<ArrayList<Minion>> decks) {
        this.decks = decks;
    }

    /**
     *
     * @return decks
     */
    public ArrayList<ArrayList<Minion>> getDecks() {
        return decks;
    }

    /**
     *
     * @param i
     * select a deck
     */
    public void selectDeck(final int i) {
        deck = decks.get(i);
    }

    /**
     *
     * @param hero
     * set the hero
     */
    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    /**
     *
     * @return hero
     */
    public Hero getHero() {
        return hero;
    }

    /**
     *
     * @param cards
     * make a cast from CardInput to Minion
     */
    public void initializeDecks(final ArrayList<ArrayList<CardInput>> cards) {
        for (int i = 0; i < cards.size(); i++) {
            ArrayList<Minion> row = new ArrayList<>();
            for (int j = 0; j < cards.get(i).size(); j++) {
                int mana2 = cards.get(i).get(j).getMana();
                int health = cards.get(i).get(j).getHealth();
                int attackDamage =  cards.get(i).get(j).getAttackDamage();
                String description =  cards.get(i).get(j).getDescription();
                ArrayList<String> colors =  cards.get(i).get(j).getColors();
                String name =  cards.get(i).get(j).getName();
                row.add(new Minion(mana2, health, attackDamage, description, colors, name, false));
            }
            decks.add(row);
        }
    }

    /**
     *
     * @param heroCard
     * make a cast from CardInput to Hero
     */
    public void initializeHero(final CardInput heroCard) {
        this.getHero().setMana(heroCard.getMana());
        this.getHero().setDescription(heroCard.getDescription());
        this.getHero().setColors(heroCard.getColors());
        this.getHero().setName(heroCard.getName());
    }
}
