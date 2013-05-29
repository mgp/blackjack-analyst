package blackjackanalyst;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import blackjackanalyst.Card.Rank;
import blackjackanalyst.Card.Suit;

/**
 * Test for {@link Shoe}.
 */
public class ShoeTestCase extends TestCase {
	public void testShuffle() {
		int numDecks = 6;
		Shoe shoe = new Shoe(numDecks);
		shoe.shuffle();
		
		// Count the occurrences of each card in the shoe.
		Map<Card, Integer> cardCounts = new HashMap<Card, Integer>();
		for (Card card : shoe.cards) {
			Integer count = cardCounts.get(card);
			if (count == null) {
				count = 0;
			}
			cardCounts.put(card, count + 1);
		}
		// Each card should have one instance from each deck.
		assertEquals(Card.CARDS_PER_DECK, cardCounts.size());
		for (Suit suit : Card.Suit.values()) {
			for (Rank rank : Card.Rank.values()) {
				Card card = Card.getCard(rank, suit);
				int count = cardCounts.get(card);
				assertEquals(numDecks, count);
			}
		}
	}
}
