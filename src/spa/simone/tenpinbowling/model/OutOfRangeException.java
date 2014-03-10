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
 * <p>
 * This exception is thrown when the frame/shot/pins number is out of range.
 * </p>
 * <p>
 * <ul>
 * 	<li>Frame: [1-10]</li>
 * 	<li>Shot: 1 or 2 (the last frame can have also the third shot)</li>
 * 	<li>Pins: [1-10]</li>
 * </ul>
 * </p>
 * 
 * @author Simone Spaccarotella {spa.simone@gmail.com}
 */
public class OutOfRangeException extends ScoreBoardException {

	private static final long serialVersionUID = 3296712693964949752L;

	/**
	 * Constructs the exception passing an error message that will be shown in
	 * the stack trace.
	 * 
	 * @param message
	 *           the error message.
	 */
	public OutOfRangeException(String message) {
		super(message);
	}

}