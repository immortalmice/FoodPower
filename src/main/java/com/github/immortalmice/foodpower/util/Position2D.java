package com.github.immortalmice.foodpower.util;

public class Position2D{
	public int x, y;

	public Position2D(){
		this(0, 0);
	}

	public Position2D(int xIn, int yIn){
		this.x = xIn;
		this.y = yIn;
	}

	public Position2D translate(int offset){
		return this.translate(offset, offset);
	}

	public Position2D translate(int x, int y){
		return new Position2D(
			this.x + x,
			this.y + y
		);
	}

	public Position2D translateToLeftTop(){
		return this.translate(PosProvider.SLOT_SIZE / 2 * -1);
	}
}