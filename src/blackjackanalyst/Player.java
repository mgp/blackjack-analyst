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

import java.util.ArrayList;
import java.util.List;

/**
 * A player at a blackjack table.
 * 
 * @author Michael Parker
 */
public class Player {
	private static int playerNum = 0;
	final String name;

	Table table;
	ArrayList<PlayerHand> bets;
	List<PlayerHand> betsReadOnly;
	int insuranceBet;

	PlayerStrategy strategy;
	int bankroll;

	private final ObserverList<PlayerObserver> playerObservers;

	/**
	 * Creates an unnamed blackjack player with the given strategy and the default
	 * bankroll of 500.
	 * 
	 * @param strategy the strategy the blackjack player will use
	 */
	public Player(PlayerStrategy strategy) {
		if (strategy == null) {
			throw new IllegalArgumentException("Player strategy can not be null");
		}
		name = "Player " + playerNum;
		++playerNum;

		table = null;
		bets = null;
		betsReadOnly = null;
		insuranceBet = 0;

		this.strategy = strategy;
		bankroll = 500;

		playerObservers = new ObserverList<PlayerObserver>();
	}

	/**
	 * Creates a named blackjack player with the given strategy and the default
	 * bankroll of 500.
	 * 
	 * @param strategy the strategy the blackjack player will use
	 * @param name the name of the player
	 */
	public Player(PlayerStrategy strategy, String name) {
		if (strategy == null) {
			throw new IllegalArgumentException("Player strategy can not be null");
		}
		if (name == null) {
			throw new IllegalArgumentException("Player name cannot be null");
		}
		++playerNum;
		this.name = name;

		table = null;
		bets = null;
		betsReadOnly = null;

		this.strategy = strategy;
		bankroll = 500;

		playerObservers = new ObserverList<PlayerObserver>();
	}

	/**
	 * Creates an unnamed blackjack player with the given strategy and bankroll.
	 * 
	 * @param strategy the strategy the blackjack player will use
	 * @param bankroll the starting bankroll of the player
	 */
	public Player(PlayerStrategy strategy, int bankroll) {
		if (strategy == null) {
			throw new IllegalArgumentException("Player strategy can not be null");
		}
		if (bankroll < 0) {
			throw new IllegalArgumentException("Bankroll can not be negative");
		}
		++playerNum;
		name = "Player " + playerNum;

		table = null;
		bets = null;
		betsReadOnly = null;

		this.strategy = strategy;
		this.bankroll = bankroll;

		playerObservers = new ObserverList<PlayerObserver>();
	}

	/**
	 * Creates a named blackjack player with the given strategy and bankroll.
	 * 
	 * @param strategy the strategy the blackjack player will use
	 * @param bankroll the starting bankroll of the player
	 * @param name the name of the player
	 */
	public Player(PlayerStrategy strategy, int bankroll, String name) {
		if (strategy == null) {
			throw new IllegalArgumentException("Player strategy can not be null");
		}
		if (bankroll < 0) {
			throw new IllegalArgumentException("Bankroll can not be negative");
		}
		if (name == null) {
			throw new IllegalArgumentException("Player name cannot be null");
		}
		++playerNum;
		this.name = name;

		table = null;
		bets = null;
		betsReadOnly = null;

		this.strategy = strategy;
		this.bankroll = bankroll;

		playerObservers = new ObserverList<PlayerObserver>();
	}

	/**
	 * Returns the table this player is playing blackjack at. If this player is
	 * not playing at any table, this method returns {@code null}.
	 * 
	 * @return the blackjack table of this player
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Sets the strategy of the player to the given strategy. The previous
	 * strategy of the player is returned.
	 * 
	 * @param newStrategy the new player strategy
	 * @return the previous player strategy
	 */
	public PlayerStrategy setStrategy(PlayerStrategy newStrategy) {
		if (newStrategy == null) {
			throw new IllegalArgumentException("Player strategy can not be null");
		}
		PlayerStrategy prevStrategy = strategy;
		strategy = newStrategy;
		return prevStrategy;
	}

	/**
	 * Returns the strategy used by the player.
	 * 
	 * @return the player strategy
	 */
	public PlayerStrategy getStrategy() {
		return strategy;
	}

	/**
	 * Sets the bankroll of the player to the given bankroll. The previous
	 * bankroll is returned.
	 * 
	 * @param newBankroll the new player bankroll
	 * @return the previous player bankroll
	 */
	public int setBankroll(int newBankroll) {
		if (newBankroll < 0) {
			throw new IllegalArgumentException("Bankroll can not be negative");
		}
		int prevBankroll = bankroll;
		bankroll = newBankroll;
		return prevBankroll;
	}

	/**
	 * Returns the bankroll of the player.
	 * 
	 * @return the player bankroll
	 */
	public int getBankroll() {
		return bankroll;
	}

	/**
	 * Returns the name of the player.
	 * 
	 * @return the player name
	 */
	public String getName() {
		return name;
	}

	/*
	 * Prints the name of the player, the player's bankroll, and the player's
	 * table name if any
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Player name=").append(getName());
		sb.append(", bankroll=").append(bankroll);
		if (table != null) {
			sb.append(", table=").append(table.getName());
		}
		return sb.toString();
	}

	/**
	 * Returns the list of observers, to which observers for this player scan be
	 * added, removed, and so forth.
	 * 
	 * @return the list of observers for this player
	 */
	public ObserverList<PlayerObserver> getObservers() {
		return playerObservers;
	}

	/*
	 * Dispatch of player observer methods.
	 */

	protected void playerJoins(Table table) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerJoins(table);
		}
	}

	protected void playerLeaves(Table table) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerLeaves(table);
		}
	}

	protected void playerBets(int betAmount, int bankroll) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerBets(betAmount, bankroll);
		}
	}

	protected void playerInsures(int betAmount, int bankroll) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerInsures(betAmount, bankroll);
		}
	}

	protected void playerDealt(PlayerHand hand) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerDealt(hand);
		}
	}

	protected void playerDraws(Card card, PlayerHand newHand) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerDraws(card, newHand);
		}
	}

	protected void playerStands(PlayerHand hand) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerStands(hand);
		}
	}

	protected void playerBusts(PlayerHand hand, int amountLost, int newBankroll) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerBusts(hand, amountLost, newBankroll);
		}
	}

	protected void playerSplits(PlayerHand hand) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerSplits(hand);
		}
	}

	protected void playerDoublesDown(Card card, PlayerHand newHand) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerDoublesDown(card, newHand);
		}
	}

	protected void playerWins(PlayerHand hand, int amountWon, int newBankroll) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerWins(hand, amountWon, newBankroll);
		}
	}

	protected void playerLoses(PlayerHand hand, int amountLost, int newBankroll) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerLoses(hand, amountLost, newBankroll);
		}
	}

	protected void playerBlackjack(PlayerHand hand, int amountWon, int newBankroll) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerBlackjack(hand, amountWon, newBankroll);
		}
	}

	protected void playerPush(PlayerHand hand, int heldBankroll) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerPush(hand, heldBankroll);
		}
	}

	protected void playerWinsInsurance(int amountWon, int newBankroll) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerWinsInsurance(amountWon, newBankroll);
		}
	}

	protected void playerLosesInsurance(int amountLost, int newBankroll) {
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.playerLosesInsurance(amountLost, newBankroll);
		}
	}
}
