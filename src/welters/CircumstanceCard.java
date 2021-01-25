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

class CircumstanceCard{
    private final int circType;
    private final int circ;

    CircumstanceCard(int type, int value){
	circType = type;
	circ = value;
    }
    
    int getType(){
	return circType;
    }

    int getValue(){
	return circ;
    }
    
    int[] getCircs(){
	int[] c = {-1,-1,-1};
	c[circType] = circ;
	return c;
    }
}
