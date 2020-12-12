This project is intended to get students thinking critically about code and solving problems without a clear correct solution. In this project, students will write an artificial intelligence that can play the board game En Garde. The rules are as follows:
* Components:
  * A deck of 25 cards - 5 cards each of the numbers 1 through 5
  * A board consisting of 23 spaces arranged in a straight line
  * A piece representing each player
* Setup:
  * Each player starts on opposing sides of the board
  * The deck is shuffled and each player is dealt a 5 card hand
* Gameplay:
  * Each player takes turns playing a card and moving exactly that many spaces forwards or backwards. It is illegal to move off the edge of the board or to move past the opposing player. It is illegal to play a card unless you perform the full exact movement of that card.
  * After playing a card, the card is sent to the discard pile and the player draws a new card from the deck.
  * If a player's move lands them exactly on top of the other player, they score a point and the round ends.
  * If the deck runs out, the player who is farther from their starting location scores a point and the round ends. In the event that both players are equally far from their starting location, no point is awarded for that round.
  * If a player tries to make an illegal move or has no legal moves on their turn, the round ends and the other player scores a point.
  * When the round ends, the players reset to their starting positions, the deck is reshuffled, and each player is dealt a new 5 card hand.
  * The first player to achieve 5 points wins the game.

For the assignment, the students are given the full codebase and asked to write a subclass of Player, which will require them to override the inherited abstract move() function. They will have to return the move they'd like to make. The spaces are numbered, so returning 3 moves them 3 spaces in the positive direction and -3 moves them 3 spaces in the negative direction. They may also override the start() function with code they want their bot to run at the start of each round. The Player class includes all the functions they'll need to get information about the game state - the Players do NOT have direct access to the Game object as that would allow for cheating.

You can use the Game class to run single games or the Tournament class to run lots of games among a group of bots. After running a game or games, if you have logging set to true (in the constructor for the game or tournament) a .eglog file will be created, which can be watched in the simulator program.

Looking in the [source code](src), you'll find all the game code, the abstract Player class and the code for a few demo bots, whose strategies are respectively:
* Aggressive Bot: Make the legal move that would put you closest to the enemy
* Cowardly Bot: Make the legal move that would put you farthest from the enemy
* Random Bot: Choose a legal move at random
I usually grade the students based on their performance against these 3 bots, with extra credit prizes for the top 3 students in the class bracket.

This project tends to be an awesome turning point in the class where the students start to identify with code as a fun and engaging activity. I always run a class double-elimination tournament, and folks get hugely competitive. When you're running the activity, I recommend starting by having the students play the game against each other. I have them make their own decks and boards using index cards, and coins for player pieces. Then come back together, talk about strategy insights, and then introduce the code.

NOTE: I'm using simplified rules to make the code easier - if you get the actual board game, the rulebook will have other rules that are not included in this game.
