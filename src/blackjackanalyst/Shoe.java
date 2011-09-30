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
	private final int numDecks;
	private final Card[] shoeCards;

	private int nextCard;
	private int shuffleMark;

	/**
	 * Creates a shoe with the given number of decks. The number of decks
	 * specified must be at least {@code 6}.
	 * 
	 * @param numDecks the number of decks of cards in the shoe
	 */
	public Shoe(int numDecks) {
		if (numDecks < 6) {
			throw new IllegalArgumentException("Shoe must contain at least 6 decks");
		}

		this.numDecks = numDecks;
		shoeCards = new Card[numDecks * Card.CARDS_PER_DECK];
		for (int i = 0; i < shoeCards.length; ++i) {
			int cardId = i % Card.CARDS_PER_DECK;
			shoeCards[i] = Card.getCard(cardId);
		}

		shuffle();
	}

	/**
	 * Returns whether the shoe is empty, meaning {@link #getCardsLeft()} returns
	 * {@code 0}.
	 * 
	 * @return {@code true} if there are no more cards left in the shoe,
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return (nextCard == shoeCards.length);
	}

	/**
	 * Returns the number of cards left in the shoe.
	 * 
	 * @return the number of cards left in the shoe
	 */
	public int getCardsLeft() {
		return (shoeCards.length - nextCard);
	}

	/**
	 * Returns whether this shoe needs its cards shuffled. This occurs when the
	 * remaining number of cards crosses a threshold between two and three decks.
	 * 
	 * @return {@code true} if the shoe needs shuffling, {@code false} otherwise
	 */
	public boolean needsShuffle() {
		return (getCardsLeft() < shuffleMark);
	}

	/**
	 * Returns the number of decks in the shoe.
	 * 
	 * @return the number of decks in the shoe
	 */
	public final int getNumDecks() {
		return numDecks;
	}

	/**
	 * Returns all cards withdrawn from the shoe, and then shuffles the shoe.
	 */
	public void shuffle() {
		nextCard = 0;

		MersenneTwister rng = MersenneTwister.getInstance();
		for (int i = 0; i < shoeCards.length; ++i) {
			int swapIndex = i + rng.nextInt(shoeCards.length - i);
			if (swapIndex > i) {
				Card temp = shoeCards[swapIndex];
				shoeCards[swapIndex] = shoeCards[i];
				shoeCards[i] = temp;
			}
		}
		shuffleMark = 2 * Card.CARDS_PER_DECK + rng.nextInt(Card.CARDS_PER_DECK);
	}

	/**
	 * Returns the next card from the shoe. If the shoe is empty, this method
	 * returns {@code null}.
	 * 
	 * @return the next card from the shoe
	 */
	public Card getNextCard() {
		return isEmpty() ? null : shoeCards[nextCard++];
	}
}
