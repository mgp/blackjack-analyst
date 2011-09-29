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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import blackjackanalyst.DealerStrategy.DealerStrategyAction;
import blackjackanalyst.PlayerStrategy.PlayerStrategyAction;

/**
 * A blackjack table.
 * 
 * @author Michael Parker
 */
public class Table {
	protected static int table_num = 0;

	protected final String name;
	protected final int max_players;
	protected final int min_bet;
	protected final int max_bet;

	protected final DealerStrategy dealer_strategy;
	protected Hand dealer_hand;
	protected final Shoe table_shoe;
	
	protected final List<Player> players;
	protected final List<Player> players_ro;

	protected final ObserverList<TableObserver> table_obs;

	/**
	 * Creates a new unnamed table with the given maximum number of players, 
	 * dealer strategy, and minimum bet and maximum bet.
	 * 
	 * @param _max_players
	 * the maximum number of blackjack players
	 * @param _dealer_strat
	 * the strategy used by the dealer
	 * @param _min_bet
	 * the minimum bet at this table
	 * @param _max_bet
	 * the maximum bet at this table
	 */
	public Table(int _max_players, DealerStrategy _dealer_strat, int _min_bet, int _max_bet) {
		verifyConstructorParams(_max_players, _dealer_strat, _min_bet, _max_bet);
		
		name = "Table " + table_num;
		++table_num;
		max_players = _max_players;
		min_bet = _min_bet;
		max_bet = _max_bet;

		dealer_strategy = _dealer_strat;
		dealer_hand = null;
		table_shoe = new Shoe(8);
		
		players = new LinkedList<Player>();
		players_ro = Collections.unmodifiableList(players);

		table_obs = new ObserverList<TableObserver>();
	}
	
	/**
	 * Creates a new table with the given name, maximum number of players, 
	 * dealer strategy, and minimum bet and maximum bet.
	 * 
	 * @param _name
	 * the name of the blackjack table
	 * @param _max_players
	 * the maximum number of blackjack players
	 * @param _dealer_strat
	 * the strategy used by the dealer
	 * @param _min_bet
	 * the minimum bet at this table
	 * @param _max_bet
	 * the maximum bet at this table
	 */
	public Table(String _name, int _max_players, DealerStrategy _dealer_strat, 
			int _min_bet, int _max_bet) {
		if (_name == null) {
			throw new IllegalArgumentException(
					"Table name must be provided");
		}
		verifyConstructorParams(_max_players, _dealer_strat, _min_bet, _max_bet);

		name = _name;
		++table_num;
		max_players = _max_players;
		min_bet = _min_bet;
		max_bet = _max_bet;

		dealer_strategy = _dealer_strat;
		dealer_hand = null;
		table_shoe = new Shoe(8);
		
		players = new LinkedList<Player>();
		players_ro = Collections.unmodifiableList(players);

		table_obs = new ObserverList<TableObserver>();
	}
	
	protected void verifyConstructorParams(int _max_players, 
			DealerStrategy _dealer_strat, int _min_bet, int _max_bet) {
		if (_max_players < 1) {
			throw new IllegalArgumentException(
					"Maximum player argument must be positive");
		}
		if (_dealer_strat == null) {
			throw new IllegalArgumentException(
					"Dealer strategy must be provided");
		}
		if (_min_bet < 0) {
			throw new IllegalArgumentException(
					"Minimum bet must not be negative");
		}
		if (_max_bet < 0) {
			throw new IllegalArgumentException(
					"Maximum bet must not be negative");
		}
		if (_max_bet < _min_bet) {
			throw new IllegalArgumentException(
					"Maximum bet must not be less than minimum bet");
		}
	}

	/**
	 * Returns the name of the table.
	 * 
	 * @return the player name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the maximum number of players at this blackjack table.
	 * 
	 * @return the maximum number of blackjack players
	 */
	public int getMaxPlayers() {
		return max_players;
	}

	/**
	 * Returns the strategy used by the dealer.
	 * 
	 * @return the dealer strategy
	 */
	public DealerStrategy getStrategy() {
		return dealer_strategy;
	}

	/**
	 * Returns the minimum bet at the table.
	 * 
	 * @return the minimum bet
	 */
	public int getMinimumBet() {
		return min_bet;
	}

	/**
	 * Returns the maximum bet at the table.
	 * 
	 * @return the maximum bet
	 */
	public int getMaximumBet() {
		return max_bet;
	}

