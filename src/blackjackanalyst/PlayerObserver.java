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
 * An interface that allows observation of a player.
 * 
 * @author Michael Parker
 */
public interface PlayerObserver {
	/**
	 * This method is invoked when the player joins a table.
	 * 
	 * @param t
	 * the table the player has joined
	 */
	public void playerJoins(Table t);

	/**
	 * This method is invoked when the player leaves the table it had previously
	 * joined.
	 * 
	 * @param t
	 * the table the player has left
	 */
	public void playerLeaves(Table t);

	/**
	 * This method is invoked when the player makes a bet.
	 * 
	 * @param bet_amount
	 * the amount the player bet
	 * @param bankroll
	 * the bankroll of the player, which includes the amount bet
	 */
	public void playerBets(int bet_amount, int bankroll);
	
	/**
	 * This method is invoked when the player makes his insurance bet.
	 * 
	 * @param bet_amount
	 * the amount the player insures
	 * @param bankroll
	 * the bankroll of the player, which includes the amount bet
	 */
	public void playerInsures(int bet_amount, int bankroll);

	/**
	 * This method is invoked when the player is dealt a new hand.
	 * 
	 * @param player_hand
	 * the hand dealt to the player
	 */
	public void playerDealt(PlayerHand player_hand);

	/**
	 * This method is invoked when the player hits and is drawn a new card.
	 * 
	 * @param dealt_card
	 * the new card the player draws
	 * @param new_hand
	 * the player hand including the drawn card
	 */
	public void playerDraws(Card dealt_card, PlayerHand new_hand);

	/**
	 * This method is invoked when the player stands.
	 * 
	 * @param player_hand
	 * the player hand stood with
	 */
	public void playerStands(PlayerHand player_hand);

	/**
	 * This method is invoked when the player busts.
	 * 
	 * @param player_hand
	 * the player hand busted with
	 * @param amount_lost
	 * the amount the player lost
	 * @param new_bankroll
	 * the new bankroll of the player, after subtracting the loss
	 */
	public void playerBusts(PlayerHand player_hand, int amount_lost,
			int new_bankroll);

	/**
	 * This method is invoked when the player splits the hand.
	 * 
	 * @param player_hand
	 * the player hand that was split
	 */
	public void playerSplits(PlayerHand player_hand);

	/**
	 * This method is invoked when the player doubles down and is drawn a final
	 * card.
	 * 
	 * @param dealt_card
	 * the final card the player draws
	 * @param new_hand
	 * the player hand including the drawn card
	 */
	public void playerDoublesDown(Card dealt_card, PlayerHand new_hand);

	/**
	 * This method is invoked when the observed player wins, but does not get
	 * blackjack.
	 * 
	 * @param player_hand
	 * the hand the player won on
	 * @param amount_won
	 * the amount the player won
	 * @param new_bankroll
	 * the new bankroll of the player, after adding the winning
	 */
	public void playerWins(PlayerHand player_hand, int amount_won,
			int new_bankroll);

	/**
	 * This method is invoked when the observed player loses.
	 * 
	 * @param player_hand
	 * the hand the player lost on
	 * @param amount_lost
	 * the amount the player lost
	 * @param new_bankroll
	 * the new bankroll of the player, after subtracting the loss
	 */
	public void playerLoses(PlayerHand player_hand, int amount_lost,
			int new_bankroll);

	/**
	 * This method is invoked when the observed player gets a blackjack.
	 * 
	 * @param player_hand
	 * the hand the player received blackjack on
	 * @param amount_won
	 * the amount the player won on blackjack
	 * @param new_bankroll
	 * the new bankroll of the player, after adding the winning
	 */
	public void playerBlackjack(PlayerHand player_hand, int amount_won,
			int new_bankroll);

	/**
	 * This method is invoked when the observed player ties the dealer, or
	 * pushes.
	 * 
	 * @param player_hand
	 * the hand the player pushed on
	 * @param held_bankroll
	 * the unchanged bankroll of the player
	 */
	public void playerPush(PlayerHand player_hand, int held_bankroll);

	/**
	 * This method is invoked when the observed player wins his insurance bet.
	 * 
	 * @param amount_won
	 * the amount the player won
	 * @param new_bankroll
	 * the new bankroll of the player, after adding the winning
	 */
	public void playerWinsInsurance(int amount_won, int new_bankroll);

	/**
	 * This method is invoked when the observed player loses his insurance bet.
	 * 
	 * @param amount_lost
	 * the amount the player lost
	 * @param new_bankroll
	 * the new bankroll of the player, after subtracting the loss
	 */
	public void playerLosesInsurance(int amount_lost, int new_bankroll);
}
