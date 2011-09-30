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
 * The default player strategy which imitates the dealer by hitting on any value
 * below 16 or soft 17. On any other value, the player stands.
 * 
 * @author Michael Parker
 */
public class DefaultPlayerStrategy implements PlayerStrategy {
	protected Table joinedTable;
	protected int betAmount;

	public DefaultPlayerStrategy() {
		joinedTable = null;
		betAmount = 0;
	}

	public PlayerStrategyAction getAction(PlayerHand curr_hand, Card dealer_card) {
		int bestValue = curr_hand.getHighValidValue();
		if ((bestValue > 17) || ((bestValue == 17) && !curr_hand.isSoft())) {
			return PlayerStrategyAction.STAND;
		}
		return PlayerStrategyAction.HIT;
	}

	public void shoeShuffled() {
	}

	public void cardDealt(Card dealt_card) {
	}

	public void joinedTable(Table table) {
		if (joinedTable != null) {
			return;
		}
		joinedTable = table;
		betAmount = table.getMinimumBet();
	}

	public void leftTable(Table table) {
		if (table != joinedTable) {
			return;
		}
		joinedTable = null;
		betAmount = 0;
	}

	public int getBet(int bankroll) {
		return betAmount;
	}

	public int getInsuranceBet(PlayerHand hand, int betAmount) {
		return 0;
	}
}
