package org.poo.card;

import java.util.ArrayList;

public class Card {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

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
     * @param description
     * set the description field
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param colors
     * set the colors field
     */
    public void setColors(final ArrayList<String> colors) {
       this.colors = colors;
    }

    /**
     *
     * @return colors
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     *
     * @param name
     * set the name field
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }
}