	/**
	 * Returns the number of players at this blackjack table.
	 * 
	 * @return the current number of blackjack players
	 */
	public int getNumPlayers() {
		return players.size();
	}

	/**
	 * Returns whether there is room for more players at the blackjack table.
	 * This method returns <code>true</code> if and only if the current number
	 * of blackjack players is less than the maximum number permitted.
	 * 
	 * @return <code>true</code> if more players can be added to the table,
	 * <code>false</code> otherwise
	 */
	public boolean hasRoom() {
		return (getNumPlayers() < getMaxPlayers());
	}

	/**
	 * Returns a list containing all the players at the blackjack table. The
	 * returned list is always backed by the players currently at the table; the
	 * returned list may not be modified, however, so that players cannot be
	 * added to or removed from the table through the list.
	 * 
	 * @return the players currently at this table
	 */
	public List<Player> getPlayers() {
		return players_ro;
	}

	/**
	 * Attempts to add the given player to the table. For this method to succeed
	 * and return <code>true</code>, the table must have room for the player
	 * and the player must not be at a table already. Otherwise, this method
	 * returns <code>false</code>.
	 * 
	 * @param p
	 * the player to add to the table
	 * @return <code>true</code> if the player is added to the table,
	 * <code>false</code> otherwise
	 */
	public boolean addPlayer(Player p) {
		if (hasRoom() && (p.bj_table == null)) {
			players.add(p);
			p.bj_table = this;
			p.bets = new ArrayList<PlayerHand>();
			p.bets_ro = Collections.unmodifiableList(p.bets);
			// notify strategy
			p.bj_strategy.joinedTable(this);
			// notify observers
			p.playerJoins(this);
			playerJoins(p);
			return true;
		}
		return false;
	}

	/**
	 * Attempts to remove the given player from the table. For this method to
	 * succeed and return <code>true</code>, the player must be at the table
	 * already.
	 * 
	 * @param p
	 * the player to remove from the table
	 * @return <code>true</code> if the player is removed from the table,
	 * <code>false</code> otherwise
	 */
	public boolean removePlayer(Player p) {
		if (players.remove(p)) {
			p.bj_table = null;
			p.bets = null;
			p.bets_ro = null;
			// notify strategy
			p.bj_strategy.leftTable(this);
			// notify observers
			p.playerLeaves(this);
			playerLeaves(p);
			return true;
		}
		return false;
	}

	/**
	 * Plays a single round of blackjack.
	 */
	public void playRound() {
		playRounds(1);
	}

	/**
	 * Plays the given number of rounds of blackjack.
	 * 
	 * @param num_rounds
	 * the number of blackjack rounds to play
	 */
	public void playRounds(int num_rounds) {
		if (players.isEmpty()) {
			return;
		}

		for (int i = 0; i < num_rounds; ++i) {
			newRound();
			
			if (table_shoe.needsShuffle()) {
				table_shoe.shuffle();
				notifyShuffle();
				shoeShuffled();
			}

			getBets();
			dealPlayers();
			if (dealHouse()) {
				// dealer did not have blackjack
				drawPlayers();
				drawHouse();
			}
			clearTable();
		}
	}
	
	/**
	 * Returns the list of observers, to which observers for this table can be
	 * added, removed, and so forth.
	 * 
	 * @return the list of observers for this table
	 */
	public ObserverList<TableObserver> getObservers() {
		return table_obs;
	}
	
	/*
	 * Get the bets of each player.
	 */
	protected void getBets() {
		for (Iterator<Player> i = players.iterator(); i.hasNext();) {
			Player curr_player = i.next();
			if (curr_player.bankroll <= 0) {
				// player has no money left, go onto next player
				playerBets(curr_player, 0, curr_player.bankroll);
				curr_player.playerBets(0, curr_player.bankroll);
				continue;
			}
			
			int bet_amount = curr_player.getStrategy().getBet(curr_player.bankroll);
			if (bet_amount > 0) {
				// add player bet
				PlayerHand first_bet = new PlayerHand(bet_amount, false, curr_player);
				curr_player.bets.add(first_bet);
			}
			
			// notify observers of player bet
			playerBets(curr_player, bet_amount, curr_player.bankroll);
			curr_player.playerBets(bet_amount, curr_player.bankroll);
		}
	}

