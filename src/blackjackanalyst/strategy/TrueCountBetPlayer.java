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

package blackjackanalyst.strategy;

import blackjackanalyst.Card;
import blackjackanalyst.PlayerHand;

/**
 * A player that abides by the principles of <code>BasicStrategyPlayer</code>. 
 * Additionally, this player counts cards using the hi-lo system and a true 
 * count (TC), and adjusts his bet accordingly:
 * 
 * <ul>
 * <li>TC +1 or lower: initial bet</li>
 * <li>TC +2: 2x initial bet</li>
 * <li>TC +3: 3x initial bet</li>
 * <li>TC +4: 5x initial bet</li>
 * <li>TC greater than +4: 10x initial bet</li>
 * </ul>
 * 
 * @author Michael Parker
 */
public class TrueCountBetPlayer extends BasicStrategyPlayer {
	protected int cards_remaining;
	protected int raw_count;

	public TrueCountBetPlayer() {
		super();

		cards_remaining = 8 * Card.CARDS_PER_DECK;
		raw_count = 0;
	}

	public void shoeShuffled() {
		cards_remaining = 8 * Card.CARDS_PER_DECK;
		raw_count = 0;
	}

	public void cardDealt(Card dealt_card) {
		// decrement cards remaining
		--cards_remaining;
		// adjust count accordingly
		if (dealt_card.isAce() || (dealt_card.getValue() == 10)) {
			--raw_count;
		}
		else if ((dealt_card.getValue() >= 2) && (dealt_card.getValue() <= 6)) {
			++raw_count;
		}
	}

	public int getBet(int bankroll) {
		// get the true count and bet accordingly
		float tc = (raw_count * Card.CARDS_PER_DECK) / ((float) cards_remaining);

		if (tc <= 1f) {
			return min_bet;
		}
		if (tc <= 2f) {
			return (2 * min_bet);
		}
		if (tc <= 3f) {
			return (3 * min_bet);
		}
		if (tc <= 4f) {
			return (5 * min_bet);
		}
		return (10 * min_bet);
	}
	
	public int getInsuranceBet(PlayerHand curr_hand, int bet_amount) {
		// get the true count and bet accordingly
		float tc = (raw_count * Card.CARDS_PER_DECK) / ((float) cards_remaining);
		
		return (tc >= 3f) ? (bet_amount / 2) : 0;
	}
}
