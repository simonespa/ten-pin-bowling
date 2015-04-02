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

/**
 * This API provides methods that allow the user to mark the number of pins
 * knocked down, view the score and the game stats.
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}
 */
public interface ScoreBoard {
	
	public String getPlayerName();
	
	public void setPlayerName(String name);

	/**
	 * Calculates the final score.
	 */
	public void computeFinalScore();

	/**
	 * Gets the final score of the game.
	 * 
	 * @return the final score.
	 */
	public int getFinalScore();

	/**
	 * Gets the score of the frame (including bonus for the strike/spare).
	 * 
	 * @param frame
	 *           the number of the frame.
	 * @return the score of the frame.
	 * @throws OutOfRangeException
	 */
	public int getScoreOf(int frame) throws OutOfRangeException;

	/**
	 * Gets the number of pins knocked down in the frame.
	 * 
	 * @param frame
	 *           the number of the frame.
	 * @return an array with the number of pins per shot.
	 * @throws OutOfRangeException
	 */
	public int[] getShotsOf(int frame) throws OutOfRangeException;

	/**
	 * Gets the total number of spares.
	 * 
	 * @return the number of spares.
	 */
	public int getSpares();

	/**
	 * Gets the total number of strikes.
	 * 
	 * @return the number of strikes.
	 */
	public int getStrikes();

	/**
	 * Gets the sum of the two shots in the frame.
	 * 
	 * @param frame
	 *           the number of frame.
	 * @return the sum of the pins knocked down in the frame.
	 * @throws OutOfRangeException
	 */
	public int getSumOf(int frame) throws OutOfRangeException;

	/**
	 * Says if the player gets two consecutive strikes on the first two shots of
	 * the last frame.
	 * 
	 * @return true for a Barney Rubble.
	 */
	public boolean isDoubleOnLastFrame();

	/**
	 * Says if the player does not have knocked down all the pins in the frame.
	 * 
	 * @param frame
	 *           the number of the frame.
	 * @return true for an open frame.
	 * @throws OutOfRangeException
	 */
	public boolean isOpen(int frame) throws OutOfRangeException;

	/**
	 * Says if the player gets a spare in the frame.
	 * 
	 * @param frame
	 *           the number of the frame.
	 * @return true for a spare.
	 * @throws OutOfRangeException
	 */
	public boolean isSpare(int frame) throws OutOfRangeException;

	/**
	 * Says if the player gets a spare on the first two shots of the last frame,
	 * followed by a strike.
	 * 
	 * @return true for a spare followed by a strike on the last frame.
	 */
	public boolean isSpareAndStrikeOnLastFrame();

	/**
	 * Says whether the player gets a spare on the first two shots of the last
	 * frame. This event imply an extra shot.
	 * 
	 * @return true for a spare.
	 */
	public boolean isSpareOnLastFrame();

	/**
	 * Says if the player gets a strike on the frame.
	 * 
	 * @param frame
	 *           the number of the frame.
	 * @return true for a strike.
	 * @throws OutOfRangeException
	 */
	public boolean isStrike(int frame) throws OutOfRangeException;

	/**
	 * Says whether the player gets a strike on the first shot of the last frame,
	 * followed by a spare.
	 * 
	 * @return true for a strike followed by a spare.
	 */
	public boolean isStrikeAndSpareOnLastFrame();

	/**
	 * Says whether the player gets a strike on the first shot of the last frame.
	 * 
	 * @return true for a strike on the first shot.
	 */
	public boolean isStrikeOnLastFrame();

	/**
	 * Says whether the player gets a triple strike on all shots of the last
	 * frame.
	 * 
	 * @return true for a Turkey.
	 */
	public boolean isTripleOnLastFrame();

	/**
	 * Serialize the scoreboard in a string.
	 * 
	 * @return the serialized scoreboard
	 */
	public String serializeScoreBoard();

	/**
	 * Assigns the number of pins knocked down.
	 * 
	 * @param frame
	 *           the number of the frame.
	 * @param shot
	 *           the number of the shot.
	 * @param pins
	 *           the number of pins knocked down.
	 * @throws ScoreBoardException
	 */
	public void setPins(int frame, int shot, int pins) throws ScoreBoardException;

}