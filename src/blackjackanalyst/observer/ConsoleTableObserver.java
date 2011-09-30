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

	public void dealerDealt(Card up_card) {
		System.out.println("The dealer shows the up card " + up_card);
	}

	public void dealerDealt(Card down_card, Hand dealer_hand) {
		System.out.println("The dealer reveals its down card, has hand "
		    + dealer_hand);
	}

	public void dealerBlackjack(Hand dealer_hand) {
		System.out.println("The dealer reveals a blackjack, " + dealer_hand);
	}

	public void dealerDraws(Card dealt_card, Hand new_hand) {
		System.out.println("The dealer hits, now has hand " + new_hand);
	}

	public void dealerStands(Hand dealer_hand) {
		System.out.println("The dealer stands with hand " + dealer_hand);
	}

	public void dealerBusts(Hand dealer_hand) {
		System.out.println("The dealer busts with hand " + dealer_hand);
	}

	public void playerJoins(Player joined_player) {
		System.out.println(joined_player.getName() + " joins the table");
	}

	public void playerLeaves(Player left_player) {
		System.out.println(left_player.getName() + " leaves the table");
	}

	public void playerBets(Player betting_player, int bet_amount, int bankroll) {
		System.out.println(betting_player.getName() + " bets " + bet_amount);
	}

	public void playerInsures(Player betting_player, int bet_amount, int bankroll) {
		if (bet_amount > 0) {
			System.out.println(betting_player.getName() + " insures " + bet_amount);
		} else {
			System.out.println(betting_player.getName() + " declines insurance");
		}
	}

	public void playerDealt(Player dealt_player, PlayerHand player_hand) {
		System.out.println(dealt_player.getName() + " is dealt " + player_hand);
	}

	public void playerDraws(Player dealt_player, Card dealt_card,
	    PlayerHand new_hand) {
		System.out.println(dealt_player.getName() + " hits, now has hand "
		    + new_hand);
	}

	public void playerStands(Player dealt_player, PlayerHand player_hand) {
		System.out.println(dealt_player.getName() + " stands with hand "
		    + player_hand);
	}

	public void playerBusts(Player dealt_player, PlayerHand player_hand,
	    int amount_lost, int new_bankroll) {
		System.out.println(dealt_player.getName() + " busts with hand "
		    + player_hand + ", lost " + amount_lost);
	}

	public void playerSplits(Player dealt_player, PlayerHand player_hand) {
		System.out.println(dealt_player.getName() + " splits with hand "
		    + player_hand);
	}

	public void playerDoublesDown(Player dealt_player, Card dealt_card,
	    PlayerHand new_hand) {
		System.out.println(dealt_player.getName() + " doubles down, now has hand "
		    + new_hand);
	}

	public void playerWins(Player dealt_player, PlayerHand player_hand,
	    int amount_won, int new_bankroll) {
		System.out.println(dealt_player.getName() + " beat dealer with "
		    + player_hand.getHighValidValue() + ", won " + amount_won);
	}

	public void playerLoses(Player dealt_player, PlayerHand player_hand,
	    int amount_lost, int new_bankroll) {
		System.out.println(dealt_player.getName() + " lost to dealer with "
		    + player_hand.getHighValidValue() + ", lost " + amount_lost);
	}

	public void playerBlackjack(Player dealt_player, PlayerHand player_hand,
	    int amount_won, int new_bankroll) {
		System.out.println(dealt_player.getName() + " received blackjack, won "
		    + amount_won);
	}

	public void playerPush(Player dealt_player, PlayerHand player_hand,
	    int held_bankroll) {
		System.out.println(dealt_player.getName() + " pushed with dealer on "
		    + player_hand.getHighValidValue());
	}

	public void playerWinsInsurance(Player dealt_player, int amount_won,
	    int new_bankroll) {
		System.out.println(dealt_player.getName() + " won " + amount_won
		    + " on insurance");
	}

	public void playerLosesInsurance(Player dealt_player, int amount_lost,
	    int new_bankroll) {
		System.out.println(dealt_player.getName() + " lost " + amount_lost
		    + " on insurance");
	}
}
