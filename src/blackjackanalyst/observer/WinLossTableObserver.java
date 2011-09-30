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

import blackjackanalyst.Player;
import blackjackanalyst.PlayerHand;

/**
 * A table observer that records the number of wins and losses players have had
 * at a given table.
 * 
 * @author Michael Parker
 */
public class WinLossTableObserver extends AbstractTableObserver {
	protected int roundsPlayed;

	protected int numWins;
	protected int numLosses;
	protected int numBlackjacks;
	protected int numPushes;
	protected int numInsuranceWins;
	protected int numInsuranceLosses;

	protected int netGain;

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
		roundsPlayed = 0;

		numWins = 0;
		numLosses = 0;
		numBlackjacks = 0;
		numPushes = 0;
		numInsuranceWins = 0;
		numInsuranceLosses = 0;

		netGain = 0;
	}

	public void newRound(List<Player> players) {
		++roundsPlayed;
	}

	public void playerBusts(Player player, PlayerHand player_hand,
	    int amountLost, int newBankroll) {
		++numLosses;
		netGain -= amountLost;
	}

	public void playerWins(Player player, PlayerHand hand, int amountWon,
	    int newBankroll) {
		++numWins;
		netGain += amountWon;
	}

	public void playerLoses(Player player, PlayerHand hand, int amountLost,
	    int newBankroll) {
		++numLosses;
		netGain -= amountLost;
	}

	public void playerBlackjack(Player player, PlayerHand hand, int amountWon,
	    int newBankroll) {
		++numBlackjacks;
		netGain += amountWon;
	}

	public void playerPush(Player player, PlayerHand hand, int bankroll) {
		++numPushes;
	}

	public void playerWinsInsurance(Player player, int amountWon, int newBankroll) {
		++numInsuranceWins;
		netGain += amountWon;
	}

	public void playerLosesInsurance(Player player, int amountLost,
	    int newBankroll) {
		++numInsuranceLosses;
		netGain -= amountLost;
	}

	/**
	 * Returns the number of times a player has won, not including blackjacks.
	 * 
	 * @return the number of player wins
	 */
	public int getNumWins() {
		return numWins;
	}

	/**
	 * Returns the number of times a player has lost, meaning the house has won.
	 * 
	 * @return the number of player losses
	 */
	public int getNumLosses() {
		return numLosses;
	}

	/**
	 * Returns the number of times a player has received a blackjack.
	 * 
	 * @return the number of player blackjacks
	 */
	public int getNumBlackjacks() {
		return numBlackjacks;
	}

	/**
	 * Returns the number of times a player pushed.
	 * 
	 * @return the number of player pushes.
	 */
	public int getNumPushes() {
		return numPushes;
	}

	/**
	 * Returns the number of rounds of blackjack that have been played.
	 * 
	 * @return the number of blackjack rounds
	 */
	public int getNumRounds() {
		return roundsPlayed;
	}

	/**
	 * Returns the combined net winnings of the players; a negative number
	 * returned indicates an overall player loss and a winning house.
	 * 
	 * @return the net winnings of the players
	 */
	public int getNetGain() {
		return netGain;
	}

	public String toString() {
		StringBuilder sbuf = new StringBuilder(512);
		sbuf.append("W=").append(numWins);
		sbuf.append(", L=").append(numLosses);
		sbuf.append(", BJ=").append(numBlackjacks);
		sbuf.append(", P=").append(numPushes);
		sbuf.append(", IW=").append(numInsuranceWins);
		sbuf.append(", IL=").append(numInsuranceLosses);
		sbuf.append(", net=").append(netGain);
		return sbuf.toString();
	}
}
