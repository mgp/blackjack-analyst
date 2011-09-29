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

package blackjackanalyst;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A hand of cards.
 * 
 * @author Michael Parker
 */
public class Hand {
	protected final List<Card> hand_cards;
	protected final List<Card> hand_cards_ro;
	protected boolean finished;

	protected final TreeSet<Integer> hand_values;
	protected final Set<Integer> hand_values_ro;
	protected int max_valid;

	protected Hand() {
		hand_cards = new LinkedList<Card>();
		hand_cards_ro = Collections.unmodifiableList(hand_cards);

		hand_values = new TreeSet<Integer>();
		hand_values_ro = Collections.unmodifiableSet(hand_values);
		
		max_valid = 0;
	}
	
	/**
	 * Creates a new hand containing the given card.
	 * 
	 * @param _first
	 * the card to add to the new hand
	 */
	protected Hand(Card _first) {
		this();
		// add first card to hand
		add(_first);
	}

	/**
	 * Creates a new hand containing the given cards.
	 * 
	 * @param _first
	 * the card to add to the new hand first
	 * @param _second
	 * the card to add to the new hand second
	 */
	protected Hand(Card _first, Card _second) {
		this();
		// add both cards to hand
		add(_first);
		add(_second);
	}

	/**
	 * Returns a list containing the cards in the hand, appearing in the order
	 * in which they were added. The returned list may not be modified.
	 * 
	 * @return the list of cards in the hand
	 */
	public List<Card> getCards() {
		return hand_cards_ro;
	}

	/**
	 * Returns the highest possible value that can be made from the hand, but
	 * does not exceed 21. If no such value exists, meaning that this hand has
	 * busted, this method returns <code>-1</code>.
	 * 
	 * @return the highest possible hand value not exceeding 21
	 */
	public int getHighValidValue() {
		return max_valid;
	}

	/**
	 * Returns the lowest possible value that can be made from the hand. This
	 * value may exceed 21.
	 * 
	 * @return the lowest possible hand value
	 */
	public int getLowValue() {
		return hand_values.first();
	}

	/**
	 * Returns the highest possible value that can be made from the hand. This
	 * value may exceed 21.
	 * 
	 * @return the highest possible hand value
	 */
	public int getHighValue() {
		return hand_values.last();
	}

	/**
	 * Returns all possible values that can be made from the hand, where values
	 * appear in ascending order.
	 * 
	 * @return all possible hand values
	 */
	public Set<Integer> getValues() {
		return hand_values_ro;
	}

	/**
	 * Returns whether the hand has busted, meaning that the smallest possible
	 * hand value is greater than 21.
	 * 
	 * @return <code>true</code> if the hand has busted, <code>false</code>
	 * otherwise
	 */
	public boolean isBusted() {
		return (getLowValue() > 21);
	}

	/**
	 * Returns whether the current hand is a blackjack, which is an ace and a
	 * ten-valued card.
	 * 
	 * @return <code>true</code> if the hand is a blackjack,
	 * <code>false</code> otherwise
	 */
	public boolean isBlackjack() {
		return ((hand_cards.size() == 2) && (getHighValidValue() == 21));
	}

	/**
	 * Returns whether the hand is soft, meaning that it has not busted and
	 * whether the highest value that does not exceed 21 contains an ace valued
	 * as 11.
	 * 
	 * @return <code>true</code> if the hand is soft, <code>false</code>
	 * otherwise
	 */
	public boolean isSoft() {
		return (!isBusted() && (getLowValue() <= (getHighValidValue() - 10)));
	}
	
	/**
	 * Returns whether this hand is finished. The hand is finished if the 
	 * player or dealer has stood or busted, or if the player has doubled down 
	 * and received his or her final card.
	 * 
	 * @return
	 * <code>true</code> if the hand is finished, <code>false</code> otherwise
	 */
	public boolean isFinished() {
		return finished;
	}
	
	protected final void add(Card dealt_card) {
		if (hand_cards.isEmpty()) {
			// add first card dealt to hand
			hand_cards.add(dealt_card);
			if (dealt_card.isAce()) {
				// add both values for ace
				hand_values.add(1);
				hand_values.add(11);
				max_valid = 11;
			}
			else {
				// not ace, add single value
				int card_value = dealt_card.getValue();
				hand_values.add(card_value);
				max_valid = card_value;
			}
			return;
		}
		
		// update all possible hand values
		max_valid = -1;
		TreeSet<Integer> new_values = new TreeSet<Integer>();
		for (Iterator<Integer> i = hand_values.iterator(); i.hasNext();) {
			int curr_value = i.next();
			int new_value;
			if (dealt_card.isAce()) {
				// update low value for ace
				new_value = curr_value + 1;
				new_values.add(new_value);
				if ((new_value <= 21) && (new_value > max_valid)) {
					max_valid = new_value;
				}
				// update high value for ace
				new_value = curr_value + 11;
				new_values.add(new_value);
				if ((new_value <= 21) && (new_value > max_valid)) {
					max_valid = new_value;
				}
			}
			else {
				// not ace, update single value
				new_value = curr_value + dealt_card.getValue();
				new_values.add(new_value);
				if ((new_value <= 21) && (new_value > max_valid)) {
					max_valid = new_value;
				}
			}
		}
		hand_values.clear();
		hand_values.addAll(new_values);
		// add card to hand
		hand_cards.add(dealt_card);
	}

	public String toString() {
		StringBuffer sbuf = new StringBuffer(512);
		for (Iterator<Card> i = hand_cards.iterator(); i.hasNext(); ) {
			Card next_card = i.next();
			sbuf.append(next_card.toString());
			if (i.hasNext()) {
				sbuf.append(", ");
			}
		}
		sbuf.append(" (");
		if (isBlackjack()) {
			sbuf.append("BJ");
		}
		else if (isBusted()) {
			sbuf.append(getLowValue());
		}
		else {
			sbuf.append(getHighValidValue());
		}
		sbuf.append(')');
		return sbuf.toString();
	}
}
