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
import java.util.Iterator;
import java.util.List;

/**
 * A player at a blackjack table.
 * 
 * @author Michael Parker
 */
public class Player {
	protected static int player_num = 0;
	protected final String name;

	protected Table bj_table;
	protected ArrayList<PlayerHand> bets;
	protected List<PlayerHand> bets_ro;
	protected int insurance_bet;

	protected PlayerStrategy bj_strategy;
	protected int bankroll;

	protected final ObserverList<PlayerObserver> player_obs;

	/**
	 * Creates an unnamed blackjack player with the given strategy and the
	 * default bankroll of 500.
	 * 
	 * @param _bj_strategy
	 * the strategy the blackjack player will use
	 */
	public Player(PlayerStrategy _bj_strategy) {
		if (_bj_strategy == null) {
			throw new IllegalArgumentException(
					"Player strategy can not be null");
		}
		name = "Player " + player_num;
		++player_num;

		bj_table = null;
		bets = null;
		bets_ro = null;
		insurance_bet = 0;

		bj_strategy = _bj_strategy;
		bankroll = 500;

		player_obs = new ObserverList<PlayerObserver>();
	}

	/**
	 * Creates a named blackjack player with the given strategy and the default
	 * bankroll of 500.
	 * 
	 * @param _bj_strategy
	 * the strategy the blackjack player will use
	 * @param player_name
	 * the name of the player
	 */
	public Player(PlayerStrategy _bj_strategy, String player_name) {
		if (_bj_strategy == null) {
			throw new IllegalArgumentException(
					"Player strategy can not be null");
		}
		if (player_name == null) {
			throw new IllegalArgumentException("Player name cannot be null");
		}
		++player_num;
		name = player_name;

		bj_table = null;
		bets = null;
		bets_ro = null;

		bj_strategy = _bj_strategy;
		bankroll = 500;

		player_obs = new ObserverList<PlayerObserver>();
	}

	/**
	 * Creates an unnamed blackjack player with the given strategy and bankroll.
	 * 
	 * @param _bj_strategy
	 * the strategy the blackjack player will use
	 * @param _bankroll
	 * the starting bankroll of the player
	 */
	public Player(PlayerStrategy _bj_strategy, int _bankroll) {
		if (_bj_strategy == null) {
			throw new IllegalArgumentException(
					"Player strategy can not be null");
		}
		if (_bankroll < 0) {
			throw new IllegalArgumentException("Bankroll can not be negative");
		}
		++player_num;
		name = "Player " + player_num;

		bj_table = null;
		bets = null;
		bets_ro = null;

		bj_strategy = _bj_strategy;
		bankroll = _bankroll;

		player_obs = new ObserverList<PlayerObserver>();
	}

	/**
	 * Creates a named blackjack player with the given strategy and bankroll.
	 * 
	 * @param _bj_strategy
	 * the strategy the blackjack player will use
	 * @param _bankroll
	 * the starting bankroll of the player
	 * @param player_name
	 * the name of the player
	 */
	public Player(PlayerStrategy _bj_strategy, int _bankroll, String player_name) {
		if (_bj_strategy == null) {
			throw new IllegalArgumentException(
					"Player strategy can not be null");
		}
		if (_bankroll < 0) {
			throw new IllegalArgumentException("Bankroll can not be negative");
		}
		if (player_name == null) {
			throw new IllegalArgumentException("Player name cannot be null");
		}
		++player_num;
		name = player_name;

		bj_table = null;
		bets = null;
		bets_ro = null;

		bj_strategy = _bj_strategy;
		bankroll = _bankroll;

		player_obs = new ObserverList<PlayerObserver>();
	}

	/**
	 * Returns the table this player is playing blackjack at. If this player is
	 * not playing at any table, this method returns <code>null</code>.
	 * 
	 * @return the blackjack table of this player
	 */
	public Table getTable() {
		return bj_table;
	}

