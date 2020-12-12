package game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import players.*;

/**
 * A game of En Garde. Each game outputs a log file which can be used by the
 * simulator to provide a visual replay of the game.
 *
 * @author eliotkaplan
 *
 */
public class Game {

	public static void main(String[] args) {
		// Replace these players with your players to test.
		Player player0 = new RandomPlayer("Random");
		Player player1 = new AggressivePlayer("Aggro");
		Game g = new Game(player0, player1, true, true);
		g.play();
	}

	private Player p0, p1;
	private Deck deck;
	private Hand h0, h1;
	private int[] playerLoc = { 0, 22 };
	private int[] score = { 0, 0 };
	private BufferedWriter out;
	private boolean p0Turn;
	private boolean p0Start;
	private boolean verbose = true;
	private boolean logMe;
	private int lastMove = 0;

	/**
	 * Creates a new game between two players.
	 *
	 * @param p0
	 *            A player who is participating in the game.
	 * @param p1
	 *            A player who is participating in the game.
	 */
	public Game(Player p0, Player p1) {
		this(p0, p1, true, true);
	}

	/**
	 * Creates a new game between two players.
	 *
	 * @param p0
	 *            A player who is participating in the game.
	 * @param p1
	 *            A player who is participating in the game.
	 * @param verbose
	 *            Whether or not the game should print updates live.
	 */
	public Game(Player p0, Player p1, boolean verbose, boolean logMe) {
		this.verbose = verbose;
		this.logMe = logMe;
		Date d = new Date();
		if (logMe) {
			try {
				out = new BufferedWriter(
						new FileWriter("Logs/" + p0.getName() + "_vs_" + p1.getName() + "_" + d.getTime() + ".eglog"));
			} catch (IOException e) {
			}
		}
		deck = new Deck();
		this.p0 = p0;
		this.p1 = p1;
		p0.setGame(this);
		p1.setGame(this);
		h0 = new Hand();
		h1 = new Hand();
		if (Math.random() < .5)
			p0Start = true;
		else
			p0Start = false;
	}

	private void attack(int attacker) {
		score[attacker] += 1;
		log(attacker);
		if (score[0] < 5 && score[1] < 5)
			resetPlayers();
	}

	/**
	 * Returns a player's hand of cards.
	 *
	 * @param p
	 *            The player whose hand is to be returned.
	 * @return The Hand object corresponding to the p's cards.
	 */
	public Card[] getHand(Player p) {
		if (p == p0)
			return h0.toArray();
		return h1.toArray();
	}

	public int getLastMove() {
		return lastMove;
	}

	/**
	 * Returns a player's position on the board.
	 *
	 * @param p
	 *            A player
	 * @return p's position on the board.
	 */
	public int getLoc(Player p) {
		if (p == p0)
			return playerLoc[0];
		return playerLoc[1];
	}

	/**
	 * Returns a player's opponent's position on the board.
	 *
	 * @param p
	 *            A player
	 * @return p's opponent's position on the board.
	 */
	public int getOtherLoc(Player p) {
		if (p == p0)
			return playerLoc[1];
		return playerLoc[0];
	}

	/**
	 * Returns a player's opponent's score.
	 *
	 * @param p
	 *            A player
	 * @return p's opponent's score.
	 */
	public int getOtherScore(Player p) {
		if (p == p0)
			return score[1];
		return score[0];
	}

	/**
	 * Returns a player's score.
	 *
	 * @param p
	 *            A player
	 * @return p's score
	 */
	public int getScore(Player p) {
		if (p == p0)
			return score[0];
		return score[1];
	}

	/**
	 * @return the number of turns remaining in the round.
	 */
	public int getTurnsLeft() {
		return deck.size();
	}

	/**
	 * Decides if a given move is legal for player 0.
	 *
	 * @param move
	 *            The number of spaces p0 is trying to move (from -5 to 5)
	 * @return Whether or not the move is legal.
	 */
	private boolean isLegalP0Move(int move) {
		return p0.getPossibleMovesList().contains(move);
	}

	/**
	 * Decides if a given move is legal for player 1.
	 *
	 * @param move
	 *            The number of spaces p1 is trying to move (from -5 to 5)
	 * @return Whether or not the move is legal.
	 */
	private boolean isLegalP1Move(int move) {
		return p1.getPossibleMovesList().contains(move);
	}

	private void log() {
		log(-1);
	}

