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
 * A card, specified by its rank and suit.
 * 
 * @author Michael Parker
 */
public class Card {
	/**
	 * The number of card suits.
	 */
	public static final int NUM_CARD_SUITS = 4;

	/**
	 * The number of card ranks.
	 */
	public static final int NUM_CARD_RANKS = 13;

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

	private static RankValue[] RANK_VALUES = {
	    new RankValue(Rank.TWO, 2), new RankValue(Rank.THREE, 3),
	    new RankValue(Rank.FOUR, 4), new RankValue(Rank.FIVE, 5),
	    new RankValue(Rank.SIX, 6), new RankValue(Rank.SEVEN, 7),
	    new RankValue(Rank.EIGHT, 8), new RankValue(Rank.NINE, 9),
	    new RankValue(Rank.TEN, 10), new RankValue(Rank.JACK, 10),
	    new RankValue(Rank.QUEEN, 10), new RankValue(Rank.KING, 10),
	    new RankValue(Rank.ACE, 1)
	};

	private static class RankValue {
		protected final Rank rank;
		protected final int value;

		protected RankValue(Rank rank, int value) {
			this.rank = rank;
			this.value = value;
		}

		public String toString() {
			return rank.toString();
		}
	}

	private static RankValue getRankValue(int cardId) {
		return RANK_VALUES[cardId % NUM_CARD_RANKS];
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

		int cardId = cardRank.ordinal() + NUM_CARD_RANKS * cardSuit.ordinal();
		if (ALL_CARDS == null) {
			ALL_CARDS = new Card[CARDS_PER_DECK];
		}
		if (ALL_CARDS[cardId] == null) {
			ALL_CARDS[cardId] = new Card(getRankValue(cardId), cardSuit, cardId);
		}
		return ALL_CARDS[cardId];
	}
	
	private static Card[] ALL_CARDS;

	private final RankValue rankValue;
	private final Suit suit;
	private final int cardId;

	private Card(RankValue rankValue, Suit suit, int cardId) {
		this.rankValue = rankValue;
		this.suit = suit;
		this.cardId = cardId;
	}

	/**
	 * Returns the rank of this card.
	 * 
	 * @return the card rank
	 */
	public final Rank getRank() {
		return rankValue.rank;
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
		return rankValue.value;
	}

	/**
	 * Returns whether this card is an ace.
	 * 
	 * @return {@code true} if this card is an ace, {@code false} otherwise
	 */
	public boolean isAce() {
		return (rankValue.rank == Rank.ACE);
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
		StringBuilder sb = new StringBuilder(128);
		sb.append(rankValue).append(" of ").append(suit);
		return sb.toString();
	}
}