	/**
	 * Sets the strategy of the player to <code>new_strategy</code>. The
	 * previous strategy of the player is returned.
	 * 
	 * @param new_strategy
	 * the new player strategy
	 * @return the previous player strategy
	 */
	public PlayerStrategy setStrategy(PlayerStrategy new_strategy) {
		if (new_strategy == null) {
			throw new IllegalArgumentException(
					"Player strategy can not be null");
		}
		PlayerStrategy prev_strategy = bj_strategy;
		bj_strategy = new_strategy;
		return prev_strategy;
	}

	/**
	 * Returns the strategy used by the player.
	 * 
	 * @return the player strategy
	 */
	public PlayerStrategy getStrategy() {
		return bj_strategy;
	}

	/**
	 * Sets the bankroll of the player to <code>new_bankroll</code>. The
	 * previous bankroll is returned.
	 * 
	 * @param new_bankroll
	 * the new player bankroll
	 * @return the previous player bankroll
	 */
	public int setBankroll(int new_bankroll) {
		if (new_bankroll < 0) {
			throw new IllegalArgumentException("Bankroll can not be negative");
		}
		int prev_bankroll = bankroll;
		bankroll = new_bankroll;
		return prev_bankroll;
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
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("Player ").append(getName()).append(": bankroll = ").append(bankroll);
		if (bj_table != null) {
			sbuf.append(", table = ").append(bj_table.getName());
		}
		return sbuf.toString();
	}

	/**
	 * Returns the list of observers, to which observers for this player can be
	 * added, removed, and so forth.
	 * 
	 * @return the list of observers for this player
	 */
	public ObserverList<PlayerObserver> getObservers() {
		return player_obs;
	}

	/*
	 * Dispatch of player observer methods.
	 */

	protected void playerJoins(Table t) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerJoins(t);
		}
	}

	protected void playerLeaves(Table t) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerLeaves(t);
		}
	}

	protected void playerBets(int bet_amount, int bankroll) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerBets(bet_amount, bankroll);
		}
	}
	
	protected void playerInsures(int bet_amount, int bankroll) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerInsures(bet_amount, bankroll);
		}
	}

	protected void playerDealt(PlayerHand player_hand) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerDealt(player_hand);
		}
	}

	protected void playerDraws(Card dealt_card, PlayerHand new_hand) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerDraws(dealt_card, new_hand);
		}
	}

	protected void playerStands(PlayerHand player_hand) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerStands(player_hand);
		}
	}
	
	protected void playerBusts(PlayerHand player_hand, int amount_lost, int new_bankroll) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext(); ) {
			PlayerObserver player_obs = i.next();
			player_obs.playerBusts(player_hand, amount_lost, new_bankroll);
		}
	}

	protected void playerSplits(PlayerHand player_hand) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerSplits(player_hand);
		}
	}

	protected void playerDoublesDown(Card dealt_card, PlayerHand new_hand) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_ob = i.next();
			player_ob.playerDoublesDown(dealt_card, new_hand);
		}
	}

	protected void playerWins(PlayerHand player_hand, int amount_won, int new_bankroll) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerWins(player_hand, amount_won, new_bankroll);
		}
	}

	protected void playerLoses(PlayerHand player_hand, int amount_lost, int new_bankroll) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerLoses(player_hand, amount_lost, new_bankroll);
		}
	}

	protected void playerBlackjack(PlayerHand player_hand, int amount_won, int new_bankroll) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerBlackjack(player_hand, amount_won, new_bankroll);
		}
	}

	protected void playerPush(PlayerHand player_hand, int held_bankroll) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext();) {
			PlayerObserver player_obs = i.next();
			player_obs.playerPush(player_hand, held_bankroll);
		}
	}
	
	protected void playerWinsInsurance(int amount_won, int new_bankroll) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext(); ) {
			PlayerObserver player_obs = i.next();
			player_obs.playerWinsInsurance(amount_won, new_bankroll);
		}
	}
	
	protected void playerLosesInsurance(int amount_lost, int new_bankroll) {
		List<PlayerObserver> obs_list = player_obs.obs_list;
		for (Iterator<PlayerObserver> i = obs_list.iterator(); i.hasNext(); ) {
			PlayerObserver player_obs = i.next();
			player_obs.playerLosesInsurance(amount_lost, new_bankroll);
		}
	}
}
