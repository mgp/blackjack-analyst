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
 * An abstract class that defines an empty body for each method of interface
 * <code>TableObserver</code>. Concrete implementations that are only
 * interested in select methods of the interface can extend this class for
 * clarity and convenience.
 * 
 * @author Michael Parker
 */
public abstract class AbstractTableObserver implements TableObserver {
	public void newRound(List<Player> players) {
		// A concrete subclass can override this method.
	}

	public void shoeShuffled() {
		// A concrete subclass can override this method.
	}

	public void dealerDealt(Card up_card) {
		// A concrete subclass can override this method.
	}

	public void dealerDealt(Card down_card, Hand dealer_hand) {
		// A concrete subclass can override this method.
	}

	public void dealerBlackjack(Hand dealer_hand) {
		// A concrete subclass can override this method.
	}

	public void dealerDraws(Card dealt_card, Hand new_hand) {
		// A concrete subclass can override this method.
	}

	public void dealerStands(Hand dealer_hand) {
		// A concrete subclass can override this method.
	}

	public void dealerBusts(Hand dealer_hand) {
		// A concrete subclass can override this method.
	}

	public void playerJoins(Player joined_player) {
		// A concrete subclass can override this method.
	}

	public void playerLeaves(Player left_player) {
		// A concrete subclass can override this method.
	}

	public void playerBets(Player betting_player, int bet_amount, int bankroll) {
		// A concrete subclass can override this method.
	}
	
	public void playerInsures(Player betting_player, int bet_amount, int new_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerDealt(Player dealt_player, PlayerHand player_hand) {
		// A concrete subclass can override this method.
	}

	public void playerDraws(Player dealt_player, Card dealt_card,
			PlayerHand new_hand) {
		// A concrete subclass can override this method.
	}

	public void playerStands(Player dealt_player, PlayerHand player_hand) {
		// A concrete subclass can override this method.
	}

	public void playerBusts(Player dealt_player, PlayerHand player_hand,
			int amount_lost, int new_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerSplits(Player dealt_player, PlayerHand player_hand) {
		// A concrete subclass can override this method.
	}

	public void playerDoublesDown(Player dealt_player, Card dealt_card,
			PlayerHand new_hand) {
		// A concrete subclass can override this method.
	}

	public void playerWins(Player dealt_player, PlayerHand player_hand, 
			int amount_won, int new_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerLoses(Player dealt_player, PlayerHand player_hand, 
			int amount_lost, int new_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerBlackjack(Player dealt_player, PlayerHand player_hand, 
			int amount_won, int new_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerPush(Player dealt_player, PlayerHand player_hand, int held_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerWinsInsurance(Player dealt_player, int amount_won, int new_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerLosesInsurance(Player dealt_player, int amount_lost, int new_bankroll) {
		// A concrete subclass can override this method.
	}
}
