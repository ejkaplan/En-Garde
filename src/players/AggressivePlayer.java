package players;

import java.util.Arrays;

/**
 * A player that always chooses the legal move that brings it closest to its
 * opponent.
 * 
 * @author eliotkaplan
 * 
 */
public class AggressivePlayer extends Player {

	public AggressivePlayer(String name) {
		super(name);
	}

	public int move() {
		int[] poss = getPossibleMoves();
		Arrays.sort(poss);
		if (getLocation() > getOpponentLocation())
			return poss[0];
		else {
			return poss[poss.length-1];
		}
	}

}
