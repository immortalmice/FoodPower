package com.github.immortalmice.foodpower.customclass.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SlotPosProvider{
	public static final int SLOT_SIZE = 18;

	public static List<Position2D> CIRCLE(Position2D center, int radius, int count){
		List<Position2D> result = new ArrayList<>();

		float angle = 360 / count;
		for(int i = 0; i <= count-1; i ++){
			Position2D pos = new Position2D(
				(int)(center.x + radius * Math.cos((angle * i - 90) * Math.PI / 180)),
				(int)(center.y + radius * Math.sin((angle * i - 90) * Math.PI / 180))
			);
			result.add(pos.translateToLeftTop());
		}
		return result;
	}

	public static List<Position2D> DICE(Position2D origin, int width, int height, int count){
		Position2D center = new Position2D(
			(int)(origin.x + width / 2),
			(int)(origin.y + height / 2)
		);

		if(count <= 6 && count >= 1){
			List<Position2D> result = new ArrayList<>();
			int quarterGap = Math.min(width / 4, height / 4);

			Position2D leftTop = center.translate(-quarterGap, -quarterGap);
			Position2D rightTop = center.translate(quarterGap, -quarterGap);
			Position2D leftBottom = center.translate(-quarterGap, quarterGap);
			Position2D rightBottom = center.translate(quarterGap, quarterGap);

			if(count == 1 || count == 3 || count == 5){
				result.add(center);
			}
			if(count >= 2 && count <= 5){
				result.addAll(Arrays.asList(leftTop, rightBottom));
			}
			if(count >= 4 && count <= 5){
				result.addAll(Arrays.asList(leftBottom, rightTop));
			}
			if(count == 6){
				int oneThirdGap = Math.min(width / 3, height / 3);
				List<Position2D> centerBetween = Arrays.asList(
					center.translate((int)(-oneThirdGap / 1.5f), 0),
					center.translate((int)(oneThirdGap / 1.5f), 0)
				);

				result.addAll(centerBetween.stream().map(pos -> pos.translate(0, -oneThirdGap)).collect(Collectors.toList()));
				result.addAll(centerBetween);
				result.addAll(centerBetween.stream().map(pos -> pos.translate(0, oneThirdGap)).collect(Collectors.toList()));
			}

			return result.stream().map(pos -> pos.translateToLeftTop()).collect(Collectors.toList());
		}
		// Use circle shape when count is not in [1, 6]
		return SlotPosProvider.CIRCLE(
			center,
			Math.min(width, height) - SlotPosProvider.SLOT_SIZE,
			count
		);
	}

	public static class RecipeTableSlotPos{
		public static final Position2D CENTER = new Position2D(92, 81);
		public static final int RADIUS = 40;

		private static final Map<Integer, List<Position2D>> cache = new HashMap<>();

		public static List<Position2D> provide(int count){
			if(RecipeTableSlotPos.cache.containsKey(count)){
				return RecipeTableSlotPos.cache.get(count);
			}
			
			List<Position2D> pos = SlotPosProvider.CIRCLE(
				RecipeTableSlotPos.CENTER,
				RecipeTableSlotPos.RADIUS,
				count
			);
			RecipeTableSlotPos.cache.put(count, pos);
			return pos;
		}
	}

	public static class KitchenApplianceSlotPos{
		public static final Position2D ORIGIN = new Position2D(66, 20);
		public static final int WIDTH = 80;
		public static final int HEIGHT = 106;

		private static final Map<Integer, List<Position2D>> cache = new HashMap<>();

		public static List<Position2D> provide(int count){
			if(KitchenApplianceSlotPos.cache.containsKey(count)){
				return KitchenApplianceSlotPos.cache.get(count);
			}

			List<Position2D> pos = SlotPosProvider.DICE(
				KitchenApplianceSlotPos.ORIGIN,
				KitchenApplianceSlotPos.WIDTH,
				KitchenApplianceSlotPos.HEIGHT,
				count
			);
			KitchenApplianceSlotPos.cache.put(count, pos);
			return pos;
		}
	}

	public static class Position2D{
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
			return this.translate(SlotPosProvider.SLOT_SIZE / 2 * -1);
		}
	}
}