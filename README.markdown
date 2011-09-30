Blackjack Analyst
=================

Blackjack Analyst allows evaluation of blackjack playing strategies through extensive simulation.

This code was previously hosted at http://www.sourceforge.net/projects/bjanalyst. (Unfortunate URL, I know.)

Writing custom strategies
-------------------------

To write dealer (or house) strategies, implement the `DealerStrategy`
interface. To write player strategies, implement the `PlayerStrategy`
interface.

Included strategies
-------------------

Blackjack Analyst comes included with three `PlayerStrategy` implementations:

* `DefaultPlayerStrategy`: Mimics the default dealer strategy by hitting on
  soft 17 or lower.
* `BasicPlayerStrategy`: Uses basic strategy for deciding turn actions, and
  always makes the minimum bet.
* `TrueCountPlayerStrategy`: Uses basic strategy and adjusts his bet according
  to the hi-lo count.

Only one default dealer strategy is included, called `DefaultDealer`, which
hits on soft 17 or lower.

Simulator options
-----------------

Mixing and matching strategies requires basic knowledge of Java. To get you
started, the `Simulator` class has a `main` method that will create a table and
simulate players all executing a specified strategy. It accepts the following
command line arguments:

* `num_rounds`: The number of rounds of Blackjack to play.
* `player_names`: A comma-separated list specifying the player names.
* `strategy`: The player strategy to use. Valid values are `default`, `basic`,
  and `true_count` which select `DefaultPlayerStrategy`, `BasicPlayerStrategy`,
  and `TrueCountPlayerStrategy` respectively.
* `verbose`: Prints detailed information about every round. Not recommended if
  simulating many rounds.

Simulator output
----------------

When the simulator has completed, `W` is for player wins, `L` is for losses,
`BJ` is for blackjacks, `P` is for pushes, `IW` is the number of wins on
insurance, `IL` is the number of losses on insurance. (Note that basic strategy
never takes insurance.) The number following `net` reflects the aggregate
winnings of the players. The new individual bankrolls of each player follows.

#### True count strategy

Command line arguments:

    --num_rounds=50000 --player_names=Dustin,Ely,Mike,Tim --strategy=true_count

Output:

    W=81257, L=98174, BJ=8945, P=17582, IW=236, IL=568, net=37370
    Player name=Dustin: bankroll=123520, table=Table1
    Player name=Ely: bankroll=106710, table=Table1
    Player name=Mike: bankroll=105100, table=Table1
    Player name=Tim: bankroll=102040, table=Table1

#### Basic strategy

Command line arguments:

    --num_rounds=50000 --player_names=Dustin,Ely,Mike,Tim --strategy=basic

Output:

    W=80133, L=99162, BJ=9035, P=17581, IW=0, IL=0, net=-37590
    Player name=Dustin: bankroll=85170, table=Table1
    Player name=Ely: bankroll=88040, table=Table1
    Player name=Mike: bankroll=91710, table=Table1
    Player name=Tim: bankroll=97490, table=Table1

#### Default strategy

Command line arguments:

    --num_rounds=50000 --player_names=Dustin,Ely,Mike,Tim --strategy=default

Output:

    W=73447, L=98467, BJ=8945, P=19141, IW=0, IL=0, net=-232050
    Player name=Dustin: bankroll=41470, table=Table1
    Player name=Ely: bankroll=41640, table=Table1
    Player name=Mike: bankroll=41140, table=Table1
    Player name=Tim: bankroll=43700, table=Table1

#### Verbose output

Command line arguments, specifying only one round:

    --num_rounds=1 --player_names=Dustin,Ely,Mike,Tim --strategy=basic --verbose

Output:

    Dustin joins the table
    Ely joins the table
    Mike joins the table
    Tim joins the table

    New round started with 4 players
    Dustin bets 20
    Ely bets 20
    Mike bets 20
    Tim bets 20
    Dustin is dealt THREE of DIAMONDS, QUEEN of SPADES (13)
    Ely is dealt SEVEN of HEARTS, QUEEN of DIAMONDS (17)
    Mike is dealt ACE of CLUBS, TWO of CLUBS (13)
    Tim is dealt THREE of DIAMONDS, ACE of CLUBS (14)
    The dealer shows the up card SIX of DIAMONDS
    Dustin stands with hand THREE of DIAMONDS, QUEEN of SPADES (13)
    Ely stands with hand SEVEN of HEARTS, QUEEN of DIAMONDS (17)
    Mike doubles down, now has hand ACE of CLUBS, TWO of CLUBS, JACK of HEARTS (13)
    Tim doubles down, now has hand THREE of DIAMONDS, ACE of CLUBS, SEVEN of DIAMONDS (21)
    The dealer reveals its down card, has hand SIX of DIAMONDS, SIX of CLUBS (12)
    The dealer hits, now has hand SIX of DIAMONDS, SIX of CLUBS, SEVEN of CLUBS (19)
    The dealer stands with hand SIX of DIAMONDS, SIX of CLUBS, SEVEN of CLUBS (19)
    Dustin lost to dealer with 13, lost 20
    Ely lost to dealer with 17, lost 20
    Mike lost to dealer with 13, lost 40
    Tim beat dealer with 21, won 40

    W=1, L=3, BJ=0, P=0, IW=0, IL=0, net=-40
    Player name=Dustin: bankroll=99980, table=Table1
    Player name=Ely: bankroll=99980, table=Table1
    Player name=Mike: bankroll=99960, table=Table1
    Player name=Tim: bankroll=100040, table=Table1

