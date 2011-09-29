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

import blackjackanalyst.Card;
import blackjackanalyst.PlayerHand;
import blackjackanalyst.PlayerObserver;
import blackjackanalyst.Table;

/**
 * An abstract class that defines an empty body for each method of interface
 * <code>PlayerObserver</code>. Concrete implementations that are only
 * interested in select methods of the interface can extend this class for
 * clarity and convenience.
 * 
 * @author Michael Parker
 */
public abstract class AbstractPlayerObserver implements PlayerObserver {
	public void playerJoins(Table t) {
		// A concrete subclass can override this method.
	}

	public void playerLeaves(Table t) {
		// A concrete subclass can override this method.
	}

	public void playerBets(int bet_amount, int bankroll) {
		// A concrete subclass can override this method.
	}
	
	public void playerInsures(int bet_amount, int bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerDealt(PlayerHand player_hand) {
		// A concrete subclass can override this method.
	}

	public void playerDraws(Card dealt_card, PlayerHand new_hand) {
		// A concrete subclass can override this method.
	}

	public void playerStands(PlayerHand player_hand) {
		// A concrete subclass can override this method.
	}

	public void playerBusts(PlayerHand player_hand, int amount_lost,
			int new_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerSplits(PlayerHand player_hand) {
		// A concrete subclass can override this method.
	}

	public void playerDoublesDown(Card dealt_card, PlayerHand new_hand) {
		// A concrete subclass can override this method.
	}

	public void playerWins(PlayerHand player_hand, int amount_won, int new_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerLoses(PlayerHand player_hand, int amount_lost, int new_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerBlackjack(PlayerHand player_hand,int amount_won, int new_bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerPush(PlayerHand player_hand,int held_bankroll) {
		// A concrete subclass can override this method.
	}
	
	public void playerWinsInsurance(int bet_amount, int new_bankroll) {
		// A concrete subclass can override this method.
	}
	
	public void playerLosesInsurance(int bet_amount, int new_bankroll) {
		// A concrete subclass can override this method.
	}
}
