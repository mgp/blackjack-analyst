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

package blackjackanalyst.observer;

import blackjackanalyst.Card;
import blackjackanalyst.PlayerHand;
import blackjackanalyst.PlayerObserver;
import blackjackanalyst.Table;

/**
 * A player observer that records the number of wins and losses a player has
 * had.
 * 
 * @author Michael Parker
 */
public class WinLossPlayerObserver implements PlayerObserver {
	protected int curr_streak;
	protected boolean winning_streak;

	protected int num_wins;
	protected int num_losses;
	protected int num_blackjacks;
	protected int num_pushes;
	protected int num_insurance_wins;
	protected int num_insurance_losses;

	protected int win_streak;
	protected int loss_streak;
	
	protected int net_gain;

	/**
	 * Creates a new player observer that gathers statistics.
	 */
	public WinLossPlayerObserver() {
		reset();
	}

	/**
	 * Resets the statistics recorded by this observer.
	 */
	public void reset() {
		curr_streak = 0;
		winning_streak = false;

		num_wins = 0;
		num_losses = 0;
		num_blackjacks = 0;
		num_pushes = 0;

		win_streak = 0;
		loss_streak = 0;
		
		net_gain = 0;
	}

	public void playerJoins(Table t) {
	}

	public void playerLeaves(Table t) {
	}

	public void playerBets(int bet_amount, int bankroll) {
	}
	
	public void playerInsures(int bet_amount, int bankroll) {
	}

	public void playerDealt(PlayerHand player_hand) {
	}

	public void playerDraws(Card dealt_card, PlayerHand new_hand) {
	}

	public void playerStands(PlayerHand player_hand) {
	}

	public void playerBusts(PlayerHand player_hand, int amount_lost, int new_bankroll) {
		++num_losses;
		net_gain -= amount_lost;
		updateLossStreak();
	}

	public void playerSplits(PlayerHand player_hand) {
	}

	public void playerDoublesDown(Card dealt_card, PlayerHand new_hand) {
	}

	public void playerWins(PlayerHand player_hand, int amount_won, int new_bankroll) {
		++num_wins;
		net_gain += amount_won;
		updateWinStreak();
	}

	public void playerLoses(PlayerHand player_hand, int amount_lost, int new_bankroll) {
		++num_losses;
		net_gain -= amount_lost;
		updateLossStreak();
	}

	public void playerBlackjack(PlayerHand player_hand, int amount_won, int new_bankroll) {
		++num_blackjacks;
		net_gain += amount_won;
		updateWinStreak();
	}

	public void playerPush(PlayerHand player_hand, int held_bankroll) {
		++num_pushes;
		// push erases any streak
		curr_streak = 0;
	}
	
	public void playerWinsInsurance(int amount_won, int new_bankroll) {
		++num_insurance_wins;
		net_gain += amount_won;
	}
	
	public void playerLosesInsurance(int amount_lost, int new_bankroll) {
		++num_insurance_losses;
		net_gain -= amount_lost;
	}

	protected void updateWinStreak() {
		if (!winning_streak) {
			// erase losing streak, start winning streak
			curr_streak = 0;
			winning_streak = true;
		}
		if (++curr_streak > win_streak) {
			win_streak = curr_streak;
		}
	}

	protected void updateLossStreak() {
		if (winning_streak) {
			// erase winning streak, start losing streak
			curr_streak = 0;
			winning_streak = false;
		}
		if (++curr_streak > loss_streak) {
			loss_streak = curr_streak;
		}
	}

	/**
	 * Returns the number of times the player has won, not including blackjacks.
	 * 
	 * @return the number of player wins
	 */
	public int getNumWins() {
		return num_wins;
	}

	/**
	 * Returns the number of times the player has lost.
	 * 
	 * @return the number of player losses
	 */
	public int getNumLosses() {
		return num_losses;
	}

	/**
	 * Returns the number of times the player has received a blackjack.
	 * 
	 * @return the number of player blackjacks
	 */
	public int getNumBlackjacks() {
		return num_blackjacks;
	}

	/**
	 * Returns the number of times the player pushed.
	 * 
	 * @return the number of player pushes.
	 */
	public int getNumPushes() {
		return num_pushes;
	}

	/**
	 * Returns the longest win streak for this player, which may include
	 * blackjacks. A winning streak is broken any time the player pushes or
	 * loses.
	 * 
	 * @return the longest player win streak
	 */
	public int getWinStreak() {
		return win_streak;
	}

	/**
	 * Returns the longest loss streak for this player. A losing streak is
	 * broken any time the player pushes or wins.
	 * 
	 * @return the longest player loss streak
	 */
	public int getLossStreak() {
		return loss_streak;
	}
	
	/**
	 * Returns the combined net winnings of this player; a negative number
	 * returned indicates a winning house.
	 * 
	 * @return the net winnings of this player
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
