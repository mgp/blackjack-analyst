package blackjackanalyst;

import junit.framework.TestCase;
import blackjackanalyst.Card.Rank;
import blackjackanalyst.Card.Suit;

/**
 * Test for {@link Card}.
 */
public class CardTestCase extends TestCase {
	public void testGetCard() {
		Card[] cards = new Card[Card.CARDS_PER_DECK];
		int i = 0;
		for (Suit suit : Card.Suit.values()) {
			for (Rank rank : Card.Rank.values()) {
				Card card = Card.getCard(rank, suit);
				assertEquals(suit, card.getSuit());
				assertEquals(rank, card.getRank());
				cards[i] = card;
				++i;
			}
		}
		
		// Assert that each card is a singleton instance.
		i = 0;
		for (Suit suit : Card.Suit.values()) {
			for (Rank rank : Card.Rank.values()) {
				Card card = Card.getCard(rank, suit);
				assertSame(cards[i], card);
				++i;
			}
		}
	}
}
