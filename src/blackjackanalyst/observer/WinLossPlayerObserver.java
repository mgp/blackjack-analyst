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

import blackjackanalyst.PlayerHand;

/**
 * A player observer that records the number of wins and losses a player has
 * had.
 * 
 * @author Michael Parker
 */
public class WinLossPlayerObserver extends AbstractPlayerObserver {
	protected int streakLength;
	protected boolean onWinningStreak;

	protected int numWins;
	protected int numLosses;
	protected int numBlackjacks;
	protected int numPushes;
	protected int numInsuranceWins;
	protected int numInsuranceLosses;

	protected int longestWinStreak;
	protected int longestLosingStreak;

	protected int netGain;

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
		streakLength = 0;
		onWinningStreak = false;

		numWins = 0;
		numLosses = 0;
		numBlackjacks = 0;
		numPushes = 0;

		longestWinStreak = 0;
		longestLosingStreak = 0;

		netGain = 0;
	}

	public void playerBusts(PlayerHand hand, int amountLost, int newBankroll) {
		++numLosses;
		netGain -= amountLost;
		updateLossStreak();
	}

	public void playerWins(PlayerHand hand, int amountWon, int newBankroll) {
		++numWins;
		netGain += amountWon;
		updateWinStreak();
	}

	public void playerLoses(PlayerHand hand, int amountLost, int newBankroll) {
		++numLosses;
		netGain -= amountLost;
		updateLossStreak();
	}

	public void playerBlackjack(PlayerHand hand, int amountWon, int newBankroll) {
		++numBlackjacks;
		netGain += amountWon;
		updateWinStreak();
	}

	public void playerPush(PlayerHand hand, int heldBankroll) {
		++numPushes;
		// push erases any streak
		streakLength = 0;
	}

	public void playerWinsInsurance(int amountWon, int newBankroll) {
		++numInsuranceWins;
		netGain += amountWon;
	}

	public void playerLosesInsurance(int amountLost, int newBankroll) {
		++numInsuranceLosses;
		netGain -= amountLost;
	}

	protected void updateWinStreak() {
		if (!onWinningStreak) {
			// erase losing streak, start winning streak
			streakLength = 0;
			onWinningStreak = true;
		}
		if (++streakLength > longestWinStreak) {
			longestWinStreak = streakLength;
		}
	}

	protected void updateLossStreak() {
		if (onWinningStreak) {
			// erase winning streak, start losing streak
			streakLength = 0;
			onWinningStreak = false;
		}
		if (++streakLength > longestLosingStreak) {
			longestLosingStreak = streakLength;
		}
	}

	/**
	 * Returns the number of times the player has won, not including blackjacks.
	 * 
	 * @return the number of player wins
	 */
	public int getNumWins() {
		return numWins;
	}

	/**
	 * Returns the number of times the player has lost.
	 * 
	 * @return the number of player losses
	 */
	public int getNumLosses() {
		return numLosses;
	}

	/**
	 * Returns the number of times the player has received a blackjack.
	 * 
	 * @return the number of player blackjacks
	 */
	public int getNumBlackjacks() {
		return numBlackjacks;
	}

	/**
	 * Returns the number of times the player pushed.
	 * 
	 * @return the number of player pushes
	 */
	public int getNumPushes() {
		return numPushes;
	}

	/**
	 * Returns the longest win streak for this player, which may include
	 * blackjacks. A winning streak is broken any time the player pushes or loses.
	 * 
	 * @return the longest player win streak
	 */
	public int getWinStreak() {
		return longestWinStreak;
	}

	/**
	 * Returns the longest loss streak for this player. A losing streak is broken
	 * any time the player pushes or wins.
	 * 
	 * @return the longest player loss streak
	 */
	public int getLossStreak() {
		return longestLosingStreak;
	}

	/**
	 * Returns the combined net winnings of this player; a negative number
	 * returned indicates a winning house.
	 * 
	 * @return the net winnings of this player
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
