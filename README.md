# welters

### General Instructions

To run this software:

Make sure you have a sufficiently modern version of Java installed, anything
11 (which I used to compile) and up is fine I think. Then, open the .jar file 
you want to run; if that doesn't work for whatever reason you can also use

 java -jar NAME.jar

in the command line, after moving to the directory this file is in.

### Welters (2P):

Based off the game of the same name in the The Magicians series, your task
is to capture a bunch of tiles on the game board to prove yourself the better
magician. Success of each cast will be determined by how closely you match the
circumstances on the cards you play with the circumstances on the target tile.

#### Interface:

Three windows will appear when you run this game, one is the Map and the
other two are each player's Hands. 

The Map holds the tiles to be captured.

![The Map Interface](https://github.com/MrCocoNuat/welters/blob/main/assets/map.png)

Cards show up in the hands, along with the
player indicator. When it is your turn, the other player's hand is hidden.

![The Hand Interaces](https://github.com/MrCocoNuat/welters/blob/main/assets/hands.png)

*DO NOT CLOSE THE WINDOWS WHILE THE GAME IS IN PROGRESS* or you will be unable
to continue playing, and the game will still be open in the background! At this
point, you should kill the process and start a new game if you want. Of course,
if you intend to quit the game, go ahead and kill the process. The windows will
automatically close. 

The board window also has some extra information: in the bottom left, the
current scores are displayed. The bottom right shows the number of cards left
in the deck. The deck starts out at 105 cards, but 13 are immediately dealt,
so 92 remain at the beginning of the game. Every game has the exact same deck,
shuffled in some order.

#### Gameplay:

Player 1 goes first; player 1 will only have 5 cards in the hand the first
turn, but every subsequent turn both players start their turn with 8 cards.

To play cards, just click on them to select, and click on them again to
unselect. You can only select up to 1 card of each type (red, yellow, blue).
When you are satisfied with your selection, click the PLAY button to continue.

Now you have to click a tile to capture. Clicking a tile selects it, clicking
again will unselect it. You cannot select a tile you already own. The colored
border indicates the grade of tile: blue/water, tan/sand, green/grass,
gray/stone, white/metal. 

![A Water Tile](https://github.com/MrCocoNuat/welters/blob/main/assets/water.png)

![A Sand Tile](https://github.com/MrCocoNuat/welters/blob/main/assets/sand.png)

![A Grass Tile](https://github.com/MrCocoNuat/welters/blob/main/assets/grass.png)

![A Stone Tile](https://github.com/MrCocoNuat/welters/blob/main/assets/stone.png)

![A Metal Tile](https://github.com/MrCocoNuat/welters/blob/main/assets/metal.png)

As the grade increases, the allowed error in casting
decreases, but the score the tile grants increases. Click CAST when you are
satisfied.

If you use cards that exactly match the target tile, the perfect cast is
guaranteed to capture successfully no matter what.

Otherwise, to decide whether the capture is successful, the game will compare
the values on the cards you picked against the values on the tile.
The difference between the two is calculated three times, one for each type,
then summed as the error. If you did not play a card of a certain type, then
10 will be added to the error for that type. Next, a uniform random roll from
1 to 12 is added to the error, to represent a random proficiency in casting.
Then, if the opponent currently owns the tile you are capturing, another 1-12
roll is added, which represents a counter to complicate your casting. Finally,
for every tile orthogonally adjacent to the target you also own, 2 is
subtracted from the error, as a sort of concentration of magic that makes your
job simpler. If the error value is still below the tolerance of the tile grade,
you successfully capture the tile, and the inner triangle will change its
color accordingly. Otherwise, the board does not change.

Tolerances: 

Water 54 (essentially infinite, as there is no way to get an error
higher than that), 

Sand 21, 

Grass 15, 

Stone 12, 

Metal 9.

The picture below shows an uncaptured tile, a tile captured by player 2, and a tile captured by player 1.
![Captured Tiles](https://github.com/MrCocoNuat/welters/blob/main/assets/owning.png)

Either way, your cards are now spent; draw new cards until you reach 8 again.
When the deck reaches 0 cards left, no new cards can be drawn, so your hand
will have less cards the following turn.

The game ends when the last card is used and both hands are empty. The score
for each player is calculated by adding up the score for each tile they have
captured; the winner has the higher score.

Scores:

Water 1, 

Sand 2, 

Grass 4, 

Stone 8, 

Metal 12.

After this, the game will automatically kill itself in 30 seconds, so you can
safely close the windows. If you want to replay, just re-open the .jar.
