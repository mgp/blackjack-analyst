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

package blackjackanalyst.observer;

import java.util.List;

import blackjackanalyst.Card;
import blackjackanalyst.Hand;
import blackjackanalyst.Player;
import blackjackanalyst.PlayerHand;
import blackjackanalyst.TableObserver;

/**
 * A table observer that records the number of wins and losses players have had
 * at a given table.
 * 
 * @author Michael Parker
 */
public class WinLossTableObserver implements TableObserver {
	protected int rounds_played;

	protected int num_wins;
	protected int num_losses;
	protected int num_blackjacks;
	protected int num_pushes;
	protected int num_insurance_wins;
	protected int num_insurance_losses;

	protected int net_gain;

	/**
	 * Creates a new table observer that gathers statistics.
	 */
	public WinLossTableObserver() {
		reset();
	}

	/**
	 * Resets all statistics recorded by this observer.
	 */
	public void reset() {
		rounds_played = 0;
		
		num_wins = 0;
		num_losses = 0;
		num_blackjacks = 0;
		num_pushes = 0;
		num_insurance_wins = 0;
		num_insurance_losses = 0;

		net_gain = 0;
	}

	public void newRound(List<Player> players) {
		++rounds_played;
	}

	public void shoeShuffled() {
	}

	public void dealerDealt(Card up_card) {
	}

	public void dealerDealt(Card down_card, Hand dealer_hand) {
	}

	public void dealerBlackjack(Hand dealer_hand) {
	}

	public void dealerDraws(Card dealt_card, Hand new_hand) {
	}

	public void dealerStands(Hand dealer_hand) {
	}

	public void dealerBusts(Hand dealer_hand) {
	}

	public void playerJoins(Player joined_player) {
	}

	public void playerLeaves(Player left_player) {
	}

	public void playerBets(Player betting_player, int bet_amount, int bankroll) {
	}
	
	public void playerInsures(Player betting_player, int bet_amount, int bankroll) {
	}

	public void playerDealt(Player dealt_player, PlayerHand player_hand) {
	}

	public void playerDraws(Player dealt_player, Card dealt_card,
			PlayerHand new_hand) {
	}

	public void playerStands(Player dealt_player, PlayerHand player_hand) {
	}

	public void playerBusts(Player dealt_player, PlayerHand player_hand,
			int amount_lost, int new_bankroll) {
		++num_losses;
		net_gain -= amount_lost;
	}

	public void playerSplits(Player dealt_player, PlayerHand player_hand) {
	}

	public void playerDoublesDown(Player dealt_player, Card dealt_card,
			PlayerHand new_hand) {
	}

	public void playerWins(Player dealt_player, PlayerHand player_hand, int amount_won, 
			int new_bankroll) {
		++num_wins;
		net_gain += amount_won;
	}

	public void playerLoses(Player dealt_player, PlayerHand player_hand, int amount_lost,
			int new_bankroll) {
		++num_losses;
		net_gain -= amount_lost;
	}

	public void playerBlackjack(Player dealt_player, PlayerHand player_hand, int amount_won,
			int new_bankroll) {
		++num_blackjacks;
		net_gain += amount_won;
	}

	public void playerPush(Player dealt_player, PlayerHand player_hand, int held_bankroll) {
		++num_pushes;
	}
	
	public void playerWinsInsurance(Player dealt_player, int amount_won, int new_bankroll) {
		++num_insurance_wins;
		net_gain += amount_won;
	}
	
	public void playerLosesInsurance(Player dealt_player, int amount_lost, int new_bankroll) {
		++num_insurance_losses;
		net_gain -= amount_lost;
	}

	/**
	 * Returns the number of times a player has won, not including blackjacks.
	 * 
	 * @return the number of player wins
	 */
	public int getNumWins() {
		return num_wins;
	}

	/**
	 * Returns the number of times a player has lost, meaning the house has won.
	 * 
	 * @return the number of player losses
	 */
	public int getNumLosses() {
		return num_losses;
	}

	/**
	 * Returns the number of times a player has received a blackjack.
	 * 
	 * @return the number of player blackjacks
	 */
	public int getNumBlackjacks() {
		return num_blackjacks;
	}

	/**
	 * Returns the number of times a player pushed.
	 * 
	 * @return the number of player pushes.
	 */
	public int getNumPushes() {
		return num_pushes;
	}

	/**
	 * Returns the number of rounds of blackjack that have been played.
	 * 
	 * @return the number of blackjack rounds
	 */
	public int getNumRounds() {
		return rounds_played;
	}

	/**
	 * Returns the combined net winnings of the players; a negative number
	 * returned indicates an overall player loss and a winning house.
	 * 
	 * @return the net winnings of the players
	 */
	public int getNetGain() {
		return net_gain;
	}

	public String toString() {
		StringBuffer sbuf = new StringBuffer(512);
		sbuf.append("W: ").append(num_wins);
		sbuf.append(", L: ").append(num_losses);
		sbuf.append(", BJ: ").append(num_blackjacks);
		sbuf.append(", P: ").append(num_pushes);
		sbuf.append(", IW: ").append(num_insurance_wins);
		sbuf.append(", IL: ").append(num_insurance_losses);
		sbuf.append(", net = ").append(net_gain);
		return sbuf.toString();
	}
}
