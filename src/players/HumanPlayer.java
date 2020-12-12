package players;

import java.util.Scanner;

/**
 * A human playable player. Use this if you want to try and play against your
 * AI. Great way to test how it fares against a specific circumstance.
 * 
 * @author eliotkaplan
 * 
 */
public class HumanPlayer extends Player {

	public HumanPlayer(String name) {
		super(name);
	}

	Scanner sc = new Scanner(System.in);

	@Override
	public int move() {
		System.out.print("Your hand contains: | ");
		for (int c : getHand()) {
			System.out.print(c + " | ");
		}
		System.out.print("\nYour options are: | ");
		for (int i : getPossibleMoves()) {
			System.out.print(i + " | ");
		}
		System.out.println();
		System.out.println("You are at location " + getLocation());
		System.out.println("You have " + getScore() + " points.");
		System.out.println("Your opponent has " + getOpponentScore()
				+ " points.");
		System.out.println("There are " + getTurnsRemaining()
				+ " turns remaining in the round.");
		while (true) {
			System.out.println("What's your move?");
			try {
				return sc.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid entry. Please enter a number.");
				sc.next();
			}
		}
	}

}
