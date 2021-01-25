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
import java.awt.Color;

class Map implements DrawListener{
    private MapTile[][] map = new MapTile[7][5];
    private final int[] tolerances = {54,21,15,12,9};
    private final int[] scores = {1,2,4,8,12};


    private Draw gui = new Draw();
    private final Font cardFont = new Font("SANS_SERIF",0,20);
    private final Font scoreFont = new Font("SANS_SERIF",0,50);
    private final Font bigFont = new Font("SANS_SERIF",0,40);
    private final Color[] typeColor = new Color[5]; //standard color sets are just not doing it


    private volatile boolean choosing;
    private int[] choice = new int[2];
    
    Map(){
	int[] types = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,3,3,3,4,4,4};
	for(int i = 0; i < 45; i++){
	    int swap = i + (int) ((45 - i) * Math.random());
	    int temp = types[i];
	    types[i] = types[swap];
	    types[swap] = temp;
	}
	int index = 0;
	for(int i = 0; i < 7; i++){
	    for(int j = 0; j < 5; j++){
		map[i][j] = new MapTile(types[index]);
		index++;
	    }
	}

	typeColor[0] = new Color(95,95,255);
	typeColor[1] = new Color(255,239,127);
	typeColor[2] = new Color(63,255,63);
	typeColor[3] = new Color(127,127,127);
	typeColor[4] = new Color(255,255,255);

