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
	 * 
	 * @author Michael Parker
	 */
	public static enum Suit {
		HEARTS, DIAMONDS, SPADES, CLUBS
	};

	/**
	 * The rank of a card.
	 * 
	 * @author Michael Parker
	 */
	public static enum Rank {
		TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
	};

	protected static Suit[] all_suits = {
			Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES
	};

	protected static CardRank[] all_ranks = {
			new CardRank(Rank.ACE, 1), new CardRank(Rank.TWO, 2),
			new CardRank(Rank.THREE, 3), new CardRank(Rank.FOUR, 4),
			new CardRank(Rank.FIVE, 5), new CardRank(Rank.SIX, 6),
			new CardRank(Rank.SEVEN, 7), new CardRank(Rank.EIGHT, 8),
			new CardRank(Rank.NINE, 9), new CardRank(Rank.TEN, 10),
			new CardRank(Rank.JACK, 10), new CardRank(Rank.QUEEN, 10),
			new CardRank(Rank.KING, 10)
	};

	protected static class CardRank {
		protected final Rank rank_enum;
		protected final int value;

		protected CardRank(Rank _card_rank, int _value) {
			rank_enum = _card_rank;
			value = _value;
		}

		public String toString() {
			return rank_enum.toString();
		}
	}

	protected static Suit getCardSuit(int card_id) {
		return all_suits[card_id / CARD_RANKS];
	}

	protected static CardRank getCardRank(int card_id) {
		return all_ranks[card_id % CARD_RANKS];
	}

	/**
	 * Returns the <code>Card</code> object having the given suit and rank.
	 * 
	 * @param _card_rank
	 * the rank of the card to retrieve
	 * @param _card_suit
	 * the suit of the card to retrieve
	 * @return the <code>Card</code> object having the given rank and suit
	 */
	public static Card getCard(Rank _card_rank, Suit _card_suit) {
		if ((_card_rank == null) || (_card_suit == null)) {
			throw new NullPointerException("Specified rank or suit is null");
		}

		int card_id = _card_rank.ordinal() + CARD_SUITS * _card_suit.ordinal();
		if (all_cards == null) {
			all_cards = new Card[CARDS_PER_DECK];
		}
		if (all_cards[card_id] == null) {
			all_cards[card_id] = new Card(getCardRank(card_id), _card_suit,
					card_id);
		}
		return all_cards[card_id];
	}

	/**
	 * Returns the <code>Card</code> having the given unique numerical
	 * identifier.
	 * 
	 * @param card_id
	 * the numerical identifier of the card
	 * @return the card having the given numerical identifier
	 */
	public static Card getCard(int card_id) {
		if ((card_id < 0) || (card_id >= CARDS_PER_DECK)) {
			throw new IllegalArgumentException(
					"Card identifier is not in range");
		}

		if (all_cards == null) {
			all_cards = new Card[CARDS_PER_DECK];
		}
		if (all_cards[card_id] == null) {
			all_cards[card_id] = new Card(getCardRank(card_id),
					getCardSuit(card_id), card_id);
		}
		return all_cards[card_id];
	}

	protected static Card[] all_cards;

	protected final CardRank card_rank;
	protected final Suit suit_enum;
	protected final int card_id;

	protected Card(CardRank _card_rank, Suit _card_suit, int _card_id) {
		card_rank = _card_rank;
		suit_enum = _card_suit;
		card_id = _card_id;
	}

	/**
	 * Returns the rank of this card.
	 * 
	 * @return the card rank
	 */
	public final Rank getRank() {
		return card_rank.rank_enum;
	}

	/**
	 * Returns the suit of this card.
	 * 
	 * @return the card suit
	 */
	public final Suit getSuit() {
		return suit_enum;
	}

	/**
	 * Returns the value of the card. If the rank of the card is an ace, this
	 * method returns <code>1</code>.
	 * 
	 * @return the value of the card
	 */
	public int getValue() {
		return card_rank.value;
	}

	/**
	 * Returns whether this card is an ace.
	 * 
	 * @return <code>true</code> if this card is an ace, <code>false</code>
	 * otherwise
	 */
	public boolean isAce() {
		return (card_rank.rank_enum == Rank.ACE);
	}

	/**
	 * Returns the unique numerical identifier of this card.
	 * 
	 * @return the identifying card number
	 */
	public int getID() {
		return card_id;
	}

	public String toString() {
		StringBuffer sbuf = new StringBuffer(128);
		sbuf.append(card_rank).append(" of ").append(suit_enum);
		return sbuf.toString();
	}
}
