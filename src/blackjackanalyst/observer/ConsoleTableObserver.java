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

import java.util.List;

import blackjackanalyst.Card;
import blackjackanalyst.Hand;
import blackjackanalyst.Player;
import blackjackanalyst.PlayerHand;
import blackjackanalyst.TableObserver;

/**
 * An implementation of interface {@link TableObserver} that writes to the
 * console.
 * 
 * @author Michael Parker
 */
public class ConsoleTableObserver implements TableObserver {
	protected static ConsoleTableObserver instance;

	public static ConsoleTableObserver getInstance() {
		if (instance == null) {
			instance = new ConsoleTableObserver();
		}
		return instance;
	}

	protected ConsoleTableObserver() {
	}

	public void newRound(List<Player> players) {
		System.out.println("\nNew round started with " + players.size()
		    + " players");
	}

	public void shoeShuffled() {
		System.out.println("The shoe is shuffled.");
	}

	public void dealerDealt(Card upCard) {
		System.out.println("The dealer shows the up card " + upCard);
	}

	public void dealerDealt(Card downCard, Hand hand) {
		System.out.println("The dealer reveals its down card, has hand " + hand);
	}

	public void dealerBlackjack(Hand hand) {
		System.out.println("The dealer reveals a blackjack, " + hand);
	}

	public void dealerDraws(Card card, Hand hand) {
		System.out.println("The dealer hits, now has hand " + hand);
	}

	public void dealerStands(Hand hand) {
		System.out.println("The dealer stands with hand " + hand);
	}

	public void dealerBusts(Hand hand) {
		System.out.println("The dealer busts with hand " + hand);
	}

	public void playerJoins(Player player) {
		System.out.println(player.getName() + " joins the table");
	}

	public void playerLeaves(Player player) {
		System.out.println(player.getName() + " leaves the table");
	}

	public void playerBets(Player player, int betAmount, int bankroll) {
		System.out.println(player.getName() + " bets " + betAmount);
	}

	public void playerInsures(Player player, int betAmount, int bankroll) {
		if (betAmount > 0) {
			System.out.println(player.getName() + " insures " + betAmount);
		} else {
			System.out.println(player.getName() + " declines insurance");
		}
	}

	public void playerDealt(Player player, PlayerHand hand) {
		System.out.println(player.getName() + " is dealt " + hand);
	}

	public void playerDraws(Player player, Card card, PlayerHand hand) {
		System.out.println(player.getName() + " hits, now has hand " + hand);
	}

	public void playerStands(Player player, PlayerHand hand) {
		System.out.println(player.getName() + " stands with hand " + hand);
	}

	public void playerBusts(Player player, PlayerHand hand, int amountLost,
	    int newBankroll) {
		System.out.println(player.getName() + " busts with hand " + hand
		    + ", lost " + amountLost);
	}

	public void playerSplits(Player player, PlayerHand hand) {
		System.out.println(player.getName() + " splits with hand " + hand);
	}

	public void playerDoublesDown(Player player, Card card, PlayerHand hand) {
		System.out
		    .println(player.getName() + " doubles down, now has hand " + hand);
	}

	public void playerWins(Player player, PlayerHand hand, int amountWon,
	    int newBankroll) {
		System.out.println(player.getName() + " beat dealer with "
		    + hand.getHighValidValue() + ", won " + amountWon);
	}

	public void playerLoses(Player player, PlayerHand hand, int amountLost,
	    int newBankroll) {
		System.out.println(player.getName() + " lost to dealer with "
		    + hand.getHighValidValue() + ", lost " + amountLost);
	}

	public void playerBlackjack(Player player, PlayerHand hand, int amountWon,
	    int newBankroll) {
		System.out.println(player.getName() + " received blackjack, won "
		    + amountWon);
	}

	public void playerPush(Player player, PlayerHand hand, int bankroll) {
		System.out.println(player.getName() + " pushed with dealer on "
		    + hand.getHighValidValue());
	}

	public void playerWinsInsurance(Player player, int amountWon, int newBankroll) {
		System.out
		    .println(player.getName() + " won " + amountWon + " on insurance");
	}

	public void playerLosesInsurance(Player player, int amountLost,
	    int newBankroll) {
		System.out.println(player.getName() + " lost " + amountLost
		    + " on insurance");
	}
}
