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
 * A player that abides by the principles of {@link BasicPlayerStrategy}.
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
public class TrueCountPlayerStrategy extends BasicPlayerStrategy {
	protected int cardsRemaining;
	protected int rawCount;

	public TrueCountPlayerStrategy() {
		super();

		cardsRemaining = 8 * Card.CARDS_PER_DECK;
		rawCount = 0;
	}

	public void shoeShuffled() {
		cardsRemaining = 8 * Card.CARDS_PER_DECK;
		rawCount = 0;
	}

	public void cardDealt(Card dealtCard) {
		// decrement cards remaining
		--cardsRemaining;
		// adjust count accordingly
		if (dealtCard.isAce() || (dealtCard.getValue() == 10)) {
			--rawCount;
		} else if ((dealtCard.getValue() >= 2) && (dealtCard.getValue() <= 6)) {
			++rawCount;
		}
	}

	public int getBet(int bankroll) {
		// get the true count and bet accordingly
		float trueCount = (rawCount * Card.CARDS_PER_DECK) / ((float) cardsRemaining);

		if (trueCount <= 1f) {
			return minBet;
		}
		if (trueCount <= 2f) {
			return (2 * minBet);
		}
		if (trueCount <= 3f) {
			return (3 * minBet);
		}
		if (trueCount <= 4f) {
			return (5 * minBet);
		}
		return (10 * minBet);
	}

	public int getInsuranceBet(PlayerHand hand, int betAmount) {
		// get the true count and bet accordingly
		float tc = (rawCount * Card.CARDS_PER_DECK) / ((float) cardsRemaining);

		return (tc >= 3f) ? (betAmount / 2) : 0;
	}
}
