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
	 * @param players
	 * the players playing this round
	 */
	public void newRound(List<Player> players);

	/**
	 * This method is invoked when the shoe is shuffled.
	 */
	public void shoeShuffled();

	/**
	 * This method is invoked when the dealer is dealt its up card.
	 * 
	 * @param up_card
	 * the up card of the dealer
	 */
	public void dealerDealt(Card up_card);

	/**
	 * This method is invoked when the dealer reveals its down card and does not
	 * receive a blackjack.
	 * 
	 * @param down_card
	 * the revealed down card of the dealer
	 * @param dealer_hand
	 * the dealer hand, including both its up and down card
	 */
	public void dealerDealt(Card down_card, Hand dealer_hand);

	/**
	 * This method is invoked when the dealer reveals its down card and receives
	 * a blackjack.
	 * 
	 * @param dealer_hand
	 * the dealer hand that is a blackjack
	 */
	public void dealerBlackjack(Hand dealer_hand);

	/**
	 * This method is invoked when the dealer hits and is drawn a new card.
	 * 
	 * @param dealt_card
	 * the new card the dealer draws
	 * @param new_hand
	 * the dealer hand including its drawn card
	 */
	public void dealerDraws(Card dealt_card, Hand new_hand);

	/**
	 * This method is invoked when the dealer stands.
	 * 
	 * @param dealer_hand
	 * the hand the dealer stood with
	 */
	public void dealerStands(Hand dealer_hand);

	/**
	 * This method is invoked when the dealer busts.
	 * 
	 * @param dealer_hand
	 * the hand the dealer busted with
	 */
	public void dealerBusts(Hand dealer_hand);

	/**
	 * This method is invoked when a player joins a table.
	 * 
	 * @param joined_player
	 * the player that has joined the table
	 */
	public void playerJoins(Player joined_player);

	/**
	 * This method is invoked when a player leaves the table.
	 * 
	 * @param left_player
	 * the player that has left the table
	 */
	public void playerLeaves(Player left_player);

	/**
	 * This method is invoked when a player makes a bet.
	 * 
	 * @param betting_player
	 * the player that has made a bet
	 * @param bet_amount
	 * the amount the player bet
	 * @param bankroll
	 * the bankroll of the player, which includes the amount bet
	 */
	public void playerBets(Player betting_player, int bet_amount, int bankroll);

	/**
	 * This method is invoked when a player makes his insurance bet.
	 * 
	 * @param betting_player
	 * the player that has made an insurance bet
	 * @param bet_amount
	 * the amount the player insures
	 * @param bankroll
	 * the bankroll of the player, which includes the amount bet
	 */
	public void playerInsures(Player betting_player, int bet_amount, int bankroll);

	/**
	 * This method is invoked when a player is dealt a new hand.
	 * 
	 * @param dealt_player
	 * the player that was dealt a hand
	 * @param player_hand
	 * the hand dealt to the player
	 */
	public void playerDealt(Player dealt_player, PlayerHand player_hand);

	/**
	 * This method is invoked when a player hits and is drawn a new card.
	 * 
	 * @param dealt_player
	 * the player that draws a card
	 * @param dealt_card
	 * the new card the player draws
	 * @param new_hand
	 * the player hand including the drawn card
	 */
	public void playerDraws(Player dealt_player, Card dealt_card,
			PlayerHand new_hand);

	/**
	 * This method is invoked when a player stands.
	 * 
	 * @param dealt_player
	 * the player that stands
	 * @param player_hand
	 * the player hand stood with
	 */
	public void playerStands(Player dealt_player, PlayerHand player_hand);

	/**
	 * This method is invoked when a player busts.
	 * 
	 * @param dealt_player
	 * the player that busts
	 * @param player_hand
	 * the player hand busted with
	 * @param amount_lost
	 * the amount the player lost
	 * @param new_bankroll
	 * the new bankroll of the player, after subtracting the loss
	 */
	public void playerBusts(Player dealt_player, PlayerHand player_hand,
			int amount_lost, int new_bankroll);

	/**
	 * This method is invoked when a player splits the hand.
	 * 
	 * @param dealt_player
	 * the player that splits
	 * @param player_hand
	 * the player hand that was split
	 */
	public void playerSplits(Player dealt_player, PlayerHand player_hand);

	/**
	 * This method is invoked when a player doubles down and is drawn a final
	 * card.
	 * 
	 * @param dealt_player
	 * the player that doubles down
	 * @param dealt_card
	 * the final card the player draws
	 * @param new_hand
	 * the player hand including the drawn card
	 */
	public void playerDoublesDown(Player dealt_player, Card dealt_card,
			PlayerHand new_hand);

	/**
	 * This method is invoked when a player wins, but does not get blackjack.
	 * 
	 * @param dealt_player
	 * the player that won
	 * @param player_hand
	 * the hand the player won on
	 * @param amount_won
	 * the amount the player won
	 * @param new_bankroll
	 * the new bankroll of the player, after adding the winning
	 */
	public void playerWins(Player dealt_player, PlayerHand player_hand, 
			int amount_won, int new_bankroll);

	/**
	 * This method is invoked when a player loses.
	 * 
	 * @param dealt_player
	 * the player that lost
	 * @param player_hand
	 * the hand the player lost on
	 * @param amount_lost
	 * the amount the player lost
	 * @param new_bankroll
	 * the new bankroll of the player, after subtracting the loss
	 */
	public void playerLoses(Player dealt_player, PlayerHand player_hand, 
			int amount_lost, int new_bankroll);

	/**
	 * This method is invoked when a player gets a blackjack.
	 * 
	 * @param dealt_player
	 * the player that got a blackjack
	 * @param player_hand
	 * the hand the player received blackjack on
	 * @param amount_won
	 * the amount the player won on blackjack
	 * @param new_bankroll
	 * the new bankroll of the player, after adding the winning
	 */
	public void playerBlackjack(Player dealt_player, PlayerHand player_hand, 
			int amount_won, int new_bankroll);

	/**
	 * This method is invoked when a player ties the dealer, or pushes.
	 * 
	 * @param dealt_player
	 * the player that tied the dealer
	 * @param player_hand
	 * the hand the player pushed on
	 * @param held_bankroll
	 * the unchanged bankroll of the player
	 */
	public void playerPush(Player dealt_player, PlayerHand player_hand, 
			int held_bankroll);
	
	/**
	 * This method is invoked when a player wins his insurance bet.
	 * 
	 * @param dealt_player
	 * the player that won insurance
	 * @param amount_won
	 * the amount the player won
	 * @param new_bankroll
	 * the new bankroll of the player, after adding the winning
	 */
	public void playerWinsInsurance(Player dealt_player, int amount_won, int new_bankroll);
	
	/**
	 * This method is invoked when a player loses his insurance bet.
	 * 
	 * @param dealt_player
	 * the player that lost insurance
	 * @param amount_lost
	 * the amount the player lost
	 * @param new_bankroll
	 * the new bankroll of the player, after subtracting the loss
	 */
	public void playerLosesInsurance(Player dealt_player, int amount_lost, int new_bankroll);
}