	gui.enableDoubleBuffering();
	gui.setCanvasSize(500,800);
	gui.setXscale(0,500);
	gui.setYscale(0,800);
	gui.addListener(this);
	gui.clear(Draw.BLACK);
	for(int i = 0; i < 7; i++){
	    for(int j = 0; j < 5; j++){
		guiTile(i,j);
	    }
	}
	gui.setPenColor(Draw.DARK_GRAY);
	gui.filledRectangle(450,50,25,40);
	gui.setPenColor(Draw.YELLOW);
	gui.rectangle(450,50,22.5,37.5);
	gui.line(432.5, 40, 450, 70.5);
	gui.line(467.5, 40, 450, 70.5);
	gui.line(432.5, 40, 467.5, 40);
	gui.setPenColor(Draw.WHITE);
	gui.setFont(cardFont);
	gui.text(450,50,"92"); //don't really need a hardcode here, but might as well save like 5 CPU cycles...
	gui.setFont(scoreFont);
	gui.setPenColor(Draw.GREEN);
	gui.text(50,45,Game.scores[0] + "");
	gui.setPenColor(Draw.PINK);
	gui.text(150,45,Game.scores[1] + "");
	gui.show();
    }

    void guiDeck(){
	gui.setPenColor(Draw.BLACK);
	gui.filledRectangle(450,50,30,45);
	gui.setPenColor(Draw.DARK_GRAY);
	gui.filledRectangle(450,50,25,40);
	gui.setPenColor(Draw.YELLOW);
	gui.rectangle(450,50,22.5,37.5);
	gui.line(432.5, 40, 450, 70.5);
	gui.line(467.5, 40, 450, 70.5);
	gui.line(432.5, 40, 467.5, 40);
	gui.setPenColor(Draw.WHITE);
	gui.setFont(cardFont);
	gui.text(450,50,105 - Game.deck.getIndex() + "");
	gui.show();
    }
    
    int[] guiBoardPicker(){
	
	
	gui.setPenColor(Draw.BLUE);
	gui.rectangle(250,50,60,25);
	gui.setPenColor(Draw.DARK_GRAY);
	gui.setFont(bigFont);
	gui.text(250,43,"CAST");
	gui.show();
	
	choosing = true;
	choice[0] = -1;
	choice[1] = -1;

	while (choosing) gui.pause(10);
	    
	gui.clear(Draw.BLACK);
	for(int i = 0; i < 7; i++){
	    for(int j = 0; j < 5; j++){
		guiTile(i,j);
	    }
	}
	guiDeck();
	gui.setFont(scoreFont);
	gui.setPenColor(Draw.GREEN);
	gui.text(50,45,Game.scores[0] + "");
	gui.setPenColor(Draw.PINK);
	gui.text(150,45,Game.scores[1] + "");
	gui.show();
	int[] ans = {choice[1],choice[0]};
	return ans;
    }

    void guiTile(int i, int j){
	MapTile tile = map[i][j];
	gui.setPenColor((tile.getOwner() == -1)? Draw.YELLOW : ((tile.getOwner() == 0)? Draw.GREEN : Draw.PINK));
	gui.line(100 * j + 85, 725 - 100 * i, 100 * j + 50, 786 - 100 * i);
	gui.line(100 * j + 15, 725 - 100 * i, 100 * j + 50, 786 - 100 * i);
	gui.line(100 * j + 85, 725 - 100 * i, 100 * j + 15, 725 - 100 * i);

	gui.setFont(cardFont);
	int[] circs = tile.getCircs();
	gui.setPenColor(Draw.RED);
	gui.text(100 * j + 50, 757 - 100 * i, circs[0] + "");
	gui.setPenColor(Draw.ORANGE);
	gui.text(100 * j + 35, 734 - 100 * i, circs[1] + "");
	gui.setPenColor(Draw.CYAN);
	gui.text(100 * j + 65, 734 - 100 * i, circs[2] + "");

	gui.setPenColor(typeColor[tile.getType()]);
	gui.square(100 * j + 50, 750 - 100 * i, 45);

    }
    
    MapTile[][] getMap(){
	return map;
    }

    public void mousePressed(double x, double y){
	if (Game.boardPhase == 0) return;
	if (gui.mouseY() >= 100){
	    int xChoice = (int)(gui.mouseX() / 100);
	    int yChoice = (int)(8 - gui.mouseY() / 100);
	    if (map[yChoice][xChoice].getOwner() == Game.getCurrentPlayer()) return;
	    if (choice[0] != xChoice | choice[1] != yChoice){
		if (choice[0] != -1 || choice[1] != -1){
		    gui.setPenColor(Draw.BLACK);
		    gui.square(100 * choice[0] + 50, 750 - 100 * choice[1], 50);
		}
		gui.setPenColor(Draw.MAGENTA);
		gui.square(100 * xChoice + 50, 750 - 100 * yChoice, 50);
		choice[0] = xChoice;
		choice[1] = yChoice;
	    }
	    else {
		gui.setPenColor(Draw.BLACK);
		gui.square(100 * xChoice + 50, 750 - 100 * yChoice, 50);
		choice[0] = -1;
		choice[1] = -1;
	    }
	}
	else if ((choice[0] != -1 || choice[1] != -1) && gui.mouseX() > 190 && gui.mouseX() < 310 && gui.mouseY() < 75 && gui.mouseY() > 25) choosing = false;

	gui.setPenColor((choice[0] != -1 || choice[1] != -1)? Draw.WHITE : Draw.DARK_GRAY);
	gui.setFont(bigFont);
	gui.text(250,43,"CAST");
	gui.show();
    }

    void capture(int[] cardValues, int y, int x){
	gui.setPenColor(Draw.BLACK);
	gui.filledRectangle(250,50,60,50);
    	int ans = tolerances[map[y][x].getType()];
    	for(int i = 0; i < 3; i++){
    	    if (cardValues[i] == -1) ans -= 10;
    	    else ans -= (int)(Math.abs(cardValues[i] - map[y][x].getCircs()[i]));
    	}
	if (ans == tolerances[map[y][x].getType()]){ //perfect cast skips checks
	    if (map[y][x].getOwner() != -1) Game.scores[1 - Game.getCurrentPlayer()] -= scores[map[y][x].getType()];
	    Game.scores[Game.getCurrentPlayer()] += scores[map[y][x].getType()];
	    map[y][x].setOwner(Game.getCurrentPlayer());
	    guiTile(y,x);
	}
	else{
	    int proficiency = 1 + (int) (12 * Math.random());
	    ans -= proficiency;
	    int defence = 1 + (int) (12 * Math.random());
	    if (map[y][x].getOwner() != -1) ans -= defence;
	    if (y > 0){
		if (map[y-1][x].getOwner() == Game.getCurrentPlayer()) ans += 2;
	    }
	    if (y < 6){
		if (map[y+1][x].getOwner() == Game.getCurrentPlayer()) ans += 2;
	    }
	    if (x > 0){
		if (map[y][x-1].getOwner() == Game.getCurrentPlayer()) ans += 2;
	    }
	    if (x < 4){
		if (map[y][x+1].getOwner() == Game.getCurrentPlayer()) ans += 2;
	    }
	    if (ans > -1) {
		if (map[y][x].getOwner() != -1) Game.scores[1 - Game.getCurrentPlayer()] -= scores[map[y][x].getType()];
		Game.scores[Game.getCurrentPlayer()] += scores[map[y][x].getType()];
		map[y][x].setOwner(Game.getCurrentPlayer());
		guiTile(y,x);   
	    }
	}
	gui.setPenColor(Draw.BLACK);
	gui.filledRectangle(250,50,250,50);
	guiDeck();
	gui.setFont(scoreFont);
	gui.setPenColor(Draw.GREEN);
	gui.text(50,45,Game.scores[0] + "");
	gui.setPenColor(Draw.PINK);
	gui.text(150,45,Game.scores[1] + "");
	gui.show();
    }

    void end(){
	gui.pause(30000);
    }
    
    public void mouseDragged(double x, double y){}
    public void mouseReleased(double x, double y){}
    public void mouseClicked(double x, double y){}
    public void keyTyped(char c){}
    public void keyPressed(int keycode){}
    public void keyReleased(int keycode){}
}
