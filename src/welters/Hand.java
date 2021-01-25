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

import java.awt.Font;

class Hand implements DrawListener{
    private CircumstanceCard[] hand = new CircumstanceCard[8];
    
    private int size = 0;
    private Draw gui = new Draw();
    private final Font bigFont = new Font("SANS_SERIF",0,80);
    private final Font smallFont = new Font("SANS_SERIF",0,24);
    private final Font cardFont = new Font("SANS_SERIF",0,20);
    private final Font endFont = new Font("SANS_SERIF",0,120);
    private final int player;

    private volatile boolean choosing;
    private int[] choice = new int[3];
    
    Hand(boolean firstHand, int play){
	for(int i = 0; i < ((firstHand)? 5 : 8); i++){
	    hand[i] = Game.deck.draw();
	}
	size = (firstHand)? 5 : 8;
	player = play;

	gui.enableDoubleBuffering();
	gui.setCanvasSize(900,160);
	gui.setXscale(0,900);
	gui.setYscale(0,160);
	gui.addListener(this);
	gui.clear(Draw.BLACK);
	gui.setPenColor(Draw.WHITE);
	gui.setFont(smallFont);
	gui.text(850,140,"PLAYER");
	gui.setFont(bigFont);
	gui.setPenColor((player == 0)? Draw.GREEN : Draw.PINK);
	gui.text(850,80,(player == 0)? "1" : "2");
	gui.show();
    }


    int getSize(){
	return size;
    }
    
    CircumstanceCard getCard(int i){
	return hand[i];
    }

    void replaceCard(int i){
	if (Game.deck.getIndex() < 105) hand[i] = Game.deck.draw();
	else hand[i] = null;
	
	boolean empty = true;
	for (int j = 0; j < size; j++) empty = empty && (hand[j] == null);
	if (empty) size = 0;
	Game.map.guiDeck();
    }
    void plenishHand(){
	if (size == 5) {
	    for(int j = 5; j < 8; j++){
		hand[j] = Game.deck.draw();
	    }
	    size = 8;
	}
	Game.map.guiDeck();
    }
    int[] guiHandPicker(){


	gui.setPenColor(Draw.BLUE);
	gui.rectangle(850,30,45,20);
	gui.setPenColor(Draw.WHITE);
	gui.setFont(smallFont);
	gui.text(850,26,"PLAY");

	

	
	for(int i = 0; i < size; i++){
	    guiCard(i);
	}
	gui.show();
	
	
	choosing = true;
	choice[0] = -1;
	choice[1] = -1;
	choice[2] = -1;

	while (choosing) gui.pause(10);
	
	gui.setPenColor(Draw.BLACK);
	gui.filledRectangle(850,30,50,25);
	gui.show();
	return choice;
	
    }
    
    void guiCard(int i){
	if (hand[i] == null) return;
	gui.setPenColor(Draw.DARK_GRAY);
	gui.filledRectangle(100 * i + 50, 80, 50, 80);
	gui.setPenColor(Draw.YELLOW);
	gui.rectangle(100 * i + 50, 80, 45, 75);
	gui.line(100 * i + 15, 60, 100 * i + 50, 121);
	gui.line(100 * i + 85, 60, 100 * i + 50, 121);
	gui.line(100 * i + 15, 60, 100 * i + 85, 60);
	gui.setFont(cardFont);
	if (hand[i].getType() == 0){
	    gui.setPenColor(Draw.RED);
	    gui.text(100 * i + 50, 92, hand[i].getValue() + "");
	}
	if (hand[i].getType() == 1){
	    gui.setPenColor(Draw.ORANGE);
	    gui.text(100 * i + 35, 69, hand[i].getValue() + "");
	}
	if (hand[i].getType() == 2){
	    gui.setPenColor(Draw.CYAN);
	    gui.text(100 * i + 65, 69, hand[i].getValue() + "");
	}
    }

    void blankOut(){
	gui.clear(Draw.BLACK); 
	gui.setPenColor(Draw.WHITE);
	gui.setFont(smallFont);
	gui.text(850,140,"PLAYER");
	gui.setFont(bigFont);
	gui.setPenColor((player == 0)? Draw.GREEN : Draw.PINK);
	gui.text(850,80,(player == 0)? "1" : "2");
	gui.show();
    }
    
    public void mousePressed(double x, double y){
	if (player != Game.getCurrentPlayer()) return;
	if (Game.boardPhase == 1) return;
	int cardNumber = (int)(gui.mouseX() / 100);
	if (cardNumber < size){
	    if (hand[cardNumber] == null) return;
	    int type = hand[cardNumber].getType();
	    if  (choice[type] == cardNumber) {
	    	choice[type] = -1;
	    	gui.setPenColor(Draw.YELLOW);
	    	gui.rectangle(100 * cardNumber + 50, 80, 45, 75);
	    }
	    else if (choice[type] == -1){
		choice[type] = cardNumber;
	    	gui.setPenColor(Draw.MAGENTA);
	    	gui.rectangle(100 * cardNumber + 50, 80, 45, 75);
	    }
	}
	else if (gui.mouseX() > 805 && gui.mouseX() < 895 && gui.mouseY() > 10 && gui.mouseY() < 50) choosing = false;
	gui.show();
    }

    void end(int result){
	gui.setPenColor(Draw.WHITE);
	gui.setFont(endFont);
	if (result == -1){
	    gui.text(400,65,"YOU LOSE");
	}
	if (result == 0){
	    gui.text(400,65,"DRAW");
	}
	if (result == 1){
	    gui.text(400,65,"YOU WIN");
	}
	gui.show();
    }


    public void mouseDragged(double x, double y){}
    public void mouseReleased(double x, double y){}
    public void mouseClicked(double x, double y){}
    public void keyTyped(char c){}
    public void keyPressed(int keycode){}
    public void keyReleased(int keycode){}
}
