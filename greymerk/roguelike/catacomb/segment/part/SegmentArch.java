package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.Dungeon;
import greymerk.roguelike.catacomb.segment.IAlcove;
import greymerk.roguelike.catacomb.segment.alcove.PrisonCell;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class SegmentArch extends SegmentBase {

	@Override
	protected void genWall(Cardinal dir) {
			
		MetaBlock stair = theme.getSecondaryStair(); 
		stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(dir), true));
		
		MetaBlock air = new MetaBlock(0);
		
		Coord cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, air, true, true);
		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);
		
		for(Cardinal orth : Cardinal.getOrthogonal(dir)){
			cursor = new Coord(x, y, z);
			cursor.add(orth, 1);
			cursor.add(dir, 2);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryPillar(), true, true);
			cursor.add(Cardinal.UP, 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, theme.getSecondaryWall(), true, true);
			cursor.add(Cardinal.reverse(dir), 1);
			WorldGenPrimitive.setBlock(world, rand, cursor, stair, true, true);			
		}
		
		if(rand.nextInt(10) == 0 && Catacomb.getLevel(y) == 2){
			IAlcove cell = new PrisonCell();
			if(cell.isValidLocation(world, x, y, z, dir)){
				cell.generate(world, rand, theme, x, y, z, dir);
			}
		}
	}
}