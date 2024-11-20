package org.poo.card;

public class Card {
    private int mana;
    private String description;
    private String colors;
    private String name;

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getColors() {
        return colors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
