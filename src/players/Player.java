package players;

import game.Card;
import game.Game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Player {

	private Game game;
	private String name;

	/**
	 * Creates a new player.
	 * 
	 * @param name
	 *            The player's name.
	 */
	public Player(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return An array of integers representing the values of cards in my hand.
	 */
	protected final int[] getHand() {
		Card[] hand = game.getHand(this);
		int[] hand_good = new int[hand.length];
		for (int i = 0; i < hand.length; i++) {
			hand_good[i] = hand[i].value;
		}
		return hand_good;
	}

	/**
	 * @return the move that your opponent executed last turn. If there was no
	 *         last move (because this is the first move of the round) returns
	 *         0.
	 */
	protected final int getLastMove() {
		return game.getLastMove();
	}

	/**
	 * @return my location on the board (an integer between 0 and 22 inclusive)
	 */
	protected final int getLocation() {
		return game.getLoc(this);
	}

	public String getName() {
		return name;
	}

	/**
	 * @return my opponent's location on the board (an integer between 0 and 22
	 *         inclusive)
	 */
	protected final int getOpponentLocation() {
		return game.getOtherLoc(this);
	}

	/**
	 * 
	 * @return my opponent's score. (Everyone starts with 0 points, and the
	 *         winner is the first to 5.)
	 */
	protected final int getOpponentScore() {
		return game.getOtherScore(this);
	}

	/**
	 * Returns all possible legal moves. That is, all integer values that are
	 * represented in my hand and their negatives excluding the values that
	 * would cause me to pass my opponent or drop off either side of the board.
	 * 
	 * @return A list of integers representing all of my possible legal moves.
	 */
	public final int[] getPossibleMoves() {
		List<Integer> possibilities = getPossibleMovesList();
		int[] out = new int[possibilities.size()];
		for (int i = 0; i < out.length; i++) {
			out[i] = possibilities.get(i);
		}
		return out;
	}

	public final List<Integer> getPossibleMovesList() {
		int[] hand = getHand();
		List<Integer> possibilities = new LinkedList<Integer>();
		int myLoc = getLocation();
		int oppLoc = getOpponentLocation();
		for (int c : hand) {
			if (!possibilities.contains(c) && myLoc + c < 23) {
				if (!(myLoc < oppLoc && myLoc + c > oppLoc)) {
					possibilities.add(c);
				}
			}
			if (!possibilities.contains(-1 * c) && myLoc - c >= 0) {
				if (!(myLoc > oppLoc && myLoc - c < oppLoc)) {
					possibilities.add(-1 * c);
				}
			}
		}
		Collections.sort(possibilities);
		return possibilities;
	}

	/**
	 * 
	 * @return My current score. Everyone starts with 0 points, and the first to
	 *         5 wins.
	 */
	protected final int getScore() {
		return game.getScore(this);
	}

	/**
	 * Returns the number of turns left in the round. Every round ends either
	 * when a hit is scored or when 14 turns have gone by. That is, each player
	 * gets at most 7 turns per round.
	 * 
	 * @return The number of turns remaining in the round.
	 */
	protected final int getTurnsRemaining() {
		return game.getTurnsLeft();
	}

	/**
	 * Returns what this player is going to do on the next turn.
	 * 
	 * @return An integer representing how many spaces the player will move.
	 */
	public abstract int move();

	/**
	 * Links the player with the game in which it is participating. DO NOT CALL
	 * THIS METHOD. IF YOU CALL THIS METHOD, YOU WILL LOSE.
	 * 
	 * @param game
	 *            The game in which this player is about to participate.
	 */
	public final void setGame(Game game) {
		this.game = game;
	}

	/**
	 * This function is called automatically at the start of each ROUND. That
	 * is, at the start of the game and every time the players reset positions,
	 * after they have drawn their starting hands for the round. It does nothing
	 * by default.
	 */
	public void start() {

	}

}
