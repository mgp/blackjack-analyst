/*
 * Copyright Michael Parker (michael.g.parker@gmail.com).
 * 
 * This file is part of Blackjack Analyst.
 * 
 * Blackjack Analyst is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * Blackjack Analyst is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Blackjack Analyst; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package blackjackanalyst;

/**
 * An interface to be implemented by a user-defined dealer strategy. A dealer
 * strategy defines what action the house takes given a hand of cards.
 * 
 * @author Michael Parker
 */
public interface DealerStrategy {
	/**
	 * The possible dealer actions, which are simply hit and stand.
	 * 
	 * @author Michael Parker
	 */
	public static enum DealerStrategyAction {
		/**
		 * This is used when the dealer wants to hit.
		 */
		HIT,

		/**
		 * This is used when the dealer wants to stand.
		 */
		STAND
	}

	/**
	 * Returns the proper action, given the current hand. If this method returns
	 * <code>null</code>, the caller determines what action is taken.
	 * 
	 * @param dealer_hand
	 * the current hand of cards
	 * @return the proper action, which is either hit or stand
	 */
	public DealerStrategyAction getAction(Hand dealer_hand);
}