	/*
	 * Deal two cards to each player.
	 */
	protected void dealPlayers() {
		for (Iterator<Player> i = players.iterator(); i.hasNext();) {
			// deal first card to player
			Player curr_player = i.next();
			if (curr_player.bets.isEmpty()) {
				continue;
			}
			// if player has placed bet, deal first card
			Card dealt_card = table_shoe.getNextCard();
			// add card to hand
			PlayerHand first_hand = curr_player.bets.get(0);
			first_hand.add(dealt_card);
			
			// notify strategies of dealt card
			notifyDealt(dealt_card);
		}
		for (Iterator<Player> i = players.iterator(); i.hasNext();) {
			// deal second card to player
			Player curr_player = i.next();
			if (curr_player.bets.isEmpty()) {
				continue;
			}
			// if player has placed bet, deal second card
			Card dealt_card = table_shoe.getNextCard();
			// add card to hand
			PlayerHand first_hand = curr_player.bets.get(0);
			first_hand.add(dealt_card);
			
			// notify strategies of dealt card
			notifyDealt(dealt_card);
			// notify obsevers of dealt hand
			curr_player.playerDealt(first_hand);
			playerDealt(curr_player, first_hand);
		}
	}

	/*
	 * Deal two cards to the house.
	 */
	protected boolean dealHouse() {
		// deal up card and down card for dealer
		Card dealt_up = table_shoe.getNextCard();
		Card dealt_down = table_shoe.getNextCard();
		dealer_hand = new Hand(dealt_up, dealt_down);
		
		// notify strategies of dealer up card
		notifyDealt(dealt_up);
		// notify observers of dealer up card
		dealerDealt(dealt_up);
		
		if (dealt_up.isAce()) {
			// dealer showing ace, offer players insurance
			for (Iterator<Player> i = players.iterator(); i.hasNext();) {
				Player curr_player = i.next();
				if (curr_player.bets.isEmpty()) {
					// player did not place bet
					continue;
				}
				
				// player has only one bet, get insurance on it
				PlayerHand first_bet = curr_player.bets.get(0);
				int insurance_amt = curr_player.bj_strategy.getInsuranceBet(
						first_bet, first_bet.bet_amount);
				final int half_bet = first_bet.bet_amount / 2;
				if (insurance_amt > half_bet) {
					insurance_amt = half_bet;
				}
				else if (insurance_amt < 0) {
					insurance_amt = 0;
				}
				
				// set insurance amount and notify observers
				curr_player.insurance_bet = insurance_amt;
				curr_player.playerInsures(insurance_amt, curr_player.bankroll);
				playerInsures(curr_player, insurance_amt, curr_player.bankroll);
			}
		}
		
		if ((dealt_up.isAce() || (dealt_up.getValue() == 10)) && 
				(dealer_hand.getHighValidValue() == 21)) {
			// dealer has blackjack, notify strategies of down card
			notifyDealt(dealt_down);
			// notify observers of blackjack on dealer
			dealerBlackjack(dealer_hand);
			
			for (Iterator<Player> i = players.iterator(); i.hasNext();) {
				Player curr_player = i.next();
				if (curr_player.bets.isEmpty()) {
					// player did not place bet
					continue;
				}
				
				if (curr_player.insurance_bet > 0) {
					// player wins on insurance
					int amount_won = 2 * curr_player.insurance_bet;
					curr_player.bankroll += amount_won;
					curr_player.insurance_bet = 0;
					// notify observers of win on insurance
					curr_player.playerWinsInsurance(amount_won, curr_player.bankroll);
					playerWinsInsurance(curr_player, amount_won, curr_player.bankroll);
				}
				
				// player has only one bet
				PlayerHand first_bet = curr_player.bets.get(0);
				if (first_bet.getHighValidValue() < 21) {
					// player hand is not blackjack, deduct from bankroll
					curr_player.bankroll -= first_bet.bet_amount;
					
					// notify observers of loss to dealer
					curr_player.playerLoses(first_bet, first_bet.bet_amount, curr_player.bankroll);
					playerLoses(curr_player, first_bet, first_bet.bet_amount, curr_player.bankroll);
				}
				else {
					// notify observers of push with dealer
					curr_player.playerPush(first_bet, curr_player.bankroll);
					playerPush(curr_player, first_bet, curr_player.bankroll);
				}
			}
			// do not continue drawing players and drawing house
			return false;
		}
		else {
			for (Iterator<Player> i = players.iterator(); i.hasNext(); ) {
				Player curr_player = i.next();
				if (curr_player.bets.isEmpty()) {
					// player did not place bet
					continue;
				}
				
				if (curr_player.insurance_bet > 0) {
					// player loses on insurance
					int amount_lost = curr_player.insurance_bet;
					curr_player.bankroll -= amount_lost;
					curr_player.insurance_bet = 0;
					// notify observers of loss on insurance
					curr_player.playerLosesInsurance(amount_lost, curr_player.bankroll);
					playerLosesInsurance(curr_player, amount_lost, curr_player.bankroll);
				}
				
				// player has only one bet
				PlayerHand first_bet = curr_player.bets.get(0);
				if (first_bet.getHighValidValue() == 21) {
					first_bet.finished = true;
					// player hand is blackjack, add to bankroll
					int amount_won = (int) (1.5 * first_bet.bet_amount);
					curr_player.bankroll += amount_won;
					
					// notify observers of player blackjack
					curr_player.playerBlackjack(first_bet, amount_won, curr_player.bankroll);
					playerBlackjack(curr_player, first_bet, amount_won, curr_player.bankroll);
				}
			}
			// continue drawing players and drawing house
			return true;
		}
	}

