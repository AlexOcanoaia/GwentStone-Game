package org.poo.statistics;

public class Statistics {
    private int numberGamesplayed = 0;
    private int playerOneWins = 0;
    private int playerTwoWins = 0;

    public void setNumberGamesplayed(int numberGamesplayed) {
        this.numberGamesplayed = numberGamesplayed;
    }
    
    public int getNumberGamesplayed() {
        return numberGamesplayed;
    }

    public void setPlayerOneWins(int playerOneWins) {
        this.playerOneWins = playerOneWins;
    }

    public int getPlayerOneWins() {
        return playerOneWins;
    }

    public void setPlayerTwoWins(int playerTwoWins) {
        this.playerTwoWins = playerTwoWins;
    }

    public int getPlayerTwoWins() {
        return playerTwoWins;
    }
}
