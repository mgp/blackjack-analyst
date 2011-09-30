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
import blackjackanalyst.PlayerStrategy;
import blackjackanalyst.Table;

/**
 * A player that uses basic strategy correctly, provided the following table
 * options:
 * 
 * <ul>
 * <li>Eight decks</li>
 * <li>Dealer hits soft 17</li>
 * <li>Double any 2 cards</li>
 * <li>Double after split</li>
 * </ul>
 * 
 * The strategies used are based on the chart found at
 * http://wizardofodds.com/blackjack.
 * 
 * @author Michael Parker
 */
public class BasicPlayerStrategy implements PlayerStrategy {
	protected Table joinedTable;
	protected int minBet;

	public BasicPlayerStrategy() {
		joinedTable = null;
		minBet = 0;
	}

	public PlayerStrategyAction getAction(PlayerHand hand, Card dealerCard) {
		// player has a pair
		if (hand.isPair()) {
			Card firstCard = hand.getCards().get(0);
			if (firstCard.isAce()) {
				return PlayerStrategyAction.SPLIT;
			}

			int firstCardValue = firstCard.getValue();
			if ((firstCardValue == 2) || (firstCardValue == 3)) {
				int dealerCardValue = dealerCard.getValue();
				if ((dealerCardValue == 1) || (dealerCardValue >= 8)) {
					return PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.SPLIT;
			}

			if (firstCardValue == 4) {
				int dealerCardValue = dealerCard.getValue();
				if ((dealerCardValue == 5) || (dealerCardValue == 6)) {
					return PlayerStrategyAction.SPLIT;
				}
				return PlayerStrategyAction.HIT;
			}

			if (firstCardValue == 5) {
				int dealerCardValue = dealerCard.getValue();
				if ((dealerCardValue == 1) || (dealerCardValue == 10)) {
					return PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.DOUBLE_DOWN;
			}

			if (firstCardValue == 6) {
				int dealerCardValue = dealerCard.getValue();
				if ((dealerCardValue >= 2) && (dealerCardValue <= 6)) {
					return PlayerStrategyAction.SPLIT;
				}
				return PlayerStrategyAction.HIT;
			}

			if (firstCardValue == 7) {
				int dealerCardValue = dealerCard.getValue();
				if ((dealerCardValue >= 2) && (dealerCardValue <= 7)) {
					return PlayerStrategyAction.SPLIT;
				}
				return PlayerStrategyAction.HIT;
			}

			if (firstCardValue == 8) {
				return PlayerStrategyAction.SPLIT;
			}

			if (firstCardValue == 9) {
				int dealerCardValue = dealerCard.getValue();
				if ((dealerCardValue == 1) || (dealerCardValue == 7)
				    || (dealerCardValue == 10)) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.SPLIT;
			}

			if (firstCardValue == 10) {
				return PlayerStrategyAction.STAND;
			}
		}

		// player has a soft hand
		if (hand.isSoft()) {
			int playerHandValue = hand.getHighValidValue();
			if (playerHandValue == 11) {
				return PlayerStrategyAction.HIT;
			}

			if ((playerHandValue == 13) || (playerHandValue == 14)) {
				int dealerCardValue = dealerCard.getValue();
				if ((dealerCardValue == 5) || (dealerCardValue == 6)) {
					return (hand.getCards().size() == 2) ? PlayerStrategyAction.DOUBLE_DOWN
					    : PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.HIT;
			}

			if ((playerHandValue == 15) || (playerHandValue == 16)) {
				int dealerCardValue = dealerCard.getValue();
				if ((dealerCardValue >= 4) && (dealerCardValue <= 6)) {
					return (hand.getCards().size() == 2) ? PlayerStrategyAction.DOUBLE_DOWN
					    : PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.HIT;
			}

			if (playerHandValue == 17) {
				int dealerCardValue = dealerCard.getValue();
				if ((dealerCardValue >= 3) && (dealerCardValue <= 6)) {
					return (hand.getCards().size() == 2) ? PlayerStrategyAction.DOUBLE_DOWN
					    : PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.HIT;
			}

			if (playerHandValue == 18) {
				int dealerCardValue = dealerCard.getValue();
				if ((dealerCardValue >= 2) && (dealerCardValue <= 6)) {
					return (hand.getCards().size() == 2) ? PlayerStrategyAction.DOUBLE_DOWN
					    : PlayerStrategyAction.STAND;
				}
				if ((dealerCardValue == 7) || (dealerCardValue == 8)) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}

			if (playerHandValue == 19) {
				int dealerCardValue = dealerCard.getValue();
				if (dealerCardValue == 6) {
					return (hand.getCards().size() == 2) ? PlayerStrategyAction.DOUBLE_DOWN
					    : PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.STAND;
			}

			// stand on 20 or 21
			return PlayerStrategyAction.STAND;
		}

		// any remaining hands
		int playerHandValue = hand.getHighValidValue();
		if (playerHandValue <= 8) {
			return PlayerStrategyAction.HIT;
		}

		if (playerHandValue == 9) {
			int dealerCardValue = dealerCard.getValue();
			if ((dealerCardValue >= 3) && (dealerCardValue <= 6)) {
				return (hand.getCards().size() == 2) ? PlayerStrategyAction.DOUBLE_DOWN
				    : PlayerStrategyAction.HIT;
			}
			return PlayerStrategyAction.HIT;
		}

		if (playerHandValue == 10) {
			int dealerCardValue = dealerCard.getValue();
			if ((dealerCardValue == 1) || (dealerCardValue == 10)) {
				return PlayerStrategyAction.HIT;
			}
			return (hand.getCards().size() == 2) ? PlayerStrategyAction.DOUBLE_DOWN
			    : PlayerStrategyAction.HIT;
		}

		if (playerHandValue == 11) {
			return (hand.getCards().size() == 2) ? PlayerStrategyAction.DOUBLE_DOWN
			    : PlayerStrategyAction.HIT;
		}

		if (playerHandValue == 12) {
			int dealerCardValue = dealerCard.getValue();
			if ((dealerCardValue >= 4) && (dealerCardValue <= 6)) {
				return PlayerStrategyAction.STAND;
			}
			return PlayerStrategyAction.HIT;
		}

		if ((playerHandValue >= 13) && (playerHandValue <= 16)) {
			int dealerCardValue = dealerCard.getValue();
			if ((dealerCardValue >= 2) && (dealerCardValue <= 6)) {
				return PlayerStrategyAction.STAND;
			}
			return PlayerStrategyAction.HIT;
		}

		if ((playerHandValue >= 17) && (playerHandValue <= 21)) {
			return PlayerStrategyAction.STAND;
		}

		return null;
	}

	public void shoeShuffled() {
	}

	public void cardDealt(Card dealtCard) {
	}

	public void joinedTable(Table table) {
		if (joinedTable != null) {
			return;
		}
		joinedTable = table;
		minBet = table.getMinimumBet();
	}

	public void leftTable(Table table) {
		if (table != joinedTable) {
			return;
		}
		joinedTable = null;
		minBet = 0;
	}

	public int getBet(int bankroll) {
		return minBet;
	}

	public int getInsuranceBet(PlayerHand hand, int betAmount) {
		return 0;
	}
}
