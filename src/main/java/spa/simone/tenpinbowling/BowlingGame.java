/**
 * This file is part of Ten-Pin Bowling project.
 *
 * Ten-Pin Bowling is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *	
 * Ten-Pin Bowling is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *	
 * You should have received a copy of the GNU General Public License
 * along with this project. If not, see <http://www.gnu.org/licenses/>.
 */
package spa.simone.tenpinbowling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import spa.simone.tenpinbowling.model.ScoreBoard;
import spa.simone.tenpinbowling.model.ScoreBoardException;
import spa.simone.tenpinbowling.model.ScoreBoardImpl;

/**
 * This class contains the entry point of the Ten Pin Bowling game.
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}
 */
public class BowlingGame {

	private static List<ScoreBoard> players = new ArrayList<>(6);
	private static Scanner in = new Scanner(System.in);
	private static final int FRAMES = 10;
	private static final int SHOTS = 2;

	/**
	 * The entry point.
	 * 
	 * @param args
	 *           no args are required.
	 * @throws ScoreBoardException
	 */
	public static void maiSn(String[] args) throws ScoreBoardException {
		// init players
		initPlayers();
		// start game
		for (int frame = 1; frame <= FRAMES; frame++) {
			playGame(frame);
		}
		printStatistics();
	}

	/*
	 * Submits the name of the competitors.
	 */
	private static void initPlayers() {
		Set<String> names = new HashSet<>();
		boolean exit = false;
		ScoreBoard scoreBoard = null;
		String resp = null;
		
		// This loop add up to 6 people
		for (int i = 1; i <= 6 && !exit; i++) {
			System.out.print("Enter the name of the player number " + i + ": ");
			String name = in.next();
			// this loop checks whether the name of the player already exists
			while (!names.add(name)) {
				System.out.print("The player " + name + " already exists. Enter another name: ");
				name = in.next();
			}
			// Creates a new player
			scoreBoard = new ScoreBoardImpl();
			scoreBoard.setPlayerName(name);
			// Adds the player to the list
			players.add(scoreBoard);
			// If we didn't reached the maximum number of competitors
			if (i < 6) {
				// Allows the user to add new players or start to play the game
				do {
					System.out.print("Do you want to add another player? \"[y/n]\": ");
					resp = in.next();
					System.out.println();
					if (resp.equalsIgnoreCase("n")) {
						exit = true;
						break;
					}
				} while (!resp.equalsIgnoreCase("y"));
			}
		}
	}

	/*
	 * This method performs the scores submission for each player.
	 */
	private static void playGame(int frame) throws ScoreBoardException {
		if (!isLastFrame(frame)) {
			playNonFinalFrames(frame);
		} else {
			playTheLastFrame();
		}
	}

	/*
	 * Runs all non-final frames.
	 */
	private static void playNonFinalFrames(int frame) throws NumberFormatException,
			ScoreBoardException {
		System.out.println("---> Frame " + frame);
		System.out.println();
		// for each player
		for (ScoreBoard scoreBoard : players) {
			System.out.println("- " + scoreBoard.getPlayerName());
			System.out.println();
			// it has two shots
			for (int shot = 1; shot <= SHOTS; shot++) {
				System.out.print("    SHOT " + shot + ": ");
				// sets the number of knocked down pins
				scoreBoard.setPins(frame, shot, Integer.valueOf(in.next()));
				System.out.println();
				// if is strike, go to the next player
				if (scoreBoard.isStrike(frame)) {
					System.out.println("      Strike!");
					System.out.println();
					break;
				} else if (scoreBoard.isSpare(frame)) {
					System.out.println("      Spare!");
					System.out.println();
				}
			}
		}
	}

	/*
	 * Runs the last frame (manage the extra shots).
	 */
	private static void playTheLastFrame() throws NumberFormatException, ScoreBoardException {
		System.out.println("---> Frame 10");
		System.out.println();
		// for each player
		for (ScoreBoard scoreBoard : players) {
			System.out.println("- " + scoreBoard.getPlayerName());
			System.out.println();
			System.out.print("    SHOT 1: ");
			// First shot
			scoreBoard.setPins(10, 1, Integer.valueOf(in.next()));
			System.out.println();
			// if is strike (get bonus)
			if (scoreBoard.isStrikeOnLastFrame()) {
				System.out.println("      Strike! You got two extra shots.");
				System.out.println();
				System.out.print("    EXTRA SHOT 1: ");
			} else {
				System.out.print("    SHOT 2: ");
			}
			scoreBoard.setPins(10, 2, Integer.valueOf(in.next()));
			System.out.println();
			if (scoreBoard.isSpareOnLastFrame()) {
				System.out.println("      Spare! You got one extra shot.");
				System.out.println();
			}
			if (scoreBoard.isStrikeOnLastFrame()) {
				System.out.print("    EXTRA SHOT 2: ");
				scoreBoard.setPins(10, 3, Integer.valueOf(in.next()));
			} else if (scoreBoard.isSpareOnLastFrame()) {
				System.out.print("    EXTRA SHOT 1: ");
				scoreBoard.setPins(10, 3, Integer.valueOf(in.next()));
			}
			System.out.println();
		}
	}

	/*
	 * Prints the scoreboard, with a bit of statistics.
	 */
	private static void printStatistics() {
		System.out.println("-- STATISTICS");
		System.out.println();
		for (ScoreBoard scoreBoard : players) {
			System.out.println(scoreBoard.serializeScoreBoard());
		}
	}

	private static boolean isLastFrame(int frame) {
		return frame == 10;
	}

}