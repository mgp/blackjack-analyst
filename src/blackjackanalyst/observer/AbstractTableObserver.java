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

package blackjackanalyst.observer;

import java.util.List;

import blackjackanalyst.Card;
import blackjackanalyst.Hand;
import blackjackanalyst.Player;
import blackjackanalyst.PlayerHand;
import blackjackanalyst.TableObserver;

/**
 * An abstract class that defines an empty body for each method of interface
 * {@link TableObserver}. Concrete implementations that are only interested in
 * select methods of the interface can extend this class for clarity and
 * convenience.
 * 
 * @author Michael Parker
 */
public abstract class AbstractTableObserver implements TableObserver {
	public void newRound(List<Player> players) {
		// A concrete subclass can override this method.
	}

	public void shoeShuffled() {
		// A concrete subclass can override this method.
	}

	public void dealerDealt(Card upCard) {
		// A concrete subclass can override this method.
	}

	public void dealerDealt(Card downCard, Hand hand) {
		// A concrete subclass can override this method.
	}

	public void dealerBlackjack(Hand hand) {
		// A concrete subclass can override this method.
	}

	public void dealerDraws(Card card, Hand hand) {
		// A concrete subclass can override this method.
	}

	public void dealerStands(Hand hand) {
		// A concrete subclass can override this method.
	}

	public void dealerBusts(Hand hand) {
		// A concrete subclass can override this method.
	}

	public void playerJoins(Player player) {
		// A concrete subclass can override this method.
	}

	public void playerLeaves(Player player) {
		// A concrete subclass can override this method.
	}

	public void playerBets(Player betting_player, int betAmount, int bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerInsures(Player betting_player, int betAmount, int bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerDealt(Player player, PlayerHand hand) {
		// A concrete subclass can override this method.
	}

	public void playerDraws(Player player, Card card, PlayerHand hand) {
		// A concrete subclass can override this method.
	}

	public void playerStands(Player player, PlayerHand hand) {
		// A concrete subclass can override this method.
	}

	public void playerBusts(Player player, PlayerHand hand, int amountLost,
	    int newBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerSplits(Player player, PlayerHand hand) {
		// A concrete subclass can override this method.
	}

	public void playerDoublesDown(Player player, Card card, PlayerHand hand) {
		// A concrete subclass can override this method.
	}

	public void playerWins(Player player, PlayerHand hand, int amountWon,
	    int newBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerLoses(Player player, PlayerHand hand, int amountLost,
	    int newBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerBlackjack(Player player, PlayerHand hand, int amountWon,
	    int newBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerPush(Player player, PlayerHand hand, int bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerWinsInsurance(Player player, int amountWon, int newBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerLosesInsurance(Player player, int amountLost,
	    int newBankroll) {
		// A concrete subclass can override this method.
	}
}
