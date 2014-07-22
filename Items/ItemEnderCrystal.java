/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.GeoStrata.Items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import Reika.DragonAPI.Libraries.IO.ReikaRenderHelper;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import Reika.GeoStrata.Base.GeoItem;

public class ItemEnderCrystal extends GeoItem {

	public ItemEnderCrystal(int ID) {
		super(ID);
		MinecraftForge.EVENT_BUS.register(this);
		hasSubtypes = true;
	}

	@Override
	public int getNumberTypes() {
		return 2;
	}

	@Override
	public String getItemDisplayName(ItemStack is) {
		return "Ender Crystal";
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean vb) {
		if (this.canPickUpCrystal(is)) {
			li.add("Can pick up an Ender Crystal");
		}
		else if (this.canPlaceCrystal(is)) {
			li.add("Contains 1 Ender Crystal");
		}
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if (!this.canPlaceCrystal(is))
			return false;
		if (!ReikaWorldHelper.softBlocks(world, x, y, z) && world.getBlockMaterial(x, y, z) != Material.water && world.getBlockMaterial(x, y, z) != Material.lava) {
			if (side == 0)
				--y;
			if (side == 1)
				++y;
			if (side == 2)
				--z;
			if (side == 3)
				++z;
			if (side == 4)
				--x;
			if (side == 5)
				++x;
			if (!ReikaWorldHelper.softBlocks(world, x, y, z) && world.getBlockMaterial(x, y, z) != Material.water && world.getBlockMaterial(x, y, z) != Material.lava)
				return false;
		}
		if (!ep.canPlayerEdit(x, y, z, 0, is))
			return false;
		else if (!this.checkSpace(world, x, y, z))
			return false;
		else
		{
			if (!ep.capabilities.isCreativeMode)
				is.setItemDamage(0);
			//world.setBlock(x, y, z, RedstoneBlocks.WIRE.getBlockID());
			if (!world.isRemote) {
				world.setBlock(x, y, z, Block.bedrock.blockID);
				EntityEnderCrystal cry = new EntityEnderCrystal(world);
				world.spawnEntityInWorld(cry);
				cry.setPosition(x+0.5, y+1, z+0.5);
			}
			ReikaSoundHelper.playPlaceSound(world, x, y, z, Block.bedrock);
		}
		return true;
	}

	@ForgeSubscribe
	public void captureCrystal(EntityInteractEvent ev) {
		Entity e = ev.target;
		EntityPlayer ep = ev.entityPlayer;
		if (e instanceof EntityEnderCrystal) {
			ItemStack is = ep.getCurrentEquippedItem();
			if (is != null && is.itemID == itemID && this.canPickUpCrystal(is)) {
				if (!ep.capabilities.isCreativeMode)
					is.setItemDamage(1);
				e.setDead();
				int x = MathHelper.floor_double(e.posX);
				int y = MathHelper.floor_double(e.posY);
				int z = MathHelper.floor_double(e.posZ);
				int id = ep.worldObj.getBlockId(x, y-1, z);
				if (id == Block.bedrock.blockID)
					ep.worldObj.setBlock(x, y-1, z, 0);
				ReikaSoundHelper.playBreakSound(ep.worldObj, x, y, z, Block.bedrock);
				if (ep.worldObj.isRemote) {
					for (int i = 0; i < 6; i++)
						ReikaRenderHelper.spawnDropParticles(ep.worldObj, x, y, z, Block.bedrock, 0);
				}
			}
		}
	}

	public boolean canPickUpCrystal(ItemStack is) {
		return is.getItemDamage() == 0;
	}

	public boolean canPlaceCrystal(ItemStack is) {
		return is.getItemDamage() == 1;
	}

	private boolean checkSpace(World world, int x, int y, int z) {
		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				for (int j = 0; j < 3; j++) {
					int dx = x+i;
					int dy = y+j;
					int dz = z+k;
					if (!ReikaWorldHelper.softBlocks(world, dx, dy, dz))
						return false;
					Block b = Block.blocksList[world.getBlockId(dx, dy, dz)];
					if (b instanceof BlockFluidBase || b instanceof BlockFluid)
						return false;
				}
			}
		}
		return true;
	}

}
