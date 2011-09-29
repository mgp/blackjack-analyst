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

/**
 * A shoe, containing an integer number of card decks.
 * 
 * @author Michael Parker
 */
class Shoe {
	protected final int num_decks;
	protected final Card[] shoe_cards;

	protected int next_card;
	protected int shuffle_mark;

	/**
	 * Creates a shoe with the given number of decks. The number of decks
	 * specified must be at least <code>6</code>.
	 * 
	 * @param _num_decks
	 * the number of decks of cards in the shoe
	 */
	public Shoe(int _num_decks) {
		if (_num_decks < 6) {
			throw new IllegalArgumentException(
					"Shoe must contain at least 6 decks");
		}

		num_decks = _num_decks;
		shoe_cards = new Card[num_decks * Card.CARDS_PER_DECK];
		for (int i = 0; i < shoe_cards.length; ++i) {
			int card_id = i % Card.CARDS_PER_DECK;
			shoe_cards[i] = Card.getCard(card_id);
		}

		shuffle();
	}

	/**
	 * Returns whether the shoe is empty. This method returns <code>true</code>
	 * if and only if method <code>getCardsLeft</code> returns <code>0</code>.
	 * 
	 * @return <code>true</code> if there are no more cards left in the deck,
	 * <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return (next_card == shoe_cards.length);
	}

	/**
	 * Returns the number of cards left in the shoe.
	 * 
	 * @return the number of cards left in the shoe
	 */
	public int getCardsLeft() {
		return (shoe_cards.length - next_card);
	}

	/**
	 * Returns whether this shoe needs its cards shuffled. This occurs when the
	 * remaining number of cards crosses a threshold between two and three
	 * decks.
	 * 
	 * @return <code>true</code> if the shoe needs shuffling,
	 * <code>false</code> otherwise
	 */
	public boolean needsShuffle() {
		return (getCardsLeft() < shuffle_mark);
	}

	/**
	 * Returns the number of decks in the shoe.
	 * 
	 * @return the number of decks in the shoe
	 */
	public final int getNumDecks() {
		return num_decks;
	}

	/**
	 * Returns all cards withdrawn from the shoe, and then shuffles the shoe.
	 */
	public void shuffle() {
		next_card = 0;

		MersenneTwister rng = MersenneTwister.getInstance();
		for (int i = 0; i < shoe_cards.length; ++i) {
			int swap_index = i + rng.nextInt(shoe_cards.length - i);
			if (swap_index > i) {
				Card temp = shoe_cards[swap_index];
				shoe_cards[swap_index] = shoe_cards[i];
				shoe_cards[i] = temp;
			}
		}
		shuffle_mark = 2 * Card.CARDS_PER_DECK
				+ rng.nextInt(Card.CARDS_PER_DECK);
	}

	/**
	 * Returns the next card from the shoe. If the shoe is empty, this method
	 * returns <code>null</code>.
	 * 
	 * @return the next card from the shoe
	 */
	public Card getNextCard() {
		return isEmpty() ? null : shoe_cards[next_card++];
	}
}
