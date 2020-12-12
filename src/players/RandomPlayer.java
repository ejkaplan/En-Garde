package players;

import java.util.Random;

/**
 * A player that always chooses the legal move that brings it farthest from its
 * opponent.
 * 
 * @author eliotkaplan
 * 
 */
public class RandomPlayer extends Player {

	public RandomPlayer(String name) {
		super(name);
	}

	@Override
	public int move() {
		Random r = new Random();
		int[] legalMoves = this.getPossibleMoves();
		return legalMoves[r.nextInt(legalMoves.length)];
	}

}
