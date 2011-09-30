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
 * An interface that allows observation of a player.
 * 
 * @author Michael Parker
 */
public interface PlayerObserver {
	/**
	 * This method is invoked when the player joins a table.
	 * 
	 * @param table the table the player has joined
	 */
	public void playerJoins(Table table);

	/**
	 * This method is invoked when the player leaves the table it had previously
	 * joined.
	 * 
	 * @param table the table the player has left
	 */
	public void playerLeaves(Table table);

	/**
	 * This method is invoked when the player makes a bet.
	 * 
	 * @param betAmount the amount the player bet
	 * @param bankroll the bankroll of the player, which includes the amount bet
	 */
	public void playerBets(int betAmount, int bankroll);

	/**
	 * This method is invoked when the player makes his insurance bet.
	 * 
	 * @param betAmount the amount the player insures
	 * @param bankroll the bankroll of the player, which includes the amount bet
	 */
	public void playerInsures(int betAmount, int bankroll);

	/**
	 * This method is invoked when the player is dealt a new hand.
	 * 
	 * @param hand the hand dealt to the player
	 */
	public void playerDealt(PlayerHand hand);

	/**
	 * This method is invoked when the player hits and is drawn a new card.
	 * 
	 * @param card the new card the player draws
	 * @param hand the player hand including the drawn card
	 */
	public void playerDraws(Card card, PlayerHand hand);

	/**
	 * This method is invoked when the player stands.
	 * 
	 * @param hand the player hand stood with
	 */
	public void playerStands(PlayerHand hand);

	/**
	 * This method is invoked when the player busts.
	 * 
	 * @param hand the player hand busted with
	 * @param amountLost the amount the player lost
	 * @param newBankroll the new bankroll of the player, after subtracting the
	 *          loss
	 */
	public void playerBusts(PlayerHand hand, int amountLost, int newBankroll);

	/**
	 * This method is invoked when the player splits the hand.
	 * 
	 * @param hand the player hand that was split
	 */
	public void playerSplits(PlayerHand hand);

	/**
	 * This method is invoked when the player doubles down and is drawn a final
	 * card.
	 * 
	 * @param card the final card the player draws
	 * @param hand the player hand including the drawn card
	 */
	public void playerDoublesDown(Card card, PlayerHand hand);

	/**
	 * This method is invoked when the observed player wins, but does not get
	 * blackjack.
	 * 
	 * @param hand the hand the player won on
	 * @param amountWon the amount the player won
	 * @param newBankroll the new bankroll of the player, after adding the winning
	 */
	public void playerWins(PlayerHand hand, int amountWon, int newBankroll);

	/**
	 * This method is invoked when the observed player loses.
	 * 
	 * @param hand the hand the player lost on
	 * @param amountLost the amount the player lost
	 * @param newBankroll the new bankroll of the player, after subtracting the
	 *          loss
	 */
	public void playerLoses(PlayerHand hand, int amountLost, int newBankroll);

	/**
	 * This method is invoked when the observed player gets a blackjack.
	 * 
	 * @param hand the hand the player received blackjack on
	 * @param amountWon the amount the player won on blackjack
	 * @param newBankroll the new bankroll of the player, after adding the winning
	 */
	public void playerBlackjack(PlayerHand hand, int amountWon, int newBankroll);

	/**
	 * This method is invoked when the observed player ties the dealer, or pushes.
	 * 
	 * @param hand the hand the player pushed on
	 * @param heldBankroll the unchanged bankroll of the player
	 */
	public void playerPush(PlayerHand hand, int heldBankroll);

	/**
	 * This method is invoked when the observed player wins his insurance bet.
	 * 
	 * @param amountWon the amount the player won
	 * @param newBankroll the new bankroll of the player, after adding the winning
	 */
	public void playerWinsInsurance(int amountWon, int newBankroll);

	/**
	 * This method is invoked when the observed player loses his insurance bet.
	 * 
	 * @param amountLost the amount the player lost
	 * @param newBankroll the new bankroll of the player, after subtracting the
	 *          loss
	 */
	public void playerLosesInsurance(int amountLost, int newBankroll);
}
