package joshie.harvest.npcs.packet;

import io.netty.buffer.ByteBuf;
import joshie.harvest.HarvestFestival;
import joshie.harvest.core.handlers.GuiHandler;
import joshie.harvest.core.helpers.SpawnItemHelper;
import joshie.harvest.core.network.Packet;
import joshie.harvest.core.network.Packet.Side;
import joshie.harvest.core.network.PenguinPacket;
import joshie.harvest.knowledge.HFKnowledge;
import joshie.harvest.knowledge.item.ItemBook.Book;
import joshie.harvest.npcs.HFNPCs;
import joshie.harvest.npcs.entity.EntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

@Packet(Side.SERVER)
public class PacketGift extends PenguinPacket {
    private int npcID;

    public PacketGift() {}
    public PacketGift(EntityNPC npc) {
        this.npcID = npc.getEntityId();
    }

    @Override
    public void toBytes(ByteBuf to) {
        to.writeInt(npcID);
    }

    @Override
    public void fromBytes(ByteBuf from) {
        npcID = from.readInt();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handlePacket(EntityPlayer player) {
        EntityNPC npc = (EntityNPC) player.worldObj.getEntityByID(npcID);
        if (npc != null) {
            if (npc.isEntityAlive()) {
                if (npc.getNPC() != HFNPCs.GODDESS) {
                    if (player.getHeldItemOffhand() != null) {
                        player.openGui(HarvestFestival.instance, GuiHandler.GIFT, player.worldObj, npcID, -1, EnumHand.OFF_HAND.ordinal());
                    } else if (player.getHeldItemMainhand() != null) {
                        player.openGui(HarvestFestival.instance, GuiHandler.GIFT, player.worldObj, npcID, -1, EnumHand.MAIN_HAND.ordinal());
                    }
                } else {
                    SpawnItemHelper.spawnByEntity(player, HFKnowledge.BOOK.getStackFromEnum(Book.STATISTICS));
                }

                npc.setTalking(player);
            }
        }
    }
}
