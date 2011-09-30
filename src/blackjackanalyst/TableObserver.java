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

import java.util.List;

/**
 * An interface that allows neutral observation of the table.
 * 
 * @author Michael Parker
 */
public interface TableObserver {
	/**
	 * This method is invoked before each new round of blackjack.
	 * 
	 * @param players the players playing this round
	 */
	public void newRound(List<Player> players);

	/**
	 * This method is invoked when the shoe is shuffled.
	 */
	public void shoeShuffled();

	/**
	 * This method is invoked when the dealer is dealt its up card.
	 * 
	 * @param upCard the up card of the dealer
	 */
	public void dealerDealt(Card upCard);

	/**
	 * This method is invoked when the dealer reveals its down card and does not
	 * receive a blackjack.
	 * 
	 * @param downCard the revealed down card of the dealer
	 * @param hand the dealer hand, including both its up and down card
	 */
	public void dealerDealt(Card downCard, Hand hand);

	/**
	 * This method is invoked when the dealer reveals its down card and receives a
	 * blackjack.
	 * 
	 * @param hand the dealer hand that is a blackjack
	 */
	public void dealerBlackjack(Hand hand);

	/**
	 * This method is invoked when the dealer hits and is drawn a new card.
	 * 
	 * @param card the new card the dealer draws
	 * @param hand the dealer hand including its drawn card
	 */
	public void dealerDraws(Card card, Hand hand);

	/**
	 * This method is invoked when the dealer stands.
	 * 
	 * @param hand the hand the dealer stood with
	 */
	public void dealerStands(Hand hand);

	/**
	 * This method is invoked when the dealer busts.
	 * 
	 * @param hand the hand the dealer busted with
	 */
	public void dealerBusts(Hand hand);

	/**
	 * This method is invoked when a player joins a table.
	 * 
	 * @param player the player that has joined the table
	 */
	public void playerJoins(Player player);

	/**
	 * This method is invoked when a player leaves the table.
	 * 
	 * @param player the player that has left the table
	 */
	public void playerLeaves(Player player);

	/**
	 * This method is invoked when a player makes a bet.
	 * 
	 * @param player the player that has made a bet
	 * @param betAmount the amount the player bet
	 * @param bankroll the bankroll of the player, which includes the amount bet
	 */
	public void playerBets(Player player, int betAmount, int bankroll);

	/**
	 * This method is invoked when a player makes his insurance bet.
	 * 
	 * @param player the player that has made an insurance bet
	 * @param betAmount the amount the player insures
	 * @param bankroll the bankroll of the player, which includes the amount bet
	 */
	public void playerInsures(Player player, int betAmount, int bankroll);

	/**
	 * This method is invoked when a player is dealt a new hand.
	 * 
	 * @param player the player that was dealt a hand
	 * @param hand the hand dealt to the player
	 */
	public void playerDealt(Player player, PlayerHand hand);

	/**
	 * This method is invoked when a player hits and is drawn a new card.
	 * 
	 * @param player the player that draws a card
	 * @param card the new card the player draws
	 * @param hand the player hand including the drawn card
	 */
	public void playerDraws(Player player, Card card, PlayerHand hand);

	/**
	 * This method is invoked when a player stands.
	 * 
	 * @param player the player that stands
	 * @param hand the player hand stood with
	 */
	public void playerStands(Player player, PlayerHand hand);

	/**
	 * This method is invoked when a player busts.
	 * 
	 * @param player the player that busts
	 * @param hand the player hand busted with
	 * @param amountLost the amount the player lost
	 * @param newBankroll the new bankroll of the player, after subtracting the
	 *          loss
	 */
	public void playerBusts(Player player, PlayerHand hand, int amountLost,
	    int newBankroll);

	/**
	 * This method is invoked when a player splits the hand.
	 * 
	 * @param player the player that splits
	 * @param hand the player hand that was split
	 */
	public void playerSplits(Player player, PlayerHand hand);

	/**
	 * This method is invoked when a player doubles down and is drawn a final
	 * card.
	 * 
	 * @param player the player that doubles down
	 * @param card the final card the player draws
	 * @param hand the player hand including the drawn card
	 */
	public void playerDoublesDown(Player player, Card card, PlayerHand hand);

	/**
	 * This method is invoked when a player wins, but does not get blackjack.
	 * 
	 * @param player the player that won
	 * @param hand the hand the player won on
	 * @param amountWon the amount the player won
	 * @param newBankroll the new bankroll of the player, after adding the winning
	 */
	public void playerWins(Player player, PlayerHand hand, int amountWon,
	    int newBankroll);

	/**
	 * This method is invoked when a player loses.
	 * 
	 * @param player the player that lost
	 * @param hand the hand the player lost on
	 * @param amountLost the amount the player lost
	 * @param newBankroll the new bankroll of the player, after subtracting the
	 *          loss
	 */
	public void playerLoses(Player player, PlayerHand hand, int amountLost,
	    int newBankroll);

	/**
	 * This method is invoked when a player gets a blackjack.
	 * 
	 * @param player the player that got a blackjack
	 * @param hand the hand the player received blackjack on
	 * @param amountWon the amount the player won on blackjack
	 * @param newBankroll the new bankroll of the player, after adding the winning
	 */
	public void playerBlackjack(Player player, PlayerHand hand, int amountWon,
	    int newBankroll);

	/**
	 * This method is invoked when a player ties the dealer, or pushes.
	 * 
	 * @param player the player that tied the dealer
	 * @param hand the hand the player pushed on
	 * @param bankroll the unchanged bankroll of the player
	 */
	public void playerPush(Player player, PlayerHand hand, int bankroll);

	/**
	 * This method is invoked when a player wins his insurance bet.
	 * 
	 * @param player the player that won insurance
	 * @param amountWon the amount the player won
	 * @param newBankroll the new bankroll of the player, after adding the winning
	 */
	public void playerWinsInsurance(Player player, int amountWon, int newBankroll);

	/**
	 * This method is invoked when a player loses his insurance bet.
	 * 
	 * @param player the player that lost insurance
	 * @param amountLost the amount the player lost
	 * @param newBankroll the new bankroll of the player, after subtracting the
	 *          loss
	 */
	public void playerLosesInsurance(Player player, int amountLost,
	    int newBankroll);
}
