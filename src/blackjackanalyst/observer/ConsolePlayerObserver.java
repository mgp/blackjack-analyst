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
import blackjackanalyst.Player;
import blackjackanalyst.PlayerHand;
import blackjackanalyst.PlayerObserver;
import blackjackanalyst.Table;

/**
 * An implementation of interface {@link PlayerObserver} that writes to the
 * console.
 * 
 * @author Michael Parker
 */
public class ConsolePlayerObserver implements PlayerObserver {
	protected final Player player;

	/**
	 * Creates a new player observer that writes to the console.
	 * 
	 * @param player the player to observe
	 */
	public ConsolePlayerObserver(Player player) {
		this.player = player;
	}

	/**
	 * Returns the player this object observes.
	 * 
	 * @return the observed player
	 */
	public Player getPlayer() {
		return player;
	}

	public void playerJoins(Table table) {
		System.out.println(player.getName() + " joins table " + table.getName());
	}

	public void playerLeaves(Table table) {
		System.out.println(player.getName() + " leaves table " + table.getName());
	}

	public void playerBets(int betAmount, int bankroll) {
		System.out.println(player.getName() + " bets " + betAmount);
	}

	public void playerInsures(int betAmount, int bankroll) {
		if (betAmount > 0) {
			System.out.println(player.getName() + " insures " + betAmount);
		} else {
			System.out.println(player.getName() + " declines insurance");
		}
	}

	public void playerDealt(PlayerHand hand) {
		System.out.println(player.getName() + " is dealt " + hand);
	}

	public void playerDraws(Card card, PlayerHand hand) {
		System.out.println(player.getName() + " hits, now has hand " + hand);
	}

	public void playerStands(PlayerHand hand) {
		System.out.println(player.getName() + " stands with hand " + hand);
	}

	public void playerBusts(PlayerHand hand, int amountLost, int newBankroll) {
		System.out.println(player.getName() + " busts with hand " + hand
		    + ", lost " + amountLost);
	}

	public void playerSplits(PlayerHand hand) {
		System.out.println(player.getName() + " splits with hand " + hand);
	}

	public void playerDoublesDown(Card card, PlayerHand hand) {
		System.out
		    .println(player.getName() + " doubles down, now has hand " + hand);
	}

	public void playerWins(PlayerHand hand, int amountWon, int newBankroll) {
		System.out.println(player.getName() + " beat dealer with "
		    + hand.getHighValidValue() + ", won " + amountWon);
	}

	public void playerLoses(PlayerHand hand, int amountLost, int newBankroll) {
		System.out.println(player.getName() + " lost to dealer with "
		    + hand.getHighValidValue() + ", lost " + amountLost);
	}

	public void playerBlackjack(PlayerHand hand, int amountWon, int newBankroll) {
		System.out.println(player.getName() + " received blackjack, won "
		    + amountWon);
	}

	public void playerPush(PlayerHand hand, int heldBankroll) {
		System.out.println(player.getName() + " pushed with dealer on "
		    + hand.getHighValidValue());
	}

	public void playerWinsInsurance(int amountWon, int newBankroll) {
		System.out
		    .println(player.getName() + " won " + amountWon + " on insurance");
	}

	public void playerLosesInsurance(int amountLost, int newBankroll) {
		System.out.println(player.getName() + " lost " + amountLost
		    + " on insurance");
	}
}
