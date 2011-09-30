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

import blackjackanalyst.Card;
import blackjackanalyst.PlayerHand;
import blackjackanalyst.PlayerObserver;
import blackjackanalyst.Table;

/**
 * An abstract class that defines an empty body for each method of interface
 * {@link PlayerObserver}. Concrete implementations that are only interested in
 * select methods of the interface can extend this class for clarity and
 * convenience.
 * 
 * @author Michael Parker
 */
public abstract class AbstractPlayerObserver implements PlayerObserver {
	public void playerJoins(Table table) {
		// A concrete subclass can override this method.
	}

	public void playerLeaves(Table table) {
		// A concrete subclass can override this method.
	}

	public void playerBets(int betAmount, int bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerInsures(int betAmount, int bankroll) {
		// A concrete subclass can override this method.
	}

	public void playerDealt(PlayerHand hand) {
		// A concrete subclass can override this method.
	}

	public void playerDraws(Card dealtCard, PlayerHand hand) {
		// A concrete subclass can override this method.
	}

	public void playerStands(PlayerHand hand) {
		// A concrete subclass can override this method.
	}

	public void playerBusts(PlayerHand hand, int amountLost, int newBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerSplits(PlayerHand hand) {
		// A concrete subclass can override this method.
	}

	public void playerDoublesDown(Card dealtCard, PlayerHand newHand) {
		// A concrete subclass can override this method.
	}

	public void playerWins(PlayerHand hand, int amountWon, int newBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerLoses(PlayerHand hand, int amountLost, int newBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerBlackjack(PlayerHand hand, int amountWon, int newBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerPush(PlayerHand hand, int heldBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerWinsInsurance(int amountWon, int newBankroll) {
		// A concrete subclass can override this method.
	}

	public void playerLosesInsurance(int amountLost, int newBankroll) {
		// A concrete subclass can override this method.
	}
}