	/**
	 * Adds a line to the log file to reflect the current state of the game. The
	 * log is used exclusively for the benefit of the simulator and has no
	 * impact on actual gameplay.
	 * <p>
	 * There are several codes that refer to specific in game events. These
	 * codes are:
	 * <ul>
	 * <li>-1: Nothing special happening.
	 * <li>0: Player 0 just hit player 1
	 * <li>1: Player 1 just hit player 0
	 * <li>2: The round ended by timing out
	 * <li>3: Someone attempted an illegal move
	 * </ul>
	 *
	 * @param code
	 *            The code for referring to special events in game.
	 */
	private void log(int code) {
		if (!logMe)
			return;
		String s = playerLoc[0] + " ";
		s += playerLoc[1] + " ";
		for (Card c : h0.toArray()) {
			s += c.value + " ";
		}
		for (Card c : h1.toArray()) {
			s += c.value + " ";
		}
		s += score[0] + " ";
		s += score[1] + " ";
		s += deck.size() + " ";
		s += code + " ";
		if (code == 3) {
			if (p0Turn)
				s += "0 ";
			else
				s += "1 ";
		}
		s += "\n";
		try {
			out.write(s);
		} catch (IOException e) {
		}
	}

	/**
	 * Queries p0 for their move, checks the legality of said move and updates
	 * the board.
	 */
	private void p0Move() {
		int m = -10;
		try {
			m = p0.move();
		} catch (Exception e) {
		}
		if (isLegalP0Move(m)) {
			lastMove = m;
			h0.remove(Math.abs(m));
			deck.deal(h0);
			playerLoc[0] += m;
			if (verbose)
				System.out.println("Player 0 moves " + m);
			if (playerLoc[0] == playerLoc[1]) {
				playerLoc[0] -= m;
				attack(0);
				if (verbose)
					System.out.println("Player 0 scores!");
			} else {
				log();
			}
		} else {
			if (verbose)
				System.out.println("Illegal move.");
			score[1] += 1;
			log(3);
			resetPlayers();
		}
	}

	/**
	 * Queries p1 for their move, checks the legality of said move and updates
	 * the board.
	 */
	private void p1Move() {
		int m = -10;
		try {
			m = p1.move();
		} catch (Exception e) {
		}
		if (isLegalP1Move(m)) {
			lastMove = m;
			h1.remove(Math.abs(m));
			deck.deal(h1);
			playerLoc[1] += m;
			if (verbose)
				System.out.println("Player 1 moves " + m);
			if (playerLoc[0] == playerLoc[1]) {
				playerLoc[1] -= m;
				attack(1);
				if (verbose)
					System.out.println("Player 1 scores!");
			} else {
				log();
			}
		} else {
			if (verbose)
				System.out.println("Illegal move.");
			score[0] += 1;
			log(3);
			resetPlayers();
		}
	}

	/**
	 * Plays out the game.
	 */
	public Player play() {
		resetPlayers();
		log();
		while (score[0] < 5 && score[1] < 5) {
			if (deck.size() == 0) {
				if (verbose)
					System.out.println("ROUND END");
				if (playerLoc[0] > 22 - playerLoc[1]) {
					score[0] += 1;
					if (verbose)
						System.out.println("Player 0 scores!");
				} else if (playerLoc[0] < 22 - playerLoc[1]) {
					score[1] += 1;
					if (verbose)
						System.out.println("Player 1 scores!");
				}
				log(2);
				if (score[0] >= 5 || score[1] >= 5) {
					break;
				}
				resetPlayers();
			}
			if (verbose)
				printBoard();
			if (p0Turn)
				p0Move();
			else
				p1Move();
			p0Turn = !p0Turn;
			if (verbose)
				System.out.println(this);
		}
		log();
		if (logMe) {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		if (score[0] > score[1])
			return p0;
		else
			return p1;
	}

	/**
	 * Prints a text-based representation of the board with the players in their
	 * current positions.
	 */
	public void printBoard() {
		System.out.print(" ");
		for (int i = 0; i <= 23 * 4; i++) {
			System.out.print("-");
		}
		System.out.println();
		for (int i = 0; i < 23; i++) {
			System.out.print(" | ");
			boolean guyPrinted = false;
			for (int j = 0; j < playerLoc.length; j++) {
				if (i == playerLoc[j]) {
					System.out.print(j);
					guyPrinted = true;
				}
			}
			if (!guyPrinted)
				System.out.print(" ");
		}
		System.out.println(" |");
		System.out.print(" ");
		for (int i = 0; i <= 23 * 4; i++) {
			System.out.print("-");
		}
		System.out.println();
		System.out.print("   ");
		for (int i = 0; i < 10; i++) {
			System.out.print(i + "   ");
		}
		for (int i = 10; i < 23; i++) {
			System.out.print(i + "  ");
		}
		System.out.println();
	}

	/**
	 * Resets the board to its starting configuration and redeals hands.
	 */
	private void resetPlayers() {
		playerLoc[0] = 0;
		playerLoc[1] = 22;
		h0.clear();
		h1.clear();
		deck.resetAndShuffle();
		deck.deal(h0);
		deck.deal(h1);
		p0Start = !p0Start;
		p0Turn = p0Start;
		p0.start();
		p1.start();
		lastMove = 0;
		log();
	}

	@Override
	public String toString() {
		String s = "";
		s += "Score: " + score[0] + " - " + score[1] + "\n";
		s += "Turns remaining in this round: " + deck.size() + "\n";
		s += "\n";
		return s;
	}

}
