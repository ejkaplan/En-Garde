package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import players.*;

/**
 * Just replace add whatever players you want to participate in the tournament
 * to the players list. Make sure no two players have the same name or else the
 * scores will come out wrong. Scores at the end are printed out in no
 * particular order.
 *
 * @author eliotkaplan
 *
 */
public class Tournament {

	public static void main(String[] args) {
		Tournament t = new Tournament();
		t.addPlayer(new AggressivePlayer("Aggro"));
		t.addPlayer(new RandomPlayer("Rando"));
		t.addPlayer(new CowardlyPlayer("Coward"));
		// THE GAME:
		t.playTournament(100, true);
		// t.gradeAll(1000);
	}

	private LinkedList<Player> players = new LinkedList<Player>();
	private HashMap<Player, Integer> scores = new HashMap<Player, Integer>();

	public void addPlayer(Player player) {
		players.add(player);
	}

	public HashMap<Player, Integer> getScores() {
		return scores;
	}

	public ArrayList<Player> playTournament(int gamesPerMatchup) {
		return playTournament(gamesPerMatchup, true);
	}

	public ArrayList<Player> playTournament(int gamesPerMatchup, boolean logMe) {
		if (gamesPerMatchup > 10)
			logMe = false;
		Random r = new Random();
		while (players.size() > 0) {
			Player p0 = players.pop();
			for (Player p1 : players) {
				for (int i = 0; i < gamesPerMatchup; i++) {
					Player winner;
					if (r.nextBoolean()) {
						winner = new Game(p0, p1, false, logMe).play();
					} else {
						winner = new Game(p1, p0, false, logMe).play();
					}
					Player loser;
					if (winner == p0)
						loser = p1;
					else
						loser = p0;
					// System.out.println(winner.name + " beat " + loser.name);
					if (scores.containsKey(winner)) {
						scores.put(winner, scores.get(winner) + 1);
					} else {
						scores.put(winner, 1);
					}
					if (!scores.containsKey(loser)) {
						scores.put(loser, 0);
					}
				}
			}
		}
		ArrayList<Player> out = new ArrayList<Player>(scores.keySet());
		Collections.sort(out, new Comparator<Player>() {
			public int compare(Player p0, Player p1) {
				return scores.get(p0) - scores.get(p1);
			}
		});
		for (Player dood : out) {
			double percentWon = 100. * scores.get(dood) / (gamesPerMatchup * (out.size() - 1));
			System.out.println(dood.getName() + ": " + percentWon + "%");
		}
		return out;
	}

	public void gradeAll(int gamesPerMatchup) {
		for (Player p : players) {
			System.out.println(p.getName() + ": " + Math.round(150.0*grade(p, gamesPerMatchup)));
		}
	}

	public double grade(Player p, int gamesPerMatchup) {
		Random r = new Random();
		ArrayList<Player> graders = new ArrayList<Player>();
		graders.add(new RandomPlayer("Rando"));
		graders.add(new CowardlyPlayer("Coward"));
		graders.add(new AggressivePlayer("Aggro"));
		for (Player p1 : graders) {
			for (int i = 0; i < gamesPerMatchup; i++) {
				Player winner;
				if (r.nextBoolean()) {
					winner = new Game(p, p1, false, false).play();
				} else {
					winner = new Game(p1, p, false, false).play();
				}
				Player loser;
				if (winner == p)
					loser = p1;
				else
					loser = p;
				// System.out.println(winner.name + " beat " + loser.name);
				if (scores.containsKey(winner)) {
					scores.put(winner, scores.get(winner) + 1);
				} else {
					scores.put(winner, 1);
				}
				if (!scores.containsKey(loser)) {
					scores.put(loser, 0);
				}

			}
		}
		return 1.0*scores.get(p) / (graders.size()*gamesPerMatchup);
	}
}
