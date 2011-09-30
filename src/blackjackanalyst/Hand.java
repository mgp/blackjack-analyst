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
	protected final List<Card> cards;
	protected final List<Card> cardsReadOnly;
	protected boolean finished;

	protected final TreeSet<Integer> handValues;
	protected final Set<Integer> handValuesReadOnly;
	protected int highValidValue;

	protected Hand() {
		cards = new LinkedList<Card>();
		cardsReadOnly = Collections.unmodifiableList(cards);

		handValues = new TreeSet<Integer>();
		handValuesReadOnly = Collections.unmodifiableSet(handValues);

		highValidValue = 0;
	}

	/**
	 * Creates a new hand containing the given card.
	 * 
	 * @param firstCard the card to add to the new hand
	 */
	protected Hand(Card firstCard) {
		this();
		// add first card to hand
		add(firstCard);
	}

	/**
	 * Creates a new hand containing the given cards.
	 * 
	 * @param firstCard the card to add to the new hand first
	 * @param secondCard the card to add to the new hand second
	 */
	protected Hand(Card firstCard, Card secondCard) {
		this();
		// add both cards to hand
		add(firstCard);
		add(secondCard);
	}

	/**
	 * Returns a list containing the cards in the hand, appearing in the order in
	 * which they were added. The returned list may not be modified.
	 * 
	 * @return the list of cards in the hand
	 */
	public List<Card> getCards() {
		return cardsReadOnly;
	}

	/**
	 * Returns the highest possible value that can be made from the hand, but does
	 * not exceed 21. If no such value exists, meaning that this hand has busted,
	 * this method returns {@code -1}.
	 * 
	 * @return the highest possible hand value not exceeding 21
	 */
	public int getHighValidValue() {
		return highValidValue;
	}

	/**
	 * Returns the lowest possible value that can be made from the hand. This
	 * value may exceed 21.
	 * 
	 * @return the lowest possible hand value
	 */
	public int getLowValue() {
		return handValues.first();
	}

	/**
	 * Returns the highest possible value that can be made from the hand. This
	 * value may exceed 21.
	 * 
	 * @return the highest possible hand value
	 */
	public int getHighValue() {
		return handValues.last();
	}

	/**
	 * Returns all possible values that can be made from the hand, where values
	 * appear in ascending order.
	 * 
	 * @return all possible hand values
	 */
	public Set<Integer> getValues() {
		return handValuesReadOnly;
	}

	/**
	 * Returns whether the hand has busted, meaning that the smallest possible
	 * hand value is greater than 21.
	 * 
	 * @return {@code true} if the hand has busted, {@code false} otherwise
	 */
	public boolean isBusted() {
		return (getLowValue() > 21);
	}

	/**
	 * Returns whether the current hand is a blackjack, which is an ace and a
	 * ten-valued card.
	 * 
	 * @return {@code true} if the hand is a blackjack, {@code false} otherwise
	 */
	public boolean isBlackjack() {
		return ((cards.size() == 2) && (getHighValidValue() == 21));
	}

	/**
	 * Returns whether the hand is soft, meaning that it has not busted and
	 * whether the highest value that does not exceed 21 contains an ace valued as
	 * 11.
	 * 
	 * @return {@code true} if the hand is soft, {@code false} otherwise
	 */
	public boolean isSoft() {
		return (!isBusted() && (getLowValue() <= (getHighValidValue() - 10)));
	}

	/**
	 * Returns whether this hand is finished. The hand is finished if the player
	 * or dealer has stood or busted, or if the player has doubled down and
	 * received his or her final card.
	 * 
	 * @return {@code true} if the hand is finished, {@code false} otherwise
	 */
	public boolean isFinished() {
		return finished;
	}

	protected final void add(Card card) {
		if (cards.isEmpty()) {
			// add first card dealt to hand
			cards.add(card);
			if (card.isAce()) {
				// add both values for ace
				handValues.add(1);
				handValues.add(11);
				highValidValue = 11;
			} else {
				// not ace, add single value
				int cardValue = card.getValue();
				handValues.add(cardValue);
				highValidValue = cardValue;
			}
			return;
		}

		// update all possible hand values
		highValidValue = -1;
		TreeSet<Integer> newValues = new TreeSet<Integer>();
		for (Iterator<Integer> i = handValues.iterator(); i.hasNext();) {
			int currValue = i.next();
			int newValue;
			if (card.isAce()) {
				// update low value for ace
				newValue = currValue + 1;
				newValues.add(newValue);
				if ((newValue <= 21) && (newValue > highValidValue)) {
					highValidValue = newValue;
				}
				// update high value for ace
				newValue = currValue + 11;
				newValues.add(newValue);
				if ((newValue <= 21) && (newValue > highValidValue)) {
					highValidValue = newValue;
				}
			} else {
				// not ace, update single value
				newValue = currValue + card.getValue();
				newValues.add(newValue);
				if ((newValue <= 21) && (newValue > highValidValue)) {
					highValidValue = newValue;
				}
			}
		}
		handValues.clear();
		handValues.addAll(newValues);
		// add card to hand
		cards.add(card);
	}

	public String toString() {
		StringBuilder sbuf = new StringBuilder(512);
		for (Iterator<Card> i = cards.iterator(); i.hasNext();) {
			Card next_card = i.next();
			sbuf.append(next_card.toString());
			if (i.hasNext()) {
				sbuf.append(", ");
			}
		}
		sbuf.append(" (");
		if (isBlackjack()) {
			sbuf.append("Blackjack");
		} else if (isBusted()) {
			sbuf.append(getLowValue());
		} else {
			sbuf.append(getHighValidValue());
		}
		sbuf.append(')');
		return sbuf.toString();
	}
}
