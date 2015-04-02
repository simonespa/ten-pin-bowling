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

/**
 * <p>
 * This class validate the user input.
 * </p>
 * <p>
 * It checks the frame, the shot and the pins numbers inserted by the user.
 * </p>
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}
 */
public class Validator {

	/**
	 * Checks that the frame number is valid.
	 * 
	 * @param frame
	 *           the frame number.
	 * @throws OutOfRangeException
	 */
	public static void checkFrame(int frame) throws OutOfRangeException {
		if (frame < 1 || frame > 10) {
			throw new OutOfRangeException(frame
					+ " is an invalid frame value. It must be in the range [1-10]");
		}
	}

	/**
	 * Checks that the shot number is valid.
	 * 
	 * @param frame
	 *           the frame number.
	 * @param shot
	 *           the shot number.
	 * @throws OutOfRangeException
	 */
	public static void checkShot(int frame, int shot) throws OutOfRangeException {
		if (shot < 1 || shot > 3) {
			throw new OutOfRangeException(
					shot
							+ " is an invalid shot value. It must be equals to 1 or 2 (to 3 if it is the last frame and the player gets a strike or a spare).");
		}
		if (frame >= 1 && frame <= 9 && shot == 3) {
			throw new OutOfRangeException(
					shot
							+ " is an invalid shot value for a non-final frame. Only the last one can have a third shot (if the player gets a strike or a spare).");
		}
	}

	/**
	 * Checks that the number of pins is valid.
	 * 
	 * @param frame
	 *           the frame number.
	 * @param shot
	 *           the shot number.
	 * @param pins
	 *           the number of pins knocked down.
	 * @param shots
	 *           an array containing the number of pins
	 * @throws OutOfRangeException
	 * @throws InvalidTotalSumException
	 * @throws StrikeViolationException
	 */
	public static void checkPins(int frame, int shot, int pins, int[] shots)
			throws OutOfRangeException, InvalidTotalSumException, StrikeViolationException {
		// range validation
		if (pins < 0 || pins > 10) {
			throw new OutOfRangeException(pins
					+ " is not a valid value. It must be in the range [1-10]");
		}
		/*
		 * Domain validation
		 */
		if (frame >= 1 && frame <= 9) { // the first 9 frames
			// at the second shot
			if (shot == 2) {
				int index = getIndex(frame - 1, shot - 1) - 1;
				// if we got a strike the second shot must knock down 0 pins
				if (shots[index] == 10 && pins != 0) {
					throw new StrikeViolationException(
							pins
									+ " is not a valid value for the second shot. The player got a strike in the same frame. Use zero instead.");
				}
				// if we didn't get a strike at the first shot, the sum should
				// not exceed 10 (a spare)
				if (shots[index] + pins > 10) {
					throw new InvalidTotalSumException(
							pins
									+ " is not a valid value for the second shot. The sum for this frame exceed 10. The maximum value that can be set is "
									+ (10 - shots[index]));
				}
			}
		} else if (frame == 10) { // the last frame
			int index = getIndex(frame - 1, shot - 1);
			if (shot == 2) {
				// se al primo shot non c'è stato uno strike
				if (shots[index - 1] != 10 && (shots[index - 1] + pins > 10)) {
					// questo shot non può essere uno strike
					throw new InvalidTotalSumException(
							pins
									+ " is not a valid value for this shot. The sum for this frame exceed 10. The maximum value that can be set is "
									+ (10 - shots[index - 1]));
				}
			}
			if (shot == 3) {
				// non può esserci un terzo shot se non c'è stato uno strike o
				// uno spare
				if (shots[index - 2] != 10 && (shots[index - 2] + shots[index - 1] != 10)) {
					throw new OutOfRangeException(
							"A third shot is given only if the player got a strike or a spare in the last frame.");
				}
			}
		}
	}

}