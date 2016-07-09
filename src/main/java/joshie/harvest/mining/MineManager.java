package joshie.harvest.mining;

import gnu.trove.map.TIntLongMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntLongHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import joshie.harvest.core.helpers.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldSavedData;

import java.util.Random;

public class MineManager extends WorldSavedData {
    public static final int CHUNK_BOUNDARY = 10;
    private static final Random rand = new Random(326723423L);
    private TIntLongMap seeds = new TIntLongHashMap();
    private TIntObjectMap<TIntObjectMap<IBlockState[][]>> generation = new TIntObjectHashMap();
    private TIntObjectMap<int[]> coordinates = new TIntObjectHashMap();
    private TIntObjectMap<BlockPos> spawnCoordinates = new TIntObjectHashMap<>();

    public MineManager(String string) {
        super(string);
    }

    public boolean areCoordinatesDefault(int mineID) {
        return getSpawnCoordinateForMine(mineID).equals(new BlockPos(0, 254, mineID * CHUNK_BOUNDARY * 16));
    }

    public boolean areCoordinatesGenerated(int mineID) {
        return spawnCoordinates.containsKey(mineID);
    }

    public BlockPos getSpawnCoordinateForMine(int mineID) {
        BlockPos ret = spawnCoordinates.get(mineID);
        if (ret == null) {
            return new BlockPos(0, 254, mineID * CHUNK_BOUNDARY * 16);
        }

        return ret;
    }

    public void setSpawnForMine(int mineID, int x, int y, int z) {
        spawnCoordinates.putIfAbsent(mineID, new BlockPos(x, y, z));
    }

    public TIntObjectMap<IBlockState[][]> getStateMap(int mapIndex) {
        return generation.get(mapIndex);
    }

    public void putStateMap(int mapIndex, TIntObjectMap<IBlockState[][]>  map) {
        generation.put(mapIndex, map);
    }

    public boolean containsStateKey(int mapIndex) {
        return generation.containsKey(mapIndex);
    }

    public boolean containsCoordinatesKey(int mapIndex) {
        return coordinates.containsKey(mapIndex);
    }

    public void putCoordinates(int mapIndex, int[] coordinates) {
        this.coordinates.put(mapIndex, coordinates);
    }

    public int getCoordinates(int mapIndex, int position) {
        return coordinates.get(mapIndex)[position];
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        seeds = NBTHelper.readLongMap(tag.getTagList("Seeds", 10));
        spawnCoordinates = NBTHelper.readPositionMap(tag.getTagList("Coordinates", 10));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setTag("Seeds", NBTHelper.writeLongMap(seeds));
        tag.setTag("Coordinates", NBTHelper.writePositionMap(spawnCoordinates));
        return tag;
    }
}