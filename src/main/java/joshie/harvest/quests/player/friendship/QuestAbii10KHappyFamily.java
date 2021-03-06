package joshie.harvest.quests.player.friendship;

import joshie.harvest.api.HFApi;
import joshie.harvest.api.player.RelationshipType;
import joshie.harvest.api.quests.HFQuest;
import joshie.harvest.api.quests.Quest;
import joshie.harvest.npcs.HFNPCs;
import joshie.harvest.quests.Quests;
import joshie.harvest.quests.base.QuestFriendship;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Set;

@HFQuest("friendship.abii.parents")
public class QuestAbii10KHappyFamily extends QuestFriendship {
    public QuestAbii10KHappyFamily() {
        super(HFNPCs.DAUGHTER_CHILD, 10000);
    }

    @Override
    public boolean canStartQuest(Set<Quest> active, Set<Quest> finished) {
        return finished.contains(Quests.ABI_5K);
    }

    @Override
    public void onQuestCompleted(EntityPlayer player) {
        HFApi.player.getRelationsForPlayer(player).affectRelationship(RelationshipType.NPC, HFNPCs.MAYOR.getUUID(), 1000);
        HFApi.player.getRelationsForPlayer(player).affectRelationship(RelationshipType.NPC, HFNPCs.PRIEST.getUUID(), 1000);
        HFApi.player.getRelationsForPlayer(player).affectRelationship(RelationshipType.NPC, HFNPCs.DAUGHTER_ADULT.getUUID(), 1000);
    }
}
