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
 * A player that abides by the principles of <code>TrueCountBetPlayer</code>. 
 * Additionally, this player uses the true count (TC) to modify his basic 
 * strategy according to the chart found at
 * http://www.blackjack-student.co.uk/guide/mbs-3.asp.
 * 
 * @author Michael Parker
 */
public class ModifiedBasicStrategyPlayer extends TrueCountBetPlayer {
	public ModifiedBasicStrategyPlayer() {
		super();
	}

	public PlayerStrategyAction getAction(PlayerHand player_hand,
			Card dealer_card) {
		float tc = (raw_count * Card.CARDS_PER_DECK)
				/ ((float) cards_remaining);
		int player_hand_value = player_hand.getHighValidValue();

		/*
		 * Block 4
		 */

		if (player_hand.isPair()) {
			Card first_card = player_hand.getCards().get(0);
			if (first_card.isAce()) {
				return PlayerStrategyAction.SPLIT;
			}

			int first_card_value = first_card.getValue();
			if (first_card_value == 10) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 5) {
					if (tc >= 5f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 6) {
					if (tc >= 4f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				// split on remaining possibilities
				return PlayerStrategyAction.STAND;
			}

			if (first_card_value == 9) {
				if (dealer_card.isAce()) {
					if (tc >= 3f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}

				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 2) {
					if (tc >= -2f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 3) {
					if (tc >= -3f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if ((dealer_card_value == 4) || (dealer_card_value == 5)) {
					if (tc >= -5f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 7) {
					if (tc >= 3f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 10) {
					return PlayerStrategyAction.STAND;
				}
				// remaining possibilities are 6, 8, 9
				return PlayerStrategyAction.SPLIT;
			}

			if (first_card_value == 8) {
				return PlayerStrategyAction.SPLIT;
			}

			if (first_card_value == 7) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value >= 2) && (dealer_card_value <= 7)) {
					return PlayerStrategyAction.SPLIT;
				}
				return PlayerStrategyAction.HIT;
			}

			if (first_card_value == 6) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 2) {
					if (tc >= -1f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 3) {
					if (tc >= -3f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 4) {
					if (tc >= -4f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if ((dealer_card_value == 5) || (dealer_card_value == 6)) {
					return PlayerStrategyAction.SPLIT;
				}
				// hit on remaining possibilities
				return PlayerStrategyAction.HIT;
			}

			if (first_card_value == 5) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value == 1) || (dealer_card_value == 10)) {
					return PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.DOUBLE_DOWN;
			}

			if (first_card_value == 4) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 4) {
					if (tc >= 3f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 5) {
					if (tc >= -1f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 6) {
					if (tc >= -2f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.HIT;
			}

			if (first_card_value == 3) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 2) {
					if (tc >= -2f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 3) {
					if (tc >= -5f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if ((dealer_card_value >= 4) && (dealer_card_value <= 7)) {
					return PlayerStrategyAction.SPLIT;
				}
				return PlayerStrategyAction.HIT;
			}

			if (first_card_value == 2) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 2) {
					if (tc >= -4f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 3) {
					if (tc >= -5f) {
						return PlayerStrategyAction.SPLIT;
					}
					return PlayerStrategyAction.HIT;
				}
				if ((dealer_card_value >= 4) && (dealer_card_value <= 7)) {
					return PlayerStrategyAction.SPLIT;
				}
				return PlayerStrategyAction.HIT;
			}
		}

		/*
		 * Block 3
		 */

		if ((player_hand.getCards().size() == 2) && player_hand.isSoft()) {
			Card pat_card = player_hand.getCards().get(0).isAce() ? 
					player_hand.getCards().get(1) : player_hand.getCards().get(0);
			int pat_card_value = pat_card.getValue();

			if (pat_card_value == 9) {
				int dealer_card_value = dealer_card.getValue();
				if ((dealer_card_value == 5) || (dealer_card_value == 6)) {
					if (tc >= 5f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.STAND;
				}
			}

			if (pat_card_value == 8) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 3) {
					if (tc >= 5f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.STAND;
				}
				if (dealer_card_value == 4) {
					if (tc >= 3f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.STAND;
				}
				if ((dealer_card_value == 5) || (dealer_card_value == 6)) {
					if (tc >= 1f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.STAND;
			}

			if (pat_card_value == 7) {
				if (dealer_card.isAce()) {
					if (tc >= 1f) {
						return PlayerStrategyAction.STAND;
					}
					return PlayerStrategyAction.HIT;
				}

				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 2) {
					if (tc >= 1f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.STAND;
				}
				if (dealer_card_value == 3) {
					if (tc >= -1f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.STAND;
				}
				if (dealer_card_value == 4) {
					if (tc >= -5f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.STAND;
				}
				if ((dealer_card_value == 5) || (dealer_card_value == 6)) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				if ((dealer_card_value == 7) || (dealer_card_value == 8)) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}

			if (pat_card_value == 6) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 2) {
					if (tc >= 1f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.STAND;
				}
				if (dealer_card_value == 3) {
					if (tc >= -2f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.STAND;
				}
				if (dealer_card_value == 4) {
					if (tc >= -5f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.STAND;
				}
				if ((dealer_card_value == 5) || (dealer_card_value == 6)) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				// hit on remaining possibilities
				return PlayerStrategyAction.HIT;
			}

			if (pat_card_value == 5) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 3) {
					if (tc >= 3f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 4) {
					if (tc >= -2f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 5) {
					if (tc >= -5f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 6) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				// hit on remaining possibilities
				return PlayerStrategyAction.HIT;
			}

			if (pat_card_value == 4) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 3) {
					if (tc >= 5f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 4) {
					if (tc >= -1f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 5) {
					if (tc >= -4f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 6) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				// hit on remaining possibilities
				return PlayerStrategyAction.HIT;
			}

			if (pat_card_value == 3) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 4) {
					if (tc >= 1f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 5) {
					if (tc >= -2f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 6) {
					if (tc >= -4f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				return PlayerStrategyAction.HIT;
			}

			if (pat_card_value == 2) {
				int dealer_card_value = dealer_card.getValue();
				if (dealer_card_value == 4) {
					if (tc >= 2f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 5) {
					if (tc >= -1f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				if (dealer_card_value == 6) {
					if (tc >= -2f) {
						return PlayerStrategyAction.DOUBLE_DOWN;
					}
					return PlayerStrategyAction.HIT;
				}
				// hit on remaining possibilities
				return PlayerStrategyAction.HIT;
			}
		}

		/*
		 * Block 2
		 */

		if ((player_hand_value == 18) && player_hand.isSoft()) {
			if (dealer_card.isAce()) {
				if (tc >= 1f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}

			int dealer_card_value = dealer_card.getValue();
			if ((dealer_card_value >= 2) && (dealer_card_value <= 8)) {
				return PlayerStrategyAction.STAND;
			}
			// hit on remaining possibilities
			return PlayerStrategyAction.HIT;
		}

		if ((player_hand_value == 17) && player_hand.isSoft()) {
			return PlayerStrategyAction.HIT;
		}

		if (player_hand_value >= 17) {
			if (dealer_card.isAce()) {
				if (tc >= -5f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			// stand on remaining possibilities
			return PlayerStrategyAction.STAND;
		}

		if (player_hand_value == 16) {
			int dealer_card_value = dealer_card.getValue();
			if ((dealer_card_value >= 2) && (dealer_card_value <= 6)) {
				return PlayerStrategyAction.STAND;
			}
			if ((dealer_card_value == 7) || (dealer_card_value == 8)) {
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 9) {
				if (tc >= 4f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 10) {
				if (tc >= 0f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			// remaining possibility is ace
			if (tc >= 3f) {
				return PlayerStrategyAction.STAND;
			}
			return PlayerStrategyAction.HIT;
		}

		if (player_hand_value == 15) {
			int dealer_card_value = dealer_card.getValue();
			if ((dealer_card_value >= 2) && (dealer_card_value <= 6)) {
				return PlayerStrategyAction.STAND;
			}
			if ((dealer_card_value >= 7) && (dealer_card_value <= 9)) {
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 10) {
				if (tc >= 3f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			// remaining possibility is ace
			if (tc >= 5f) {
				return PlayerStrategyAction.STAND;
			}
			return PlayerStrategyAction.HIT;
		}

		if (player_hand_value == 14) {
			int dealer_card_value = dealer_card.getValue();
			if (dealer_card_value == 2) {
				if (tc >= -3f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 3) {
				if (tc >= -4f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 4) {
				if (tc >= -5f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if ((dealer_card_value == 5) || (dealer_card_value == 6)) {
				return PlayerStrategyAction.STAND;
			}
			return PlayerStrategyAction.HIT;
		}

		if (player_hand_value == 13) {
			int dealer_card_value = dealer_card.getValue();
			if (dealer_card_value == 2) {
				if (tc >= -1f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 3) {
				if (tc >= -2f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 4) {
				if (tc >= -4f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 5) {
				if (tc >= -5f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 6) {
				if (tc >= -5f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			return PlayerStrategyAction.HIT;
		}

		if (player_hand_value == 12) {
			int dealer_card_value = dealer_card.getValue();
			if (dealer_card_value == 2) {
				if (tc >= 2f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 3) {
				if (tc >= 1f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 4) {
				if (tc >= 0f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 5) {
				if (tc >= -1f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 6) {
				if (tc >= -2f) {
					return PlayerStrategyAction.STAND;
				}
				return PlayerStrategyAction.HIT;
			}
			return PlayerStrategyAction.HIT;
		}

		/*
		 * Block 1
		 */

		if (player_hand_value == 11) {
			int dealer_card_value = dealer_card.getValue();
			if ((dealer_card_value >= 2) && (dealer_card_value <= 8)) {
				return PlayerStrategyAction.DOUBLE_DOWN;
			}
			if (dealer_card_value == 9) {
				if (tc >= -4f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 10) {
				if (tc >= -3f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			// remaining possibility is ace
			if (tc >= 1f) {
				return PlayerStrategyAction.DOUBLE_DOWN;
			}
			return PlayerStrategyAction.HIT;
		}

		if (player_hand_value == 10) {
			int dealer_card_value = dealer_card.getValue();
			if ((dealer_card_value >= 2) && (dealer_card_value <= 7)) {
				return PlayerStrategyAction.DOUBLE_DOWN;
			}
			if (dealer_card_value == 8) {
				if (tc >= -4f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 9) {
				if (tc >= -1f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			// remaining possibilities are ten and ace
			if (tc >= 4f) {
				return PlayerStrategyAction.DOUBLE_DOWN;
			}
			return PlayerStrategyAction.HIT;
		}

		if (player_hand_value == 9) {
			int dealer_card_value = dealer_card.getValue();
			if (dealer_card_value == 2) {
				if (tc >= 1f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 3) {
				if (tc >= -1f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 4) {
				if (tc >= -2f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 5) {
				if (tc >= -4f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 6) {
				if (tc >= -5f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 7) {
				if (tc >= 3f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			// hit on remaining possibilities
			return PlayerStrategyAction.HIT;
		}

		if (player_hand_value == 8) {
			int dealer_card_value = dealer_card.getValue();
			if (dealer_card_value == 4) {
				if (tc >= 5f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 5) {
				if (tc >= 3f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			if (dealer_card_value == 6) {
				if (tc >= 2f) {
					return PlayerStrategyAction.DOUBLE_DOWN;
				}
				return PlayerStrategyAction.HIT;
			}
			// hit on remaining possibilities
			return PlayerStrategyAction.HIT;
		}

		if (player_hand_value <= 7) {
			return PlayerStrategyAction.HIT;
		}

		System.err.println("Reached last return statement with " + player_hand);
		return null;
	}
}