	protected void drawPlayers() {
		// dealer up card is first card in hand
		Card dealer_card = dealer_hand.hand_cards.get(0);
		
		for (Iterator<Player> i = players.iterator(); i.hasNext(); ) {
			Player curr_player = i.next();
			ArrayList<PlayerHand> player_bets = curr_player.bets;
			
			for (int bet_num = 0; bet_num < player_bets.size(); ++bet_num) {
				PlayerHand curr_hand = player_bets.get(bet_num);
				if (curr_hand.finished) {
					// player already dealt winnings for blackjack
					continue;
				}
				
				while (true) {
					// get player action
					PlayerStrategyAction player_action = 
						curr_player.bj_strategy.getAction(curr_hand, dealer_card);
					
					if (player_action == PlayerStrategyAction.STAND) {
						// set hand as finished
						curr_hand.finished = true;
						// notify observers that player stands
						curr_player.playerStands(curr_hand);
						playerStands(curr_player, curr_hand);
						break;
					}
					else if (player_action == PlayerStrategyAction.HIT) {
						// add next card to hand
						Card dealt_card = table_shoe.getNextCard();
						curr_hand.add(dealt_card);
						
						// notify players of dealt card
						notifyDealt(dealt_card);
						// notify observers of dealt card
						curr_player.playerDraws(dealt_card, curr_hand);
						playerDraws(curr_player, dealt_card, curr_hand);
						
						if (curr_hand.isBusted()) {
							// player busted, set hand as finished
							curr_hand.finished = true;
							// deduct from bankroll
							curr_player.bankroll -= curr_hand.bet_amount;
							
							// notify observers that player busted
							curr_player.playerBusts(curr_hand, 
									curr_hand.bet_amount, curr_player.bankroll);
							playerBusts(curr_player, curr_hand, curr_hand.bet_amount, 
									curr_player.bankroll);
							break;
						}
						else if (curr_hand.been_split) {
							if (curr_hand.isBlackjack()) {
								curr_hand.finished = true;
								// player hand is blackjack, add to bankroll
								int amount_won = (int) (1.5 * curr_hand.bet_amount);
								curr_player.bankroll += amount_won;
								
								// notify observers of player blackjack
								curr_player.playerBlackjack(curr_hand, 
										amount_won, curr_player.bankroll);
								playerBlackjack(curr_player, curr_hand, 
										amount_won, curr_player.bankroll);
								break;
							}
							else if (curr_hand.hand_cards.get(0).isAce()) {
								if (curr_hand.isPair()) {
									// allow resplitting of aces
									player_action = 
										curr_player.bj_strategy.getAction(curr_hand, dealer_card);
									if (player_action == PlayerStrategyAction.SPLIT) {
										// notify observers that player splits
										curr_player.playerSplits(curr_hand);
										playerSplits(curr_player, curr_hand);

										// make new bet with split card
										PlayerHand new_hand = curr_hand.makeSplit();
										player_bets.add(new_hand);
										continue;
									}
								}
								
								// player must now stand
								curr_hand.finished = true;
								// notify observers that player stands
								curr_player.playerStands(curr_hand);
								playerStands(curr_player, curr_hand);
								break;
							}
						}
					}
					else if (player_action == PlayerStrategyAction.DOUBLE_DOWN) {
						if (curr_hand.hand_cards.size() != 2) {
							// cannot double down if more than two cards, set hand as finished
							curr_hand.finished = true;
							// notify observers that player stands
							curr_player.playerStands(curr_hand);
							playerStands(curr_player, curr_hand);
							break;
						}
						
						// double bet
						curr_hand.bet_amount *= 2;
						// add next card to hand
						Card dealt_card = table_shoe.getNextCard();
						curr_hand.add(dealt_card);
						// set hand as finished
						curr_hand.finished = true;
						
						// notify players of dealt card
						notifyDealt(dealt_card);
						// notify observers of dealt card
						curr_player.playerDoublesDown(dealt_card, curr_hand);
						playerDoublesDown(curr_player, dealt_card, curr_hand);
						
						if (curr_hand.isBusted()) {
							// deduct from bankroll
							curr_player.bankroll -= curr_hand.bet_amount;
							
							// notify observers that player busted
							curr_player.playerBusts(curr_hand, 
									curr_hand.bet_amount, curr_player.bankroll);
							playerBusts(curr_player, curr_hand, curr_hand.bet_amount, 
									curr_player.bankroll);
						}
						break;
					}
					else if (player_action == PlayerStrategyAction.SPLIT) {
						if (!curr_hand.isPair()) {
							// cannot split if hand is not a pair, set hand as finished
							curr_hand.finished = true;
							// notify observers that player stands
							curr_player.playerStands(curr_hand);
							playerStands(curr_player, curr_hand);
							break;
						}
						
						// notify observers that player splits
						curr_player.playerSplits(curr_hand);
						playerSplits(curr_player, curr_hand);

						// make new bet with split card
						PlayerHand new_hand = curr_hand.makeSplit();
						player_bets.add(new_hand);
					}
				}
			}
		}
	}

	/*
	 * Draw cards for the house.
	 */
	protected void drawHouse() {
		// notify players of down card
		notifyDealt(dealer_hand.hand_cards.get(1));
		// notify observers of down card
		dealerDealt(dealer_hand.hand_cards.get(1), dealer_hand);
		
		// draw cards for dealer
		while (!dealer_hand.isBusted() && 
				(dealer_strategy.getAction(dealer_hand) == DealerStrategyAction.HIT)) {
			Card dealt_card = table_shoe.getNextCard();
			dealer_hand.add(dealt_card);
			
			// notify players of dealt card
			notifyDealt(dealt_card);
			// notify observers of dealt card
			dealerDraws(dealt_card, dealer_hand);
		}
		
		// dealer hand is finished
		dealer_hand.finished = true;
		// evaluate dealer hand
		boolean dealer_busted = dealer_hand.isBusted();
		int dealer_high_value = dealer_hand.getHighValidValue();
		if (dealer_busted) {
			// notify observers of dealer bust
			dealerBusts(dealer_hand);
		}
		else {
			// notify observers of dealer stand
			dealerStands(dealer_hand);
		}

		// evaluate hands of each player, adjusting bankrolls
		for (Iterator<Player> i = players.iterator(); i.hasNext();) {
			// get next player
			Player curr_player = i.next();
			for (Iterator<PlayerHand> j = curr_player.bets.iterator(); j.hasNext();) {
				// get next hand belonging to player
				PlayerHand curr_hand = j.next();

				if (!curr_hand.isBlackjack() && !curr_hand.isBusted()) {
					// player hand did not adjust bankroll earlier, get its best value
					int player_high_value = curr_hand.getHighValidValue();
					
					if (dealer_busted || (dealer_high_value < player_high_value)) {
						// dealer hand busted or player hand beat dealer hand, add to bankroll
						curr_player.bankroll += curr_hand.bet_amount;
						
						// notify observers of player win
						curr_player.playerWins(curr_hand, 
								curr_hand.bet_amount, curr_player.bankroll);
						playerWins(curr_player, curr_hand, 
								curr_hand.bet_amount, curr_player.bankroll);
					}
					else if (dealer_high_value > player_high_value){
						// dealer hand beat player hand, deduct from bankroll
						curr_player.bankroll -= curr_hand.bet_amount;
						
						// notify observers of player loss
						curr_player.playerLoses(curr_hand, 
								curr_hand.bet_amount, curr_player.bankroll);
						playerLoses(curr_player, curr_hand, 
								curr_hand.bet_amount, curr_player.bankroll);
					}
					else {
						// dealer hand equals player hand, notify observers of push
						curr_player.playerPush(curr_hand, curr_player.bankroll);
						playerPush(curr_player, curr_hand, curr_player.bankroll);
					}
				}
			}
		}
	}
	
	/*
	 * Dispatch of strategy observer methods.
	 */

