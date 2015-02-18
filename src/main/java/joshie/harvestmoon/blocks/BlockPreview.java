package joshie.harvestmoon.blocks;

import joshie.harvestmoon.blocks.tiles.TileMarker;
import joshie.harvestmoon.buildings.BuildingGroup;
import joshie.harvestmoon.lib.RenderIds;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockPreview extends BlockHMBaseMeta {
    public static final int N1_T__N2_F__SWAP_F = 0;
    public static final int N1_T__N2_T__SWAP_T = 1;
    public static final int N1_T__N2_T__SWAP_F = 2;
    public static final int N1_T__N2_F__SWAP_T = 3;
    public static final int N1_F__N2_F__SWAP_F = 4;
    public static final int N1_F__N2_F__SWAP_T = 5;
    public static final int N1_F__N2_T__SWAP_F = 6;
    public static final int N1_F__N2_T__SWAP_T = 7;

    public static boolean getN1FromMeta(int meta) {
        return meta <= 3;
    }

    public static boolean getN2FromMeta(int meta) {
        return meta == 1 || meta == 2 || meta >= 7;
    }

    public static boolean getSwapFromMeta(int meta) {
        return meta % 2 == 1;
    }

    public BlockPreview() {
        super(Material.wood);
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderIds.ALL;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public int getMetaCount() {
        return BuildingGroup.groups.size();
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!player.isSneaking()) {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta < 7) {
                meta++;
            } else meta = 0;
            return world.setBlockMetadataWithNotify(x, y, z, meta, 2);
        } else return false;
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileMarker();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        if (stack.getItemDamage() >= BuildingGroup.groups.size()) return;
        BuildingGroup group = BuildingGroup.groups.get(stack.getItemDamage());
        if (group != null) {
            TileMarker marker = (TileMarker) world.getTileEntity(x, y, z);
            marker.setBuilding(group, group.getRandom());
        }
    }
}