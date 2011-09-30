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
import java.util.Collections;
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
	protected static int tableNum = 0;

	protected final String name;
	protected final int maxPlayers;
	protected final int minBet;
	protected final int maxBet;

	protected final DealerStrategy dealerStrategy;
	protected Hand dealerHand;
	protected final Shoe shoe;

	protected final List<Player> players;
	protected final List<Player> playersReadOnly;

	protected final ObserverList<TableObserver> tableObservers;

	/**
	 * Creates a new unnamed table with the given maximum number of players,
	 * dealer strategy, and minimum bet and maximum bet.
	 * 
	 * @param maxPlayers the maximum number of blackjack players
	 * @param dealerStrategy the strategy used by the dealer
	 * @param minBet the minimum bet at this table
	 * @param maxBet the maximum bet at this table
	 */
	public Table(int maxPlayers, DealerStrategy dealerStrategy, int minBet,
	    int maxBet) {
		verifyConstructorParams(maxPlayers, dealerStrategy, minBet, maxBet);

		this.name = "Table " + tableNum;
		++tableNum;
		this.maxPlayers = maxPlayers;
		this.minBet = minBet;
		this.maxBet = maxBet;

		this.dealerStrategy = dealerStrategy;
		dealerHand = null;
		shoe = new Shoe(8);

		players = new LinkedList<Player>();
		playersReadOnly = Collections.unmodifiableList(players);

		tableObservers = new ObserverList<TableObserver>();
	}

	/**
	 * Creates a new table with the given name, maximum number of players, dealer
	 * strategy, and minimum bet and maximum bet.
	 * 
	 * @param name the name of the blackjack table
	 * @param maxPlayers the maximum number of blackjack players
	 * @param dealerStrategy the strategy used by the dealer
	 * @param minBet the minimum bet at this table
	 * @param maxBet the maximum bet at this table
	 */
	public Table(String name, int maxPlayers, DealerStrategy dealerStrategy,
	    int minBet, int maxBet) {
		if (name == null) {
			throw new IllegalArgumentException("Table name must be provided");
		}
		verifyConstructorParams(maxPlayers, dealerStrategy, minBet, maxBet);

		this.name = name;
		++tableNum;
		this.maxPlayers = maxPlayers;
		this.minBet = minBet;
		this.maxBet = maxBet;

		this.dealerStrategy = dealerStrategy;
		dealerHand = null;
		shoe = new Shoe(8);

		players = new LinkedList<Player>();
		playersReadOnly = Collections.unmodifiableList(players);

		tableObservers = new ObserverList<TableObserver>();
	}

	protected void verifyConstructorParams(int maxPlayers,
	    DealerStrategy dealerStrategy, int minBet, int maxBet) {
		if (maxPlayers < 1) {
			throw new IllegalArgumentException(
			    "Maximum player argument must be positive");
		}
		if (dealerStrategy == null) {
			throw new IllegalArgumentException("Dealer strategy must be provided");
		}
		if (minBet < 0) {
			throw new IllegalArgumentException("Minimum bet must not be negative");
		}
		if (maxBet < 0) {
			throw new IllegalArgumentException("Maximum bet must not be negative");
		}
		if (maxBet < minBet) {
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
		return maxPlayers;
	}

	/**
	 * Returns the strategy used by the dealer.
	 * 
	 * @return the dealer strategy
	 */
	public DealerStrategy getStrategy() {
		return dealerStrategy;
	}

	/**
	 * Returns the minimum bet at the table.
	 * 
	 * @return the minimum bet
	 */
	public int getMinimumBet() {
		return minBet;
	}

	/**
	 * Returns the maximum bet at the table.
	 * 
	 * @return the maximum bet
	 */
	public int getMaximumBet() {
		return maxBet;
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
	 * Returns whether there is room for more players at the blackjack table. This
	 * method returns {@code true} if and only if the current number of blackjack
	 * players is less than the maximum number permitted.
	 * 
	 * @return {@code true} if more players can be added to the table,
	 *         {@code false} otherwise
	 */
	public boolean hasRoom() {
		return (getNumPlayers() < getMaxPlayers());
	}

	/**
	 * Returns a list containing all the players at the blackjack table. The
	 * returned list is always backed by the players currently at the table; the
	 * returned list may not be modified, however, so that players cannot be added
	 * to or removed from the table through the list.
	 * 
	 * @return the players currently at this table
	 */
	public List<Player> getPlayers() {
		return playersReadOnly;
	}

	/**
	 * Attempts to add the given player to the table. For this method to succeed
	 * and return {@code true}, the table must have room for the player and the
	 * player must not be at a table already. Otherwise, this method returns
	 * {@code false}.
	 * 
	 * @param player the player to add to the table
	 * @return {@code true} if the player is added to the table, {@code false}
	 *         otherwise
	 */
	public boolean addPlayer(Player player) {
		if (hasRoom() && (player.table == null)) {
			players.add(player);
			player.table = this;
			player.bets = new ArrayList<PlayerHand>();
			player.betsReadOnly = Collections.unmodifiableList(player.bets);
			// notify strategy
			player.strategy.joinedTable(this);
			// notify observers
			player.playerJoins(this);
			playerJoins(player);
			return true;
		}
		return false;
	}

	/**
	 * Attempts to remove the given player from the table. For this method to
	 * succeed and return {@code true}, the player must be at the table
	 * already.
	 * 
	 * @param player the player to remove from the table
	 * @return {@code true} if the player is removed from the table,
	 *         {@code false} otherwise
	 */
	public boolean removePlayer(Player player) {
		if (players.remove(player)) {
			player.table = null;
			player.bets = null;
			player.betsReadOnly = null;
			// notify strategy
			player.strategy.leftTable(this);
			// notify observers
			player.playerLeaves(this);
			playerLeaves(player);
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
	 * @param numRounds the number of blackjack rounds to play
	 */
	public void playRounds(int numRounds) {
		if (players.isEmpty()) {
			return;
		}

		for (int i = 0; i < numRounds; ++i) {
			newRound();

			if (shoe.needsShuffle()) {
				shoe.shuffle();
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
		return tableObservers;
	}

	/*
	 * Get the bets of each player.
	 */
	protected void getBets() {
		for (Player player : players) {
			if (player.bankroll <= 0) {
				// player has no money left, go onto next player
				playerBets(player, 0, player.bankroll);
				player.playerBets(0, player.bankroll);
				continue;
			}

			int betAmount = player.getStrategy().getBet(player.bankroll);
			if (betAmount > 0) {
				// add player bet
				PlayerHand firstBet = new PlayerHand(betAmount, false, player);
				player.bets.add(firstBet);
			}

			// notify observers of player bet
			playerBets(player, betAmount, player.bankroll);
			player.playerBets(betAmount, player.bankroll);
		}
	}

	/*
	 * Deal two cards to each player.
	 */
	protected void dealPlayers() {
		for (Player player : players) {
			// deal first card to player
			if (player.bets.isEmpty()) {
				continue;
			}
			// if player has placed bet, deal first card
			Card dealtCard = shoe.getNextCard();
			// add card to hand
			PlayerHand firstHand = player.bets.get(0);
			firstHand.add(dealtCard);

			// notify strategies of dealt card
			notifyDealt(dealtCard);
		}
		for (Player player : players) {
			// deal second card to player
			if (player.bets.isEmpty()) {
				continue;
			}
			// if player has placed bet, deal second card
			Card dealtCard = shoe.getNextCard();
			// add card to hand
			PlayerHand firstHand = player.bets.get(0);
			firstHand.add(dealtCard);

			// notify strategies of dealt card
			notifyDealt(dealtCard);
			// notify observers of dealt hand
			player.playerDealt(firstHand);
			playerDealt(player, firstHand);
		}
	}

	/*
	 * Deal two cards to the house.
	 */
	protected boolean dealHouse() {
		// deal up card and down card for dealer
		Card upCard = shoe.getNextCard();
		Card downCard = shoe.getNextCard();
		dealerHand = new Hand(upCard, downCard);

		// notify strategies of dealer up card
		notifyDealt(upCard);
		// notify observers of dealer up card
		dealerDealt(upCard);

		if (upCard.isAce()) {
			// dealer showing ace, offer players insurance
			for (Player player : players) {
				if (player.bets.isEmpty()) {
					// player did not place bet
					continue;
				}

				// player has only one bet, get insurance on it
				PlayerHand firstBet = player.bets.get(0);
				int insuranceBet = player.strategy.getInsuranceBet(firstBet,
				    firstBet.betAmount);
				final int halfBet = firstBet.betAmount / 2;
				if (insuranceBet > halfBet) {
					insuranceBet = halfBet;
				} else if (insuranceBet < 0) {
					insuranceBet = 0;
				}

				// set insurance amount and notify observers
				player.insuranceBet = insuranceBet;
				player.playerInsures(insuranceBet, player.bankroll);
				playerInsures(player, insuranceBet, player.bankroll);
			}
		}

		if ((upCard.isAce() || (upCard.getValue() == 10))
		    && (dealerHand.getHighValidValue() == 21)) {
			// dealer has blackjack, notify strategies of down card
			notifyDealt(downCard);
			// notify observers of blackjack on dealer
			dealerBlackjack(dealerHand);

			for (Player player : players) {
				if (player.bets.isEmpty()) {
					// player did not place bet
					continue;
				}

				if (player.insuranceBet > 0) {
					// player wins on insurance
					int amountWon = 2 * player.insuranceBet;
					player.bankroll += amountWon;
					player.insuranceBet = 0;
					// notify observers of win on insurance
					player.playerWinsInsurance(amountWon, player.bankroll);
					playerWinsInsurance(player, amountWon, player.bankroll);
				}

				// player has only one bet
				PlayerHand firstBet = player.bets.get(0);
				if (firstBet.getHighValidValue() < 21) {
					// player hand is not blackjack, deduct from bankroll
					player.bankroll -= firstBet.betAmount;

					// notify observers of loss to dealer
					player.playerLoses(firstBet, firstBet.betAmount, player.bankroll);
					playerLoses(player, firstBet, firstBet.betAmount, player.bankroll);
				} else {
					// notify observers of push with dealer
					player.playerPush(firstBet, player.bankroll);
					playerPush(player, firstBet, player.bankroll);
				}
			}
			// do not continue drawing players and drawing house
			return false;
		} else {
			for (Player player : players) {
				if (player.bets.isEmpty()) {
					// player did not place bet
					continue;
				}

				if (player.insuranceBet > 0) {
					// player loses on insurance
					int amountLost = player.insuranceBet;
					player.bankroll -= amountLost;
					player.insuranceBet = 0;
					// notify observers of loss on insurance
					player.playerLosesInsurance(amountLost, player.bankroll);
					playerLosesInsurance(player, amountLost, player.bankroll);
				}

				// player has only one bet
				PlayerHand firstBet = player.bets.get(0);
				if (firstBet.getHighValidValue() == 21) {
					firstBet.finished = true;
					// player hand is blackjack, add to bankroll
					int amountWon = (int) (1.5 * firstBet.betAmount);
					player.bankroll += amountWon;

					// notify observers of player blackjack
					player.playerBlackjack(firstBet, amountWon, player.bankroll);
					playerBlackjack(player, firstBet, amountWon, player.bankroll);
				}
			}
			// continue drawing players and drawing house
			return true;
		}
	}

	protected void drawPlayers() {
		// dealer up card is first card in hand
		Card dealerCard = dealerHand.cards.get(0);

		for (Player player : players) {
			ArrayList<PlayerHand> playerBets = player.bets;

			for (int betNum = 0; betNum < playerBets.size(); ++betNum) {
				PlayerHand currHand = playerBets.get(betNum);
				if (currHand.finished) {
					// player already dealt winnings for blackjack
					continue;
				}

				while (true) {
					// get player action
					PlayerStrategyAction action = player.strategy.getAction(currHand,
					    dealerCard);

					if (action == PlayerStrategyAction.STAND) {
						// set hand as finished
						currHand.finished = true;
						// notify observers that player stands
						player.playerStands(currHand);
						playerStands(player, currHand);
						break;
					} else if (action == PlayerStrategyAction.HIT) {
						// add next card to hand
						Card dealtCard = shoe.getNextCard();
						currHand.add(dealtCard);

						// notify players of dealt card
						notifyDealt(dealtCard);
						// notify observers of dealt card
						player.playerDraws(dealtCard, currHand);
						playerDraws(player, dealtCard, currHand);

						if (currHand.isBusted()) {
							// player busted, set hand as finished
							currHand.finished = true;
							// deduct from bankroll
							player.bankroll -= currHand.betAmount;

							// notify observers that player busted
							player.playerBusts(currHand, currHand.betAmount, player.bankroll);
							playerBusts(player, currHand, currHand.betAmount, player.bankroll);
							break;
						} else if (currHand.beenSplit) {
							if (currHand.isBlackjack()) {
								currHand.finished = true;
								// player hand is blackjack, add to bankroll
								int amountWon = (int) (1.5 * currHand.betAmount);
								player.bankroll += amountWon;

								// notify observers of player blackjack
								player.playerBlackjack(currHand, amountWon, player.bankroll);
								playerBlackjack(player, currHand, amountWon, player.bankroll);
								break;
							} else if (currHand.cards.get(0).isAce()) {
								if (currHand.isPair()) {
									// allow resplitting of aces
									action = player.strategy.getAction(currHand, dealerCard);
									if (action == PlayerStrategyAction.SPLIT) {
										// notify observers that player splits
										player.playerSplits(currHand);
										playerSplits(player, currHand);

										// make new bet with split card
										PlayerHand newHand = currHand.makeSplit();
										playerBets.add(newHand);
										continue;
									}
								}

								// player must now stand
								currHand.finished = true;
								// notify observers that player stands
								player.playerStands(currHand);
								playerStands(player, currHand);
								break;
							}
						}
					} else if (action == PlayerStrategyAction.DOUBLE_DOWN) {
						if (currHand.cards.size() != 2) {
							// cannot double down if more than two cards, set hand as finished
							currHand.finished = true;
							// notify observers that player stands
							player.playerStands(currHand);
							playerStands(player, currHand);
							break;
						}

						// double bet
						currHand.betAmount *= 2;
						// add next card to hand
						Card dealtCard = shoe.getNextCard();
						currHand.add(dealtCard);
						// set hand as finished
						currHand.finished = true;

						// notify players of dealt card
						notifyDealt(dealtCard);
						// notify observers of dealt card
						player.playerDoublesDown(dealtCard, currHand);
						playerDoublesDown(player, dealtCard, currHand);

						if (currHand.isBusted()) {
							// deduct from bankroll
							player.bankroll -= currHand.betAmount;

							// notify observers that player busted
							player.playerBusts(currHand, currHand.betAmount, player.bankroll);
							playerBusts(player, currHand, currHand.betAmount, player.bankroll);
						}
						break;
					} else if (action == PlayerStrategyAction.SPLIT) {
						if (!currHand.isPair()) {
							// cannot split if hand is not a pair, set hand as finished
							currHand.finished = true;
							// notify observers that player stands
							player.playerStands(currHand);
							playerStands(player, currHand);
							break;
						}

						// notify observers that player splits
						player.playerSplits(currHand);
						playerSplits(player, currHand);

						// make new bet with split card
						PlayerHand newHand = currHand.makeSplit();
						playerBets.add(newHand);
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
		notifyDealt(dealerHand.cards.get(1));
		// notify observers of down card
		dealerDealt(dealerHand.cards.get(1), dealerHand);

		// draw cards for dealer
		while (!dealerHand.isBusted()
		    && (dealerStrategy.getAction(dealerHand) == DealerStrategyAction.HIT)) {
			Card dealtCard = shoe.getNextCard();
			dealerHand.add(dealtCard);

			// notify players of dealt card
			notifyDealt(dealtCard);
			// notify observers of dealt card
			dealerDraws(dealtCard, dealerHand);
		}

		// dealer hand is finished
		dealerHand.finished = true;
		// evaluate dealer hand
		boolean dealerBusted = dealerHand.isBusted();
		int dealerHighValue = dealerHand.getHighValidValue();
		if (dealerBusted) {
			// notify observers of dealer bust
			dealerBusts(dealerHand);
		} else {
			// notify observers of dealer stand
			dealerStands(dealerHand);
		}

		// evaluate hands of each player, adjusting bankrolls
		for (Player player : players) {
			for (PlayerHand hand : player.bets) {
				if (!hand.isBlackjack() && !hand.isBusted()) {
					// player hand did not adjust bankroll earlier, get its best value
					int highValue = hand.getHighValidValue();
					if (dealerBusted || (dealerHighValue < highValue)) {
						// dealer hand busted or player hand beat dealer hand, add to
						// bankroll
						player.bankroll += hand.betAmount;

						// notify observers of player win
						player.playerWins(hand, hand.betAmount, player.bankroll);
						playerWins(player, hand, hand.betAmount, player.bankroll);
					} else if (dealerHighValue > highValue) {
						// dealer hand beat player hand, deduct from bankroll
						player.bankroll -= hand.betAmount;

						// notify observers of player loss
						player.playerLoses(hand, hand.betAmount, player.bankroll);
						playerLoses(player, hand, hand.betAmount, player.bankroll);
					} else {
						// dealer hand equals player hand, notify observers of push
						player.playerPush(hand, player.bankroll);
						playerPush(player, hand, player.bankroll);
					}
				}
			}
		}
	}

	/*
	 * Dispatch of strategy observer methods.
	 */

	protected void clearTable() {
		dealerHand = null;
		for (Player player : players) {
			player.bets.clear();
		}
	}

	protected void notifyShuffle() {
		for (Player player : players) {
			player.getStrategy().shoeShuffled();
		}
	}

	protected void notifyDealt(Card dealtCard) {
		for (Player player : players) {
			player.getStrategy().cardDealt(dealtCard);
		}
	}

	/*
	 * Dispatch of table observer methods.
	 */

	protected void newRound() {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.newRound(playersReadOnly);
		}
	}

	protected void shoeShuffled() {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.shoeShuffled();
		}
	}

	protected void dealerDealt(Card upCard) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.dealerDealt(upCard);
		}
	}

	protected void dealerDealt(Card downCard, Hand upCard) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.dealerDealt(downCard, upCard);
		}
	}

	protected void dealerBlackjack(Hand hand) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.dealerBlackjack(hand);
		}
	}

	protected void dealerDraws(Card card, Hand newHand) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.dealerDraws(card, newHand);
		}
	}

	protected void dealerStands(Hand hand) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.dealerStands(hand);
		}
	}

	protected void dealerBusts(Hand hand) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.dealerBusts(hand);
		}
	}

	protected void playerJoins(Player player) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerJoins(player);
		}
	}

	protected void playerLeaves(Player player) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerLeaves(player);
		}
	}

	protected void playerBets(Player player, int amount, int bankroll) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerBets(player, amount, bankroll);
		}
	}

	protected void playerInsures(Player player, int amount, int bankroll) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerInsures(player, amount, bankroll);
		}
	}

	protected void playerDealt(Player player, PlayerHand hand) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerDealt(player, hand);
		}
	}

	protected void playerDraws(Player player, Card dealtCard, PlayerHand hand) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerDraws(player, dealtCard, hand);
		}
	}

	protected void playerStands(Player player, PlayerHand hand) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerStands(player, hand);
		}
	}

	protected void playerBusts(Player player, PlayerHand hand, int amountLost,
	    int newBankroll) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerBusts(player, hand, amountLost, newBankroll);
		}
	}

	protected void playerSplits(Player player, PlayerHand hand) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerSplits(player, hand);
		}
	}

	protected void playerDoublesDown(Player player, Card card, PlayerHand hand) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerDoublesDown(player, card, hand);
		}
	}

	protected void playerWins(Player player, PlayerHand hand, int amountWon,
	    int newBankroll) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerWins(player, hand, amountWon, newBankroll);
		}
	}

	protected void playerLoses(Player player, PlayerHand hand, int amountLost,
	    int newBankroll) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerLoses(player, hand, amountLost, newBankroll);
		}
	}

	protected void playerBlackjack(Player player, PlayerHand hand, int amountWon,
	    int newBankroll) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerBlackjack(player, hand, amountWon, newBankroll);
		}
	}

	protected void playerPush(Player player, PlayerHand hand, int heldBankroll) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerPush(player, hand, heldBankroll);
		}
	}

	protected void playerWinsInsurance(Player player, int amountWon,
	    int newBankroll) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerWinsInsurance(player, amountWon, newBankroll);
		}
	}

	protected void playerLosesInsurance(Player player, int amountLost,
	    int newBankroll) {
		for (TableObserver tableObserver : tableObservers) {
			tableObserver.playerLosesInsurance(player, amountLost, newBankroll);
		}
	}
}
