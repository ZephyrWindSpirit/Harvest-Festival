package joshie.harvest.quests;

import joshie.harvest.api.HFApi;
import joshie.harvest.api.quests.Quest;
import joshie.harvest.core.util.annotations.HFEvents;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@HFEvents
@SuppressWarnings("unused")
public class QuestEvents {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        List<Quest> quests = HFApi.quests.getCurrentQuests(event.getEntityPlayer());
        for (Quest quest : quests) {
            if (quest.onEntityInteract(event.getEntityPlayer(), event.getItemStack(), event.getHand(), event.getTarget())) break;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRightClickGround(PlayerInteractEvent.RightClickBlock event) {
        List<Quest> quests = HFApi.quests.getCurrentQuests(event.getEntityPlayer());
        for (Quest quest : quests) {
            if(quest.onRightClickBlock(event.getEntityPlayer(), event.getPos(), event.getFace())) break;
        }
    }
}