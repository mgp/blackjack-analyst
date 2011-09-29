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

package blackjackanalyst.strategy;

import java.util.Iterator;

import blackjackanalyst.ObserverList;
import blackjackanalyst.Player;
import blackjackanalyst.Table;
import blackjackanalyst.TableObserver;
import blackjackanalyst.observer.ConsoleTableObserver;
import blackjackanalyst.observer.WinLossTableObserver;

/**
 * Allows creation of a table with players using the hi-lo system and a true 
 * count to adjust basic strategy.
 * 
 * @author Michael Parker
 */
public class ModifiedBasicStrategyTable {
	public static void main(String[] args) {
		// ensure enough arguments
		if (args.length < 2) {
			System.err.println("Usage: java ModifiedBasicStrategyTable [verbose] <num_rounds> "
					+ "<player name> <player name> ... <player name>");
			System.exit(1);
		}

		// create table with default dealer strategy
		Table advanced_table = new Table(
				"Advanced Table", 6, DefaultDealer.getInstance(), 20, 200);
		ObserverList<TableObserver> table_obs = advanced_table.getObservers();

		int next_arg = 0;
		if (args[0].equals("verbose")) {
			// add table observer to print to console
			table_obs.add(ConsoleTableObserver.getInstance());
			++next_arg;
		}
		// add table observer to gather statistics
		WinLossTableObserver wlto = new WinLossTableObserver();
		table_obs.add(wlto);
		
		// get number of rounds to play
		int num_rounds = Integer.parseInt(args[next_arg++]);
		if (num_rounds <= 0) {
			System.err.println("Error: number of rounds played must be positive");
			System.exit(1);
		}

		// add players to table
		for (int i = next_arg; i < args.length; ++i) {
			String player_name = args[i];
			Player new_player = new Player(new ModifiedBasicStrategyPlayer(), player_name);
			advanced_table.addPlayer(new_player);
			new_player.setBankroll(100000);
		}

		// play rounds of blackjack
		advanced_table.playRounds(num_rounds);
		
		// print summary statistics
		System.out.println("\n" + wlto);
		for (Iterator<Player> i = advanced_table.getPlayers().iterator(); i.hasNext(); ) {
			System.out.println(i.next());
		}
	}
}