	protected void clearTable() {
		dealer_hand = null;
		for (Iterator<Player> i = players.iterator(); i.hasNext(); ) {
			Player curr_player = i.next();
			curr_player.bets.clear();
		}
	}

	protected void notifyShuffle() {
		for (Iterator<Player> i = players.iterator(); i.hasNext();) {
			Player curr_player = i.next();
			curr_player.getStrategy().shoeShuffled();
		}
	}

	protected void notifyDealt(Card dealt_card) {
		for (Iterator<Player> i = players.iterator(); i.hasNext();) {
			Player curr_player = i.next();
			curr_player.getStrategy().cardDealt(dealt_card);
		}
	}
	
	/*
	 * Dispatch of table observer methods.
	 */
	
	protected void newRound() {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.newRound(players_ro);
		}
	}
	
	protected void shoeShuffled() {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.shoeShuffled();
		}
	}

	protected void dealerDealt(Card up_card) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.dealerDealt(up_card);
		}
	}

	protected void dealerDealt(Card down_card, Hand dealer_hand) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.dealerDealt(down_card, dealer_hand);
		}
	}

	protected void dealerBlackjack(Hand dealer_hand) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.dealerBlackjack(dealer_hand);
		}
	}

	protected void dealerDraws(Card dealt_card, Hand new_hand) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.dealerDraws(dealt_card, new_hand);
		}
	}

	protected void dealerStands(Hand dealer_hand) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.dealerStands(dealer_hand);
		}
	}
	
	protected void dealerBusts(Hand dealer_hand) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.dealerBusts(dealer_hand);
		}
	}

	protected void playerJoins(Player joined_player) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerJoins(joined_player);
		}
	}

	protected void playerLeaves(Player left_player) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerLeaves(left_player);
		}
	}

	protected void playerBets(Player betting_player, int bet_amount, int bankroll) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerBets(betting_player, bet_amount, bankroll);
		}
	}
	
	protected void playerInsures(Player betting_player, int bet_amount, int bankroll) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerInsures(betting_player, bet_amount, bankroll);
		}
	}

	protected void playerDealt(Player dealt_player, PlayerHand player_hand) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerDealt(dealt_player, player_hand);
		}
	}

	protected void playerDraws(Player dealt_player, Card dealt_card, PlayerHand new_hand) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerDraws(dealt_player, dealt_card, new_hand);
		}
	}

	protected void playerStands(Player dealt_player, PlayerHand player_hand) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerStands(dealt_player, player_hand);
		}
	}
	
	protected void playerBusts(Player dealt_player, PlayerHand player_hand, int amount_lost, 
			int new_bankroll) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerBusts(dealt_player, player_hand, amount_lost, new_bankroll);
		}
	}

	protected void playerSplits(Player dealt_player, PlayerHand player_hand) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerSplits(dealt_player, player_hand);
		}
	}

	protected void playerDoublesDown(Player dealt_player, Card dealt_card,
			PlayerHand new_hand) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerDoublesDown(dealt_player, dealt_card, new_hand);
		}
	}

	protected void playerWins(Player dealt_player, PlayerHand player_hand, 
			int amount_won, int new_bankroll) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerWins(dealt_player, player_hand, amount_won, new_bankroll);
		}
	}

	protected void playerLoses(Player dealt_player, PlayerHand player_hand, 
			int amount_lost, int new_bankroll) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerLoses(dealt_player, player_hand, amount_lost, new_bankroll);
		}
	}

	protected void playerBlackjack(Player dealt_player, PlayerHand player_hand, 
			int amount_won, int new_bankroll) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerBlackjack(dealt_player, player_hand, amount_won, new_bankroll);
		}
	}

	protected void playerPush(Player dealt_player, PlayerHand player_hand, int held_bankroll) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerPush(dealt_player, player_hand, held_bankroll);
		}
	}
	
	protected void playerWinsInsurance(Player dealt_player, int amount_won, int new_bankroll) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerWinsInsurance(dealt_player, amount_won, new_bankroll);
		}
	}
	
	protected void playerLosesInsurance(Player dealt_player, int amount_lost, int new_bankroll) {
		List<TableObserver> obs_list = table_obs.obs_list;
		for (Iterator<TableObserver> i = obs_list.iterator(); i.hasNext(); ) {
			TableObserver table_obs = i.next();
			table_obs.playerLosesInsurance(dealt_player, amount_lost, new_bankroll);
		}
	}
}
