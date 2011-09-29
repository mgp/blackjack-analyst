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
public class BasicStrategyPlayer implements PlayerStrategy {
	protected Table joined_table;
	protected int min_bet;
	
	public BasicStrategyPlayer() {
		joined_table = null;
		min_bet = 0;
	}
	
	public PlayerStrategyAction getAction(PlayerHand player_hand, Card dealer_card) {
		// player has a pair
		if (player_hand.isPair()) {
			Card first_card = player_hand.getCards().get(0);
			if (first_card.isAce()) {
				return PlayerStrategyAction.SPLIT;
			}
			
			int first_card_value = first_card.getValue();
			if ((first_card_value == 2) || (first_card_value == 3)) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value == 1) || (dealer_card_value >= 8)) {
					return PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.SPLIT;
			}
			
			if (first_card_value == 4) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value == 5) || (dealer_card_value == 6)) {
					return PlayerStrategyAction.SPLIT;
				}
				return PlayerStrategyAction.HIT;
			}
			
			if (first_card_value == 5) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value == 1) || (dealer_card_value == 10)) {
					return PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.DOUBLE_DOWN;
			}
			
			if (first_card_value == 6) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value >= 2) && (dealer_card_value <= 6)) {
					return PlayerStrategyAction.SPLIT;
				}
				return PlayerStrategyAction.HIT;
			}
			
			if (first_card_value == 7) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value >= 2) && (dealer_card_value <= 7)) {
					return PlayerStrategyAction.SPLIT;
				}
				return PlayerStrategyAction.HIT;
			}
			
			if (first_card_value == 8) {
				return PlayerStrategyAction.SPLIT;
			}
			
			if (first_card_value == 9) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value == 1) || (dealer_card_value == 7) || 
						(dealer_card_value == 10)) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.SPLIT;
			}
			
			if (first_card_value == 10) {
				return PlayerStrategyAction.STAND;
			}
		}
		
		// player has a soft hand
		if (player_hand.isSoft()) {
			int player_hand_value = player_hand.getHighValidValue();
			if (player_hand_value == 11) {
				return PlayerStrategyAction.HIT;
			}
			
			if ((player_hand_value == 13) || (player_hand_value == 14)) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value == 5) || (dealer_card_value == 6)) {
					return (player_hand.getCards().size() == 2) ? 
							PlayerStrategyAction.DOUBLE_DOWN : PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.HIT;
			}
			
			if ((player_hand_value == 15) || (player_hand_value == 16)) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value >= 4) && (dealer_card_value <= 6)) {
					return (player_hand.getCards().size() == 2) ? 
							PlayerStrategyAction.DOUBLE_DOWN : PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.HIT;
			}
			
			if (player_hand_value == 17) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value >= 3) && (dealer_card_value <= 6)) {
					return (player_hand.getCards().size() == 2) ? 
							PlayerStrategyAction.DOUBLE_DOWN : PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.HIT;
			}
			
			if (player_hand_value == 18) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value >= 2) && (dealer_card_value <= 6)) {
					return (player_hand.getCards().size() == 2) ? 
							PlayerStrategyAction.DOUBLE_DOWN : PlayerStrategyAction.STAND;
				}
				if ((dealer_card_value == 7) || (dealer_card_value == 8)) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			
			if (player_hand_value == 19) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 6) {
					return (player_hand.getCards().size() == 2) ? 
							PlayerStrategyAction.DOUBLE_DOWN : PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.STAND;
			}
			
			// stand on 20 or 21
			return PlayerStrategyAction.STAND;
		}
		
		// any remaining hands
		int player_hand_value = player_hand.getHighValidValue();
		if (player_hand_value <= 8) {
			return PlayerStrategyAction.HIT;
		}
		
		if (player_hand_value == 9) {
			int dealer_card_value = dealer_card.getValue();
			if ((dealer_card_value >= 3) && (dealer_card_value <= 6)) {
				return (player_hand.getCards().size() == 2) ? 
						PlayerStrategyAction.DOUBLE_DOWN : PlayerStrategyAction.HIT;
			}
			return PlayerStrategyAction.HIT;
		}
		
		if (player_hand_value == 10) {
			int dealer_card_value = dealer_card.getValue();
			if ((dealer_card_value == 1) || (dealer_card_value == 10)) {
				return PlayerStrategyAction.HIT;
			}
			return (player_hand.getCards().size() == 2) ? 
					PlayerStrategyAction.DOUBLE_DOWN : PlayerStrategyAction.HIT;
		}
		
		if (player_hand_value == 11) {
			return (player_hand.getCards().size() == 2) ? 
					PlayerStrategyAction.DOUBLE_DOWN : PlayerStrategyAction.HIT;
		}
		
		if (player_hand_value == 12) {
			int dealer_card_value = dealer_card.getValue();
			if ((dealer_card_value >= 4) && (dealer_card_value <= 6)) {
				return PlayerStrategyAction.STAND;
			}
			return PlayerStrategyAction.HIT;
		}
		
		if ((player_hand_value >= 13) && (player_hand_value <= 16)) {
			int dealer_card_value = dealer_card.getValue();
			if ((dealer_card_value >= 2) && (dealer_card_value <= 6)) {
				return PlayerStrategyAction.STAND;
			}
			return PlayerStrategyAction.HIT;
		}

		if ((player_hand_value >= 17) && (player_hand_value <= 21)) {
			return PlayerStrategyAction.STAND;
		}
		
		return null;
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
		min_bet = t.getMinimumBet();
	}

	public void leftTable(Table t) {
		if (t != joined_table) {
			return;
		}
		joined_table = null;
		min_bet = 0;
	}

	public int getBet(int bankroll) {
		return min_bet;
	}
	
	public int getInsuranceBet(PlayerHand curr_hand, int bet_amount) {
		return 0;
	}
}
