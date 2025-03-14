# GwentStone Game

This project represent a game that combines features
from Heartstone and Gwent. The game is designed for two
players.

## Rules of the game

The game table have 4 rows (2 for each player).
Each player choose a deck and receive a hero.
The game is based on rounds (each player has one
round). At the beginning of every round the players
receive mana (a very important resource). The scope
of the game is that the players should place the cards
on the table (this operation cost mana) and use them against
the opponent hero.

### Description of the packages

I have created 5 Java packages: Card (Card, Minion, Hero),
Game (Game), Player (Player), Table (Table), Statistics (Statistics).
In parantheses i wrote the classes that every package contains.

- In the Card package i implemented getters, setters and useAbility
method. In useAbility method i use a switch with the name of the card
as a parameter. I check the name of the card and use the ability
corresponding to every card (some cards don't have abilities).

- In the Statistics package i have 3 fields: numberGamesplayed,
playerOneWins, playerTwoWins. I use this package to retain the
number of games played and the victories for each player.

- In the Player package i retain the decks from the start,
the deck that the player will use to play the game, the hero,
mana , id. In the Player class i implemented setters,
getters, addCardInHand method (it add a card from the deck),
initializeDecks (has the role to make a cast from CardInput to
Minion) and initializeHero (make cast from CardInput to Hero)

- In the Table package I implemented one class (Table). In this
class I represent the table with a ArrayList<ArrayList<Minion>> (has
4 rows and 5 columns). Method `addCardtoTable` has the role to
add a card on the table.
I verify the minions name to put the card on the right row.
After i place the card on the first free spot that i find. It
returns null if the card is place corectly. Otherwise, it
returns error.
    In the Table class, i have methods that represents a part
of the gameplay (attackCard, cardAbility, heroAbility). In each
method, first i verify the errors. Like the method addCardToTable
each method returns null if the operation is successful.

- In the Game package I have the most of the gameplay and
the most of the output commands. Method startGame has the role
to take all the information from the input files and place it
in the fields of the players. It prepares the decks (it shuffled
them).
    Method startRound it increment the mana each player has and
and a new card in each player hand. Method endTurn it ends the
turn for the current player (unfroze all the minions that the
player has on table)
    For the final I have implement the endGame method that
increments the player victories.
In the Game class I coded the output commands (I used
ObjectNode and ArrayNode class to place all the information
that the output needs). In the output method i verify each
command with the help from a switch.
