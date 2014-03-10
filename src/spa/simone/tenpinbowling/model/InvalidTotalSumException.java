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
 * This exception is thrown when the total number of pins knocked down in a
 * frame exceeds 10 (except the last frame).
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}
 */
public class InvalidTotalSumException extends ScoreBoardException {

	private static final long serialVersionUID = 3083281133749981354L;

	/**
	 * Constructs the exception passing an error message that will be shown in
	 * the stack trace.
	 * 
	 * @param message
	 *           the error message.
	 */
	public InvalidTotalSumException(String message) {
		super(message);
	}

}