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

import java.util.List;

/**
 * A hand of cards belonging to a player.
 * 
 * @author Michael Parker
 */
public class PlayerHand extends Hand {
	protected int betAmount;
	protected boolean beenSplit;
	protected final Player player;

	protected PlayerHand(int betAmount, boolean beenSplit, Player player) {
		super();
		this.betAmount = betAmount;
		this.beenSplit = beenSplit;
		this.player = player;
	}

	protected PlayerHand(int betAmount, boolean beenSplit, Player player,
	    Card firstCard) {
		super(firstCard);
		this.betAmount = betAmount;
		this.beenSplit = beenSplit;
		this.player = player;
	}

	protected PlayerHand(int betAmount, boolean beenSplit, Player player,
	    Card firstCard, Card secondCard) {
		super(firstCard, secondCard);
		this.betAmount = betAmount;
		this.beenSplit = beenSplit;
		this.player = player;
	}

	/**
	 * Returns whether the hand is a pair, meaning it consists of two cards with
	 * the same value. If a hand consists of a pair, it can be split.
	 * 
	 * @return {@code true} if the hand is a pair, {@code false} otherwise
	 */
	public boolean isPair() {
		return ((cards.size() == 2) && (cards.get(0).getValue() == cards.get(1)
		    .getValue()));
	}

	/**
	 * Returns whether the hand has previously been split. Hands that have
	 * previously been split cannot receive a blackjack.
	 * 
	 * @return {@code true} if the hand has previously been split, {@code false}
	 *         otherwise
	 */
	public boolean beenSplit() {
		return beenSplit;
	}

	/**
	 * Returns the name of the player to which this hand belongs.
	 * 
	 * @return the player name
	 */
	public String getName() {
		return player.name;
	}

	/**
	 * Returns the bankroll of the player to which this hand belongs.
	 * 
	 * @return the player bankroll
	 */
	public int getBankroll() {
		return player.bankroll;
	}

	/**
	 * Returns the amount bet on this hand.
	 * 
	 * @return the amount bet
	 */
	public int getBetAmount() {
		return betAmount;
	}

	/**
	 * Returns the unique identifier of this bet for the round. The first bet made
	 * by the player this round returns {@code 0}; each successive bet created by
	 * splitting increments the previous bet number.
	 * 
	 * @return the unique identifier of this bet
	 */
	public int getBetNumber() {
		int betNum = 0;
		for (PlayerHand hand : player.bets) {
			if (hand == this) {
				return betNum;
			}
			++betNum;
		}
		return -1;
	}

	/**
	 * Returns all bets belonging to the player this round. This
	 * {@link PlayerHand} object is found at the index returned by method
	 * {@link #getBetNumber()}.
	 * 
	 * @return all bets this round
	 */
	public List<PlayerHand> getBets() {
		return player.betsReadOnly;
	}

	protected PlayerHand makeSplit() {
		// adjust this hand since now split
		Card cardLeft = cards.get(0);
		handValues.clear();
		if (cardLeft.isAce()) {
			handValues.add(1);
			handValues.add(11);
			highValidValue = 11;
		} else {
			int cardValue = cardLeft.getValue();
			handValues.add(cardValue);
			highValidValue = cardValue;
		}
		beenSplit = true;

		// create and return new hand
		Card splitCard = cards.remove(1);
		return new PlayerHand(betAmount, true, player, splitCard);
	}

	/**
	 * Returns whether the current hand is a blackjack. This method returns
	 * {@code true} if the hand is an ace and a ten-valued card, and the hand has
	 * not previously been split or the split card was not an ace.
	 * 
	 * @return {@code true} if the hand is a blackjack, {@code false} otherwise
	 */
	public boolean isBlackjack() {
		// blackjack only if hand has not been split or split card is not an ace
		return (super.isBlackjack() && (!beenSplit || !cards.get(0).isAce()));
	}
}
