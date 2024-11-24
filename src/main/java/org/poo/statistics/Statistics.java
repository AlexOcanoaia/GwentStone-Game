package org.poo.statistics;

public class Statistics {
    private int numberGamesplayed = 0;
    private int playerOneWins = 0;
    private int playerTwoWins = 0;

    /**
     *
     * @param numberGamesplayed
     * set the numberGamesplayed field
     */
    public void setNumberGamesplayed(final int numberGamesplayed) {
        this.numberGamesplayed = numberGamesplayed;
    }

    /**
     *
     * @return numberGamesplayed
     */
    public int getNumberGamesplayed() {
        return numberGamesplayed;
    }

    /**
     *
     * @param playerOneWins
     * set the playerOneWins field
     */
    public void setPlayerOneWins(final int playerOneWins) {
        this.playerOneWins = playerOneWins;
    }

    /**
     *
     * @return playerOneWins
     */
    public int getPlayerOneWins() {
        return playerOneWins;
    }

    /**
     *
     * @param playerTwoWins
     * set the playerTwoWins field
     */
    public void setPlayerTwoWins(final int playerTwoWins) {
        this.playerTwoWins = playerTwoWins;
    }

    /**
     *
     * @return playerTwoWins
     */
    public int getPlayerTwoWins() {
        return playerTwoWins;
    }
}
