/*
 * welters
 * Copyright (C) 2020-2021 Aaron Wang
 *
 * This file is part of welters.jar.
 *
 * welters.jar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * welters.jar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this welters.jar.  If not, see <http://www.gnu.org/licenses/>
 */
package welters;

public class Game{
    static Deck deck;
    static Map map;
    static int[] scores = new int[2];

    private static Hand[] hands = new Hand[2];
    

    
    static int currentPlayer;
    static int boardPhase;

    public static void main(String[] args){
	initializeGame();
	currentPlayer = 0;
	boardPhase = 0;
	gameTurn(0);
	hands[0].plenishHand();
	while (hands[0].getSize() != 0 || hands[1].getSize() != 0){
	    currentPlayer = 1 - currentPlayer;
	    gameTurn(currentPlayer);
	}
	if (scores[0] > scores[1]){
	    hands[0].end(1);
	    hands[1].end(-1);
	}
	if (scores[0] == scores[1]){
	    hands[0].end(0);
	    hands[1].end(0);
	}
	if (scores[0] < scores[1]){
	    hands[0].end(-1);
	    hands[1].end(1);
	}
	map.end();
	System.exit(0); //oof
    }

    public static void initializeGame(){
	deck = new Deck();
	map = new Map();
	hands[0] = new Hand(true, 0);
	hands[1] = new Hand(false, 1);
    }

    public static void gameTurn(int player){
	int[] choice = hands[player].guiHandPicker(); // up to three card numbers in the hand and -1s
	boardPhase = 1;
	int[] tile = map.guiBoardPicker();
	hands[player].blankOut(); //Todo only blank out nonselected cards?
	int[] cardValues = new int[3];
	for (int i = 0; i < 3; i++){
	    if (choice[i] != -1) cardValues[i] = hands[player].getCard(choice[i]).getValue();
	    else cardValues[i] = -1;
	}
	
        map.capture(cardValues, tile[0], tile[1]);
	boardPhase = 0;
	for(int i : choice){
	    if (i != -1) hands[player].replaceCard(i);
	}


    }

    
    public static int score(int player){
	return 0;
    }

    public static int getCurrentPlayer(){
	return currentPlayer;
    }



}
