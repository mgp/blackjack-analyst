/*
 * Copyright 2005, 2006 Michael Parker (shadowmatter AT gmail DOT com).
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
 * An interface to be implemented by a user-defined player strategy. A player
 * strategy defines what action a player takes given a hand of cards.
 * 
 * @author Michael Parker
 */
public interface PlayerStrategy {
	/**
	 * The possible player actions, which are hit, stand, double down, and split.
	 * 
	 * @author Michael Parker
	 */
	public static enum PlayerStrategyAction {
		/**
		 * This is used when the player wants to hit.
		 */
		HIT,

		/**
		 * This is used when the player wants to stand.
		 */
		STAND,

		/**
		 * This is used when the player wants to double down.
		 */
		DOUBLE_DOWN,

		/**
		 * This is used when the player wants to split.
		 */
		SPLIT
	}

	/**
	 * Returns the proper action, given the current hand. If this method returns
	 * {@code null}, the caller determines what action is taken.
	 * 
	 * @param hand the hand of cards belonging to the player
	 * @param dealerCard the card shown by the dealer
	 * @return the proper action, which is either hit, stand, double down, or
	 *         split
	 */
	public PlayerStrategyAction getAction(PlayerHand hand, Card dealerCard);

	/**
	 * This informs the strategy that the shoe has been shuffled.
	 */
	public void shoeShuffled();

	/**
	 * This informs the strategy that the given card has been dealt to some
	 * player.
	 * 
	 * @param dealtCard the card that was dealt
	 */
	public void cardDealt(Card dealtCard);

	/**
	 * This informs the strategy that the player has now joined a table.
	 * 
	 * @param table the table the player has joined
	 */
	public void joinedTable(Table table);

	/**
	 * This informs the strategy that the player has now left the table it
	 * previously joined.
	 * 
	 * @param table the table the player has left
	 */
	public void leftTable(Table table);

	/**
	 * Notifies the player that a new betting round has started. The bet made by
	 * the player is returned, which must be greater than or equal to {@code 0}
	 * and less than or equal to {@code bankroll}.
	 * 
	 * @param bankroll the current bankroll of the player
	 * @return the bet made by the player
	 */
	public int getBet(int bankroll);

	/**
	 * Notifies the player that the dealer is showing an ace as his up card and is
	 * offering insurance. This method returns the amount the player wants to
	 * insure, up to half his original bet. If this method returns {@code 0}, no
	 * insurance is taken.
	 * 
	 * @param hand the hand of cards belonging to the player
	 * @param betAmount the amount bet on the player hand
	 * @return the amount of money the player wants to insure
	 */
	public int getInsuranceBet(PlayerHand hand, int betAmount);
}
