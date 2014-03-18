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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;

import org.lwjgl.input.Keyboard;

import Reika.DragonAPI.Libraries.ReikaPotionHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.GeoStrata.CrystalPotionController;
import Reika.GeoStrata.Base.CrystalBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockCrystal extends ItemBlock {

	public ItemBlockCrystal(int ID) {
		super(ID);
		hasSubtypes = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < ReikaDyeHelper.dyes.length; i++) {
			ItemStack item = new ItemStack(par1, 1, i);
			par3List.add(item);
		}
	}

	@Override
	public final String getUnlocalizedName(ItemStack is) {
		int d = is.getItemDamage();
		return super.getUnlocalizedName() + "." + d;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public Icon getIconFromDamage(int dmg) {
		return Block.blocksList[this.getBlockID()].getIcon(0, dmg);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer ep, List li, boolean par4) {
		CrystalBlock block = (CrystalBlock)Block.blocksList[is.itemID];
		ReikaDyeHelper dye = ReikaDyeHelper.getColorFromItem(is);
		PotionEffect eff = CrystalPotionController.getEffectFromColor(dye, 200, 0);
		PotionEffect neff = CrystalPotionController.getNetherEffectFromColor(dye, 200, 0);
		boolean negative = eff != null ? ReikaPotionHelper.isBadEffect(Potion.potionTypes[eff.getPotionID()]) : false;
		boolean nnegative = neff != null ? ReikaPotionHelper.isBadEffect(Potion.potionTypes[neff.getPotionID()]) : false;
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			if (block.shouldGiveEffects()) {
				li.add("Effects: "+CrystalPotionController.getEffectName(dye));
				if (negative)
					li.add("(Mobs only)");
				String sg = nnegative || neff == null ? "(Players only)" : "(Mobs only)";
				li.add("Nether Effects: "+CrystalPotionController.getNetherEffectName(dye));
				li.add(sg);
				if (CrystalPotionController.getEffectFromColor(dye, 200, 0) != null) {
					li.add("");
					li.add("Effect Range: "+block.getRange());
					li.add("Effect Level: "+(block.getPotionLevel()+1));

				}
			}
			else {
				li.add("No effects - not enabled in config");
			}
		}
		else {
			StringBuilder sb = new StringBuilder();
			sb.append("Hold ");
			sb.append(EnumChatFormatting.GREEN.toString());
			sb.append("Shift");
			sb.append(EnumChatFormatting.GRAY.toString());
			sb.append(" for effect data");
			li.add(sb.toString());
		}
	}

}
