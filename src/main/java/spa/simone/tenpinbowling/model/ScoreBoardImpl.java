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
package spa.simone.tenpinbowling.model;

import static spa.simone.tenpinbowling.model.Util.getIndex;
import static spa.simone.tenpinbowling.model.Validator.checkFrame;
import static spa.simone.tenpinbowling.model.Validator.checkPins;
import static spa.simone.tenpinbowling.model.Validator.checkShot;

/**
 * This class is an implementation of {@link ScoreBoard} interface.
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}
 */
public class ScoreBoardImpl implements ScoreBoard {

	private String playerName;
	private int[] shots;
	private int[] score;

	/**
	 * Instantiates the ScoreBoard object.
	 */
	public ScoreBoardImpl() {
		shots = new int[21];
		for (int i = 0; i < shots.length; i++) {
			shots[i] = 0;
		}
		score = new int[10];
		for (int i = 0; i < score.length; i++) {
			score[i] = 0;
		}
	}
	
	@Override
	public String getPlayerName() {
		return playerName;
	}
	
	@Override
	public void setPlayerName(String name) {
		this.playerName = name;
	}

	@Override
	public void computeFinalScore() {
		try {
			for (int i = 1; i < 8; i++) {
				// if is a normal case
				if (!isStrike(i) && !isSpare(i)) {
					score[i - 1] = getSumOf(i);
				} else if (isSpare(i)) { // if is a spare
					// 10 + (next shot)
					score[i - 1] = 10 + getShotsOf(i + 1)[0];
				} else if (isStrike(i)) { // if is a strike
					// 10 + (next two shots)
					score[i - 1] = 10;
					if (!isStrike(i + 1)) {
						score[i - 1] += getSumOf(i + 1);
					} else if (isStrike(i + 1)) {
						score[i - 1] += 10 + getShotsOf(i + 2)[0];
					}
				}
			}
			// eighth frame
			if (!isStrike(8) && !isSpare(8)) {
				score[7] = getSumOf(8);
			} else if (isSpare(8)) {
				score[7] = 10 + getShotsOf(9)[0];
			} else if (isStrike(8)) {
				score[7] = 10;
				if (!isStrike(9)) {
					score[7] += getSumOf(9);
				} else if (isStrike(9)) {
					score[7] += 10 + shots[18];
				}
			}
			// ninth frame
			if (!isStrike(9) && !isSpare(9)) {
				score[8] = getSumOf(9);
			} else if (isSpare(9)) {
				score[8] = 10 + shots[18];
			} else if (isStrike(9)) {
				score[8] = 10 + shots[18] + shots[19];
			}
			// tenth frame
			score[9] = shots[18] + shots[19] + shots[20];
		} catch (OutOfRangeException e) {
		}
	}

	@Override
	public int getFinalScore() {
		computeFinalScore();
		int totalScore = 0;
		for (int i = 0; i < score.length; i++) {
			totalScore += score[i];
		}
		return totalScore;
	}

	@Override
	public int getScoreOf(int frame) throws OutOfRangeException {
		checkFrame(frame);
		return score[frame - 1];
	}

	@Override
	public int[] getShotsOf(int frame) throws OutOfRangeException {
		checkFrame(frame);
		int[] temp = new int[2];
		temp[0] = shots[getIndex(frame - 1, 0)];
		temp[1] = shots[getIndex(frame - 1, 1)];
		return temp;
	}

	@Override
	public int getSpares() {
		int spares = 0;
		for (int frame = 1; frame <= 9; frame++) {
			try {
				if (isSpare(frame)) {
					spares++;
				}
			} catch (OutOfRangeException e) {
			}
		}
		if (isSpareOnLastFrame()) {
			spares++;
		}
		if (isStrikeAndSpareOnLastFrame()) {
			spares++;
		}
		return spares;
	}

	@Override
	public int getStrikes() {
		int strikes = 0;
		// for each non-final frame
		for (int frame = 1; frame <= 9; frame++) {
			try {
				if (isStrike(frame)) {
					strikes++;
				}
			} catch (OutOfRangeException e) {
			}
		}
		if (isStrikeOnLastFrame()) {
			strikes++;
		}
		if (isDoubleOnLastFrame()) {
			strikes++;
		}
		if (isTripleOnLastFrame() || isSpareAndStrikeOnLastFrame()) {
			strikes++;
		}
		return strikes;
	}

	@Override
	public int getSumOf(int frame) throws OutOfRangeException {
		checkFrame(frame);
		return shots[getIndex(frame - 1, 0)] + shots[getIndex(frame - 1, 1)];
	}

	@Override
	public boolean isDoubleOnLastFrame() {
		return shots[18] == 10 && shots[19] == 10;
	}

	@Override
	public boolean isOpen(int frame) throws OutOfRangeException {
		checkFrame(frame);
		return !isStrike(frame) && !isSpare(frame);
	}

	@Override
	public boolean isSpare(int frame) throws OutOfRangeException {
		checkFrame(frame);
		int s1 = getIndex(frame - 1, 0);
		int s2 = getIndex(frame - 1, 1);
		return !isStrike(frame) && (shots[s1] + shots[s2]) == 10;
	}

	@Override
	public boolean isSpareAndStrikeOnLastFrame() {
		// 1) The first shot is not a strike
		// 2) The sum of the first and the second shots is equals to 10
		// 3) The third shot is a strike
		return isSpareOnLastFrame() && shots[20] == 10;
	}

	@Override
	public boolean isSpareOnLastFrame() {
		// 1) The first shot is not a strike;
		// 2) The sum of the first and the second shots is equals to 10.
		return shots[18] != 10 && shots[18] + shots[19] == 10;
	}

	@Override
	public boolean isStrike(int frame) throws OutOfRangeException {
		checkFrame(frame);
		int index = getIndex(frame - 1, 0);
		return shots[index] == 10;
	}

	@Override
	public boolean isStrikeAndSpareOnLastFrame() {
		// 1) The first shot is a strike;
		// 2) The second shot is not a strike;
		// 3) The sum of the second and the third shots is equals to 10.
		return shots[18] == 10 && shots[19] != 10 && shots[19] + shots[20] == 10;
	}

	@Override
	public boolean isStrikeOnLastFrame() {
		return shots[18] == 10;
	}

	@Override
	public boolean isTripleOnLastFrame() {
		return shots[18] == 10 && shots[19] == 10 && shots[20] == 10;
	}

	@Override
	public String serializeScoreBoard() {
		StringBuilder builder = new StringBuilder();
		builder.append("-- ").append(playerName).append("\n\n");
		builder.append("Frame: ");
		for (int i = 1; i <= 10; i++) {
			builder.append("   " + i + "   ");
		}
		builder.append("\nPins:  ");
		for (int i = 0; i < shots.length; i++) {
			if (i % 2 == 0 && i != 20) {
				builder.append("| ");
			}
			builder.append(shots[i]).append(" ");
		}
		computeFinalScore();
		builder.append("\nScore: ");
		for (int i = 0; i < score.length; i++) {
			builder.append("|  ");
			if (i == 9) {
				builder.append("  ");
			}
			builder.append(score[i]).append("  ");
		}
		builder.append("\n\nFinal Score: ").append(getFinalScore()).append("\n");
		builder.append("Total Strikes: ").append(getStrikes()).append("\n");
		builder.append("Total Spares: ").append(getSpares()).append("\n");
		return builder.toString();
	}

	@Override
	public void setPins(int frame, int shot, int pins) throws ScoreBoardException {
		checkFrame(frame);
		checkShot(frame, shot);
		checkPins(frame, shot, pins, shots);
		int index = getIndex(frame - 1, shot - 1);
		shots[index] = pins;
	}

}