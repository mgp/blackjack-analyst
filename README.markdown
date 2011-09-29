Blackjack Analyst
=================

Blackjack Analyst allows evaluation of blackjack playing strategies through extensive simulation.

This code was previously hosted at http://www.sourceforge.net/projects/bjanalyst. (Unfortunate name, I know.)

How can I write my own strategies?
----------------------------------

Limited knowledge of Java is required. To write dealer (or house) strategies, implement the `DealerStrategy` interface. To write player strategies, consult the `PlayerStrategy` interface.

What strategies are included?
-----------------------------

Blackjack Analyst comes included with four player strategies:

* `DefaultPlayer`: Mimics the default dealer strategy by hitting on soft 17 or lower.
* `BasicStrategyPlayer`: Uses basic strategy for deciding turn actions, and always makes the minimum bet.
* `TrueCountBetPlayer`: Uses basic strategy and adjusts his bet according to the hi-lo count.
* `ModifiedBasicStrategyPlayer`: Uses the hi-lo count to adjust his bet and modify basic strategy accordingly.

Only one default dealer strategy is included, called `DefaultDealer`, which hits on soft 17 or lower.

How can I simulate these strategies?
------------------------------------

Mixing and matching strategies requires basic knowledge of Java. To get you started, three executable class files are included: `BasicStrategyTable`, `TrueCountBetTable`, and `ModifiedBasicStrategyTable`. Each creates a table of players abiding by the `BasicStrategyPlayer`, `TrueCountBetPlayer`, and `ModifiedBasicStrategyPlayer` strategies, respectively. The command line options for each is the same.

To play 10000 rounds of blackjack with five players (Mike, Matt, Dustin, Tim, and Chris) abiding by `BasicStrategy` and summarize the results:

    java BasicStrategyTable 10000 Mike Matt Dustin Tim Chris

    W: 20162, L: 24566, BJ: 2317, P: 4489, IW: 0, IL: 0, net = 410
    Player Mike: bankroll = 98470, table = Basic Table
    Player Matt: bankroll = 96250, table = Basic Table
    Player Dustin: bankroll = 100580, table = Basic Table
    Player Tim: bankroll = 101090, table = Basic Table
    Player Chris: bankroll = 104020, table = Basic Table

Above, `W` is for player wins, `L` is for losses, `BJ` is for blackjacks, `P` is for pushes, `IW` is the number of wins on insurance, `IL` is the number of losses on insurance. (Note that basic strategy never takes insurance.) The number following `net` reflects the aggregate winnings of the players. The new individual bankrolls of each player follows.

To play only 1 round of blackjack with the same players abiding by `BasicStrategy`, but print all output using the `verbose` flag:

    java BasicStrategyTable verbose 1 Mike Matt Dustin Tim Chris

    Mike joins the table
    Matt joins the table
    Dustin joins the table
    Tim joins the table
    Chris joins the table

    New round started with 5 players
    Mike bets 20
    Matt bets 20
    Dustin bets 20
    Tim bets 20
    Chris bets 20
    Mike is dealt EIGHT of HEARTS, NINE of DIAMONDS (17)
    Matt is dealt SEVEN of CLUBS, TWO of SPADES (9)
    Dustin is dealt SEVEN of DIAMONDS, FIVE of HEARTS (12)
    Tim is dealt EIGHT of SPADES, FOUR of SPADES (12)
    Chris is dealt FOUR of DIAMONDS, QUEEN of CLUBS (14)
    The dealer shows the up card THREE of DIAMONDS
    Mike stands with hand EIGHT of HEARTS, NINE of DIAMONDS (17)
    Matt doubles down, now has hand SEVEN of CLUBS, TWO of SPADES, EIGHT of SPADES (17)
    Dustin hits, now has hand SEVEN of DIAMONDS, FIVE of HEARTS, QUEEN of CLUBS (22)
    Dustin busts with hand SEVEN of DIAMONDS, FIVE of HEARTS, QUEEN of CLUBS (22), lost 20
    Tim hits, now has hand EIGHT of SPADES, FOUR of SPADES, KING of HEARTS (22)
    Tim busts with hand EIGHT of SPADES, FOUR of SPADES, KING of HEARTS (22), lost 20
    Chris stands with hand FOUR of DIAMONDS, QUEEN of CLUBS (14)
    The dealer reveals its down card, has hand THREE of DIAMONDS, TEN of HEARTS (13)
    The dealer hits, now has hand THREE of DIAMONDS, TEN of HEARTS, THREE of HEARTS (16)
    The dealer hits, now has hand THREE of DIAMONDS, TEN of HEARTS, THREE of HEARTS, SIX of HEARTS (22)
    The dealer busts with hand THREE of DIAMONDS, TEN of HEARTS, THREE of HEARTS, SIX of HEARTS (22)
    Mike beat dealer with 17, won 20
    Matt beat dealer with 17, won 40
    Chris beat dealer with 14, won 20

    W: 3, L: 2, BJ: 0, P: 0, IW: 0, IL: 0, net = 40
    Player Mike: bankroll = 100020, table = Basic Table
    Player Matt: bankroll = 100040, table = Basic Table
    Player Dustin: bankroll = 99980, table = Basic Table
    Player Tim: bankroll = 99980, table = Basic Table
    Player Chris: bankroll = 100020, table = Basic Table

How can I change the house rules?
---------------------------------

The customizable dealer strategy allows the dealer to decide when to hit or stand, but does not affect any other house rules. These rules, by default, are to deal from a shoe containing eight decks, allow doubling down on any two cards, allow doubling down after splitting, allow re-splitting of any pair, and offer insurance. (The included player strategies reflect this.) Only the maximum number of players, the minimum and maximum bets, and the aforementioned dealer strategy may be adjusted when creating a `Table` object.
