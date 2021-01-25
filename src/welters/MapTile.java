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

class MapTile{
    private final int tileType;

    private final int circ1;
    private final int circ2;
    private final int circ3;
    private int owner;
    

    MapTile(int type){
	tileType = type;
	circ1 = (int) (11 * Math.random());
	circ2 = (int) (11 * Math.random());
	circ3 = (int) (11 * Math.random());
	owner = -1;
    }

    int getType(){
	return tileType;
    }
    
    int[] getCircs(){
	int[] c = {circ1, circ2, circ3};
	return c;
    }

    int getOwner(){
	return owner;
    }

    void setOwner(int o){
	owner = o;
    }
}
