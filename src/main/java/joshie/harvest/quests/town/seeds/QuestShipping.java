package joshie.harvest.quests.town.seeds;

import joshie.harvest.api.HFApi;
import joshie.harvest.api.calendar.CalendarDate;
import joshie.harvest.api.calendar.Season;
import joshie.harvest.api.crops.Crop;
import joshie.harvest.api.npc.NPC;
import joshie.harvest.core.HFTrackers;
import joshie.harvest.player.PlayerTrackerServer;
import joshie.harvest.player.tracking.StackSold;
import joshie.harvest.quests.base.QuestTown;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class QuestShipping extends QuestTown {
    private static final int START = 0;
    private static final int FINISHED = 1;
    private CalendarDate lastCheck;
    private Set<StackSold> crops;
    private Season season;
    private int required;
    private int total;

    public QuestShipping(NPC npc, Season season, int required) {
        this.setNPCs(npc);
        this.season = season;
        this.required = required;
    }

    //Rebuild the list of spring crops
    private void rebuildCropSet() {
        crops = new HashSet<>();
        for (Crop crop: Crop.REGISTRY) {
            for (Season season: crop.getSeasons()) {
                if (season == this.season) {
                    crops.add(StackSold.of(crop.getCropStack(1), 0L));
                    break;
                }
            }
        }
    }

    private int getTotalCrops(CalendarDate date, EntityPlayer player) {
        if (lastCheck != null && date.equals(lastCheck)) return total;
        else {
            total = 0;
            lastCheck = date.copy();
            for (StackSold sold : HFTrackers.<PlayerTrackerServer>getPlayerTrackerFromPlayer(player).getTracking().getShipped()) {
                for (StackSold spring : crops) {
                    if (sold.equals(spring)) {
                        total += sold.getAmount();
                        break;
                    }
                }
            }

            return total;
        }
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public String getLocalizedScript(EntityPlayer player, EntityLiving entity, NPC npc) {
        return quest_stage >= FINISHED ? getLocalized("complete") : null;
    }

    @Override
    public void onChatClosed(EntityPlayer player, EntityLiving entity, NPC npc, boolean wasSneaking) {
        if (quest_stage == FINISHED) complete(player);
        if (!player.worldObj.isRemote && quest_stage == START) {
            if (crops == null) rebuildCropSet();
            int totalCrops = getTotalCrops(HFApi.calendar.getDate(player.worldObj), player);
            if (totalCrops >= required) {
                increaseStage(player);
            }
        }
    }
}
