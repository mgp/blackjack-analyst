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

import blackjackanalyst.DealerStrategy;
import blackjackanalyst.Hand;

/**
 * The default dealer strategy, which hits on any value below 16 or soft 17. On
 * any other value, the dealer stands.
 * 
 * @author Michael Parker
 */
public class DefaultDealer implements DealerStrategy {
	protected static DefaultDealer _instance;

	public static DefaultDealer getInstance() {
		if (_instance == null) {
			_instance = new DefaultDealer();
		}
		return _instance;
	}

	protected DefaultDealer() {
	}

	public DealerStrategyAction getAction(Hand dealer_hand) {
		int best_value = dealer_hand.getHighValidValue();
		if ((best_value > 17) || ((best_value == 17) && !dealer_hand.isSoft())) {
			return DealerStrategyAction.STAND;
		}
		return DealerStrategyAction.HIT;
	}
}
