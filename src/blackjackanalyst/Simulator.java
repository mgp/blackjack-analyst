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

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import blackjackanalyst.observer.ConsoleTableObserver;
import blackjackanalyst.observer.WinLossTableObserver;
import blackjackanalyst.strategy.BasicPlayerStrategy;
import blackjackanalyst.strategy.DefaultDealerStrategy;
import blackjackanalyst.strategy.DefaultPlayerStrategy;
import blackjackanalyst.strategy.TrueCountPlayerStrategy;

/**
 * Class that performs the simulation of the specified strategy over the
 * specified number of rounds. The available command line arguments are as
 * follows:
 * 
 * <ul>
 * <li>--num_rounds: Specifies the number of rounds of Blackjack to play.</li>
 * <li>--player_names: A comma-separated list of the names of the players.</li>
 * <li>--verbose: If present, detailed information about each hand will be
 * displayed, instead of simply a summary at the end.</li>
 * <li>--strategy: Must be either the values <code>DEFAULT</code>,
 * <code>BASIC</code>, or <code>TRUE_COUNT</code>, which use
 * <code>DefaultPlayerStrategy</code>, <code>BasicPlayerStrategy</code>, or
 * <code>TrueCountPlayerStrategy</code>, respectively.
 * </ul>
 * 
 * @author Michael Parker
 */
public class Simulator {
	private static class ParsedArguments {
		enum StrategyType {
			DEFAULT {
				public PlayerStrategy playerStrategy() {
					return new DefaultPlayerStrategy();
				}
			},
			BASIC {
				public PlayerStrategy playerStrategy() {
					return new BasicPlayerStrategy();
				}
			},
			TRUE_COUNT {
				public PlayerStrategy playerStrategy() {
					return new TrueCountPlayerStrategy();
				}
			};

			public abstract PlayerStrategy playerStrategy();
		}

		int numRounds;
		List<String> playerNames;
		boolean verbose;
		StrategyType strategyType;

		ParsedArguments(int numRounds, List<String> playerNames, boolean verbose,
		    StrategyType strategyType) {
			this.numRounds = numRounds;
			this.playerNames = playerNames;
			this.verbose = verbose;
			this.strategyType = strategyType;
		}
	}

	private static final String NUM_ROUNDS_ARGUMENT_PREFIX = "--num_rounds=";
	private static final String VERBOSE_ARGUMENT = "--verbose";
	private static final String PLAYER_NAMES_ARGUMENT_PREFIX = "--player_names=";
	private static final String STRATEGY_ARGUMENT_PREFIX = "--strategy=";

	private static ParsedArguments parseArgs(String[] args) {
		int numRounds = 0;
		List<String> playerNames = new LinkedList<String>();
		boolean verbose = false;
		ParsedArguments.StrategyType strategyType = ParsedArguments.StrategyType.DEFAULT;

		for (String arg : args) {
			if (arg.startsWith(NUM_ROUNDS_ARGUMENT_PREFIX)) {
				arg = arg.substring(NUM_ROUNDS_ARGUMENT_PREFIX.length());
				numRounds = Integer.valueOf(arg).intValue();
			} else if (arg.equals(VERBOSE_ARGUMENT)) {
				verbose = true;
			} else if (arg.startsWith(PLAYER_NAMES_ARGUMENT_PREFIX)) {
				arg = arg.substring(PLAYER_NAMES_ARGUMENT_PREFIX.length());
				StringTokenizer tokenizer = new StringTokenizer(arg, ",");
				while (tokenizer.hasMoreTokens()) {
					String playerName = tokenizer.nextToken();
					if (playerName.length() > 0) {
						playerNames.add(playerName);
					}
				}
			} else if (arg.startsWith(STRATEGY_ARGUMENT_PREFIX)) {
				arg = arg.substring(STRATEGY_ARGUMENT_PREFIX.length()).toUpperCase();
				if (arg.equals(ParsedArguments.StrategyType.DEFAULT.toString())) {
					strategyType = ParsedArguments.StrategyType.DEFAULT;
				} else if (arg.equals(ParsedArguments.StrategyType.BASIC.toString())) {
					strategyType = ParsedArguments.StrategyType.BASIC;
				} else if (arg.equals(ParsedArguments.StrategyType.TRUE_COUNT
				    .toString())) {
					strategyType = ParsedArguments.StrategyType.TRUE_COUNT;
				}
			} else {
				throw new IllegalArgumentException(
				    "Unrecognized command line argument: " + arg);
			}
		}
		if (numRounds <= 0) {
			throw new IllegalArgumentException(
			    "Requires --num_rounds argument with positive integer");
		}
		if (playerNames.isEmpty()) {
			throw new IllegalArgumentException(
			    "Requires --names argument with at least one name");
		}

		return new ParsedArguments(numRounds, playerNames, verbose, strategyType);
	}

	public static void main(String[] args) {
		ParsedArguments parsedArguments = Simulator.parseArgs(args);

		Table advancedTable = new Table("Table1", 6, DefaultDealerStrategy.INSTANCE, 20,
		    200);
		ObserverList<TableObserver> tableObservers = advancedTable.getObservers();

		// Add table observer to gather statistics.
		WinLossTableObserver winLossObserver = new WinLossTableObserver();
		tableObservers.add(winLossObserver);
		// Add table observer to print details to console if --verbose is specified.
		if (parsedArguments.verbose) {
			tableObservers.add(ConsoleTableObserver.getInstance());
		}

		// Add players to the table.
		for (String playerName : parsedArguments.playerNames) {
			Player player = new Player(parsedArguments.strategyType.playerStrategy(),
			    playerName);
			advancedTable.addPlayer(player);
			player.setBankroll(100000);
		}

		// Simulate rounds of blackjack.
		advancedTable.playRounds(parsedArguments.numRounds);

		// Print summary statistics.
		System.out.println("\n" + winLossObserver);
		for (Player player : advancedTable.getPlayers()) {
			System.out.println(player);
		}
	}
}
