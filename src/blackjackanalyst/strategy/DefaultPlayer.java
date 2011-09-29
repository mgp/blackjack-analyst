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

package blackjackanalyst.strategy;

import blackjackanalyst.Card;
import blackjackanalyst.PlayerHand;
import blackjackanalyst.PlayerStrategy;
import blackjackanalyst.Table;

/**
 * The default player strategy which immitates the dealer by hitting on any
 * value below 16 or soft 17. On any other value, the player stands.
 * 
 * @author Michael Parker
 */
public class DefaultPlayer implements PlayerStrategy {
	protected Table joined_table;
	protected int bet_amount;

	public DefaultPlayer() {
		joined_table = null;
		bet_amount = 0;
	}

	public PlayerStrategyAction getAction(PlayerHand curr_hand, Card dealer_card) {
		int best_value = curr_hand.getHighValidValue();
		if ((best_value > 17) || ((best_value == 17) && !curr_hand.isSoft())) {
			return PlayerStrategyAction.STAND;
		}
		return PlayerStrategyAction.HIT;
	}

	public void shoeShuffled() {
	}

	public void cardDealt(Card dealt_card) {
	}

	public void joinedTable(Table t) {
		if (joined_table != null) {
			return;
		}
		joined_table = t;
		bet_amount = t.getMinimumBet();
	}

	public void leftTable(Table t) {
		if (t != joined_table) {
			return;
		}
		joined_table = null;
		bet_amount = 0;
	}

	public int getBet(int bankroll) {
		return bet_amount;
	}
	
	public int getInsuranceBet(PlayerHand curr_hand, int bet_amount) {
		return 0;
	}
}
