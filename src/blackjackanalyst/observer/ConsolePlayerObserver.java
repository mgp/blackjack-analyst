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
 * An implementation of interface <code>PlayerObserver</code> that writes to
 * the console.
 * 
 * @author Michael Parker
 */
public class ConsolePlayerObserver implements PlayerObserver {
	protected final Player p;

	/**
	 * Creates a new player observer that writes to the console.
	 * 
	 * @param _p
	 * the player to observe
	 */
	public ConsolePlayerObserver(Player _p) {
		p = _p;
	}

	/**
	 * Returns the player this object observes.
	 * 
	 * @return the observed player
	 */
	public Player getPlayer() {
		return p;
	}

	public void playerJoins(Table t) {
		System.out.println(p.getName() + " joins table " + t.getName());
	}

	public void playerLeaves(Table t) {
		System.out.println(p.getName() + " leaves table " + t.getName());
	}

	public void playerBets(int bet_amount, int bankroll) {
		System.out.println(p.getName() + " bets " + bet_amount);
	}
	
	public void playerInsures(int bet_amount, int bankroll) {
		if (bet_amount > 0) {
			System.out.println(p.getName() + " insures " + bet_amount);
		}
		else {
			System.out.println(p.getName() + " declines insurance");
		}
	}

	public void playerDealt(PlayerHand player_hand) {
		System.out.println(p.getName() + " is dealt " + player_hand);
	}

	public void playerDraws(Card dealt_card, PlayerHand new_hand) {
		System.out.println(p.getName() + " hits, now has hand " + new_hand);
	}

	public void playerStands(PlayerHand player_hand) {
		System.out.println(p.getName() + " stands with hand " + player_hand);
	}

	public void playerBusts(PlayerHand player_hand, int amount_lost, int new_bankroll) {
		System.out.println(p.getName() + " busts with hand " + player_hand 
				+ ", lost " + amount_lost);
	}

	public void playerSplits(PlayerHand player_hand) {
		System.out.println(p.getName() + " splits with hand " + player_hand);
	}

	public void playerDoublesDown(Card dealt_card, PlayerHand new_hand) {
		System.out.println(p.getName() + " doubles down, now has hand "
				+ new_hand);
	}

	public void playerWins(PlayerHand player_hand, int amount_won, int new_bankroll) {
		System.out.println(p.getName() + " beat dealer with " + 
				player_hand.getHighValidValue() + ", won " + amount_won);
	}

	public void playerLoses(PlayerHand player_hand, int amount_lost, int new_bankroll) {
		System.out.println(p.getName() + " lost to dealer with " + 
				player_hand.getHighValidValue() + ", lost " + amount_lost);
	}

	public void playerBlackjack(PlayerHand player_hand, int amount_won, int new_bankroll) {
		System.out.println(p.getName() + " received blackjack, won "
				+ amount_won);
	}

	public void playerPush(PlayerHand player_hand, int held_bankroll) {
		System.out.println(p.getName() + " pushed with dealer on " + 
				player_hand.getHighValidValue());
	}

	public void playerWinsInsurance(int amount_won, int new_bankroll) {
		System.out.println(p.getName() + " won " + amount_won + " on insurance");
	}

	public void playerLosesInsurance(int amount_lost, int new_bankroll) {
		System.out.println(p.getName() + " lost " + amount_lost + " on insurance");
	}
}
