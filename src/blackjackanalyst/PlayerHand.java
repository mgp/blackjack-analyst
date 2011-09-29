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

import java.util.Iterator;
import java.util.List;

/**
 * A hand of cards belonging to a player.
 * 
 * @author Michael Parker
 */
public class PlayerHand extends Hand {
	protected int bet_amount;
	protected boolean been_split;
	protected final Player p;
	
	protected PlayerHand(int _bet_amount, boolean _from_split, Player _p) {
		super();
		bet_amount = _bet_amount;
		been_split = _from_split;
		p = _p;
	}

	protected PlayerHand(int _bet_amount, boolean _from_split, Player _p,
			Card _first) {
		super(_first);
		bet_amount = _bet_amount;
		been_split = _from_split;
		p = _p;
	}

	protected PlayerHand(int _bet_amount, boolean _from_split, Player _p,
			Card _first, Card _second) {
		super(_first, _second);
		bet_amount = _bet_amount;
		been_split = _from_split;
		p = _p;
	}

	/**
	 * Returns whether the hand is a pair, meaning it consists of two cards with
	 * the same value. If a hand consists of a pair, it can be split.
	 * 
	 * @return <code>true</code> if the hand is a pair, <code>false</code>
	 * otherwise
	 */
	public boolean isPair() {
		return ((hand_cards.size() == 2) && 
				(hand_cards.get(0).getValue() == hand_cards.get(1).getValue()));
	}

	/**
	 * Returns whether the hand has previously been split. Hands that have
	 * previously been split cannot receive a blackjack.
	 * 
	 * @return <code>true</code> if the hand has previously been split,
	 * <code>false</code> otherwise
	 */
	public boolean beenSplit() {
		return been_split;
	}

	/**
	 * Returns the name of the player to which this hand belongs.
	 * 
	 * @return the player name
	 */
	public String getName() {
		return p.name;
	}

	/**
	 * Returns the bankroll of the player to which this hand belongs.
	 * 
	 * @return the player bankroll
	 */
	public int getBankroll() {
		return p.bankroll;
	}

	/**
	 * Returns the amount bet on this hand.
	 * 
	 * @return the amount bet
	 */
	public int getBetAmount() {
		return bet_amount;
	}

	/**
	 * Returns the unique identifier of this bet for the round. The first bet
	 * made by the player this round returns <code>0</code>; each successive
	 * bet created by splitting increments the previous bet number.
	 * 
	 * @return the unique identifier of this bet
	 */
	public int getBetNumber() {
		int bet_num = 0;
		for (Iterator<PlayerHand> i = p.bets.iterator(); i.hasNext(); ++bet_num) {
			PlayerHand curr_hand = i.next();
			if (curr_hand == this) {
				return bet_num;
			}
		}
		return -1;
	}

	/**
	 * Returns all bets belonging to the player this round. This
	 * <code>PlayerHand</code> object is found at the index returned by method
	 * <code>getBetnumber</code>.
	 * 
	 * @return all bets this round
	 */
	public List<PlayerHand> getBets() {
		return p.bets_ro;
	}

	protected PlayerHand makeSplit() {
		// adjust this hand since now split
		Card card_left = hand_cards.get(0);
		hand_values.clear();
		if (card_left.isAce()) {
			hand_values.add(1);
			hand_values.add(11);
			max_valid = 11;
		}
		else {
			int card_value = card_left.getValue();
			hand_values.add(card_value);
			max_valid = card_value;
		}
		been_split = true;

		// create and return new hand
		Card split_card = hand_cards.remove(1);
		return new PlayerHand(bet_amount, true, p, split_card);
	}

	/**
	 * Returns whether the current hand is a blackjack. This method returns
	 * <code>true</code> if the hand is an ace and a ten-valued card, and the
	 * hand has not previously been split or the split card was not an ace.
	 * 
	 * @return <code>true</code> if the hand is a blackjack,
	 * <code>false</code> otherwise
	 */
	public boolean isBlackjack() {
		// blackjack only if hand has not been split or split card is not an ace
		return (super.isBlackjack() && (!been_split || !hand_cards.get(0).isAce()));
	}
}
