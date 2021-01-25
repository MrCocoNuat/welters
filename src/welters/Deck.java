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

class Deck{
    private CircumstanceCard[] deck = new CircumstanceCard[105];
    private int index;

    Deck(){
	int[] distributionValues = {0,0,0,0,1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,7,7,7,8,8,8,9,9,9,10,10,10,10,0,0,0,0,1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,7,7,7,8,8,8,9,9,9,10,10,10,10,0,0,0,0,1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,7,7,7,8,8,8,9,9,9,10,10,10,10};
	int[] distributionTypes = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
	for(int i = 0; i < 105; i++){
	    int swap = i + (int) ((105 - i) * Math.random());
	    int tempValue = distributionValues[i];
	    distributionValues[i] = distributionValues[swap];
	    distributionValues[swap] = tempValue;
	    int tempType = distributionTypes[i];
	    distributionTypes[i] = distributionTypes[swap];
	    distributionTypes[swap] = tempType;
	}
	for(int i = 0; i < 105; i++){
		deck[i] = new CircumstanceCard(distributionTypes[i],distributionValues[i]);
	}

	index = 0;
    }

    CircumstanceCard draw(){
	return deck[index++];
    }

    int getIndex(){
	return index;
    }
}
