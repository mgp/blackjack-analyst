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

/**
 * A card, specified by its rank and suit.
 * 
 * @author Michael Parker
 */
public class Card {
	/**
	 * The number of card suits.
	 */
	public static final int CARD_SUITS = 4;

	/**
	 * The number of card ranks.
	 */
	public static final int CARD_RANKS = 13;

	/**
	 * The number of cards per deck.
	 */
	public static final int CARDS_PER_DECK = 52;

	/**
	 * The suit of a card.
	 */
	public static enum Suit {
		HEARTS, DIAMONDS, SPADES, CLUBS
	};

	/**
	 * The rank of a card.
	 */
	public static enum Rank {
		TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
	};

	protected static Suit[] ALL_SUITS = { Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS,
	    Suit.SPADES };

	private static CardRank[] ALL_RANKS = { new CardRank(Rank.ACE, 1),
	    new CardRank(Rank.TWO, 2), new CardRank(Rank.THREE, 3),
	    new CardRank(Rank.FOUR, 4), new CardRank(Rank.FIVE, 5),
	    new CardRank(Rank.SIX, 6), new CardRank(Rank.SEVEN, 7),
	    new CardRank(Rank.EIGHT, 8), new CardRank(Rank.NINE, 9),
	    new CardRank(Rank.TEN, 10), new CardRank(Rank.JACK, 10),
	    new CardRank(Rank.QUEEN, 10), new CardRank(Rank.KING, 10) };

	private static class CardRank {
		protected final Rank rank;
		protected final int value;

		protected CardRank(Rank rank, int value) {
			this.rank = rank;
			this.value = value;
		}

		public String toString() {
			return rank.toString();
		}
	}

	private static Suit getCardSuit(int cardId) {
		return ALL_SUITS[cardId / CARD_RANKS];
	}

	private static CardRank getCardRank(int cardId) {
		return ALL_RANKS[cardId % CARD_RANKS];
	}

	/**
	 * Returns the {@link Card} object having the given suit and rank.
	 * 
	 * @param cardRank the rank of the card to retrieve
	 * @param cardSuit the suit of the card to retrieve
	 * @return the {@link Card} object having the given rank and suit
	 */
	public static Card getCard(Rank cardRank, Suit cardSuit) {
		if ((cardRank == null) || (cardSuit == null)) {
			throw new NullPointerException("Specified rank or suit is null");
		}

		int cardId = cardRank.ordinal() + CARD_SUITS * cardSuit.ordinal();
		if (ALL_CARDS == null) {
			ALL_CARDS = new Card[CARDS_PER_DECK];
		}
		if (ALL_CARDS[cardId] == null) {
			ALL_CARDS[cardId] = new Card(getCardRank(cardId), cardSuit, cardId);
		}
		return ALL_CARDS[cardId];
	}

	/**
	 * Returns the {@link Card} having the given unique numerical identifier.
	 * 
	 * @param card_id the numerical identifier of the card
	 * @return the {@link Card} object having the given numerical identifier
	 */
	public static Card getCard(int card_id) {
		if ((card_id < 0) || (card_id >= CARDS_PER_DECK)) {
			throw new IllegalArgumentException("Card identifier is not in range");
		}

		if (ALL_CARDS == null) {
			ALL_CARDS = new Card[CARDS_PER_DECK];
		}
		if (ALL_CARDS[card_id] == null) {
			ALL_CARDS[card_id] = new Card(getCardRank(card_id), getCardSuit(card_id),
			    card_id);
		}
		return ALL_CARDS[card_id];
	}

	private static Card[] ALL_CARDS;

	private final CardRank cardRank;
	private final Suit suit;
	private final int cardId;

	private Card(CardRank cardRank, Suit suit, int cardId) {
		this.cardRank = cardRank;
		this.suit = suit;
		this.cardId = cardId;
	}

	/**
	 * Returns the rank of this card.
	 * 
	 * @return the card rank
	 */
	public final Rank getRank() {
		return cardRank.rank;
	}

	/**
	 * Returns the suit of this card.
	 * 
	 * @return the card suit
	 */
	public final Suit getSuit() {
		return suit;
	}

	/**
	 * Returns the value of the card. If the rank of the card is an ace, this
	 * method returns {@code 1}.
	 * 
	 * @return the value of the card
	 */
	public int getValue() {
		return cardRank.value;
	}

	/**
	 * Returns whether this card is an ace.
	 * 
	 * @return {@code true} if this card is an ace, {@code false} otherwise
	 */
	public boolean isAce() {
		return (cardRank.rank == Rank.ACE);
	}

	/**
	 * Returns the unique numerical identifier of this card.
	 * 
	 * @return the identifying card number
	 */
	public int getID() {
		return cardId;
	}

	public String toString() {
		StringBuilder sbuf = new StringBuilder(128);
		sbuf.append(cardRank).append(" of ").append(suit);
		return sbuf.toString();
	}
}
