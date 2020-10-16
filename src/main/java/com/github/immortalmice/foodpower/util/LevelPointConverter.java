package com.github.immortalmice.foodpower.util;

public class LevelPointConverter{
	/* Every level reqired (BASE * POWER ^ level) points */
	private final int base;
	private final float power;

	public static LevelPointConverter PATTERN_CONVERTER = new LevelPointConverter(10, 1.1f);
	public static LevelPointConverter FLAVOR_CONVERTER = new LevelPointConverter(5, 1.1f);

	private LevelPointConverter(int baseIn, float powerIn){
		this.base = baseIn;
		this.power = powerIn;
	}

	/* Convert exp point to level */
	public int pointToLevel(int value){
		if(value != 0)
			return (int) Math.floor(Math.log(1 - ((value * (1 - this.power)) / this.base)) / Math.log(this.power));
		else
			return 0;
	}

	/* Convert level to exp point */
	public int levelToPoint(int level, int remaining){
		return (int) Math.ceil(((Math.pow(this.power, level) - 1) * this.base) / (this.power - 1)) + remaining;
	}
}