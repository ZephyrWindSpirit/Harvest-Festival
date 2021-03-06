package joshie.harvest.core;

import joshie.harvest.animals.tracker.AnimalTrackerServer;
import joshie.harvest.core.handlers.DailyTickHandler;
import joshie.harvest.core.lib.HFModInfo;
import joshie.harvest.town.tracker.TownTrackerServer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class HFSavedData extends WorldSavedData {
    public static final String DATA_NAME = HFModInfo.CAPNAME + "-Data";

    private final AnimalTrackerServer animals = new AnimalTrackerServer();
    private final DailyTickHandler ticking = new DailyTickHandler();
    private final TownTrackerServer towns = new TownTrackerServer();

    public HFSavedData(String string) {
        super(string);
    }

    public void setWorld(World world) {
        animals.setWorld(world);
        ticking.setWorld(world);
        towns.setWorld(world);
    }

    public AnimalTrackerServer getAnimalTracker() {
        return animals;
    }

    public DailyTickHandler getTickables() {
        return ticking;
    }

    public TownTrackerServer getTownTracker() {
        return towns;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        towns.readFromNBT(nbt.getCompoundTag("TownTracker"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("TownTracker", towns.writeToNBT(new NBTTagCompound()));
        return nbt;
    }
}