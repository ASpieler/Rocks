/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.GeoStrata;

import java.util.HashMap;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;

public class CrystalPotionController {

	private static HashMap<ReikaDyeHelper, Potion> map = new HashMap<ReikaDyeHelper, Potion>();
	private static HashMap<ReikaDyeHelper, Potion> nethermap = new HashMap<ReikaDyeHelper, Potion>();

	static {
		addColorPotion(ReikaDyeHelper.BLUE, Potion.nightVision);
		addColorPotion(ReikaDyeHelper.CYAN, Potion.waterBreathing);
		addColorPotion(ReikaDyeHelper.GRAY, Potion.moveSlowdown);
		addColorPotion(ReikaDyeHelper.GREEN, Potion.poison);
		addColorPotion(ReikaDyeHelper.LIGHTBLUE, Potion.moveSpeed);
		addColorPotion(ReikaDyeHelper.LIGHTGRAY, Potion.weakness);
		addColorPotion(ReikaDyeHelper.LIME, Potion.jump);
		addColorPotion(ReikaDyeHelper.MAGENTA, Potion.regeneration);
		addColorPotion(ReikaDyeHelper.RED, Potion.resistance);
		addColorPotion(ReikaDyeHelper.ORANGE, Potion.fireResistance);
		addColorPotion(ReikaDyeHelper.PINK, Potion.damageBoost);
		addColorPotion(ReikaDyeHelper.YELLOW, Potion.digSpeed);
		addColorPotion(ReikaDyeHelper.WHITE, Potion.invisibility);
		addColorPotion(ReikaDyeHelper.BROWN, Potion.field_76443_y);

		addNetherPotion(ReikaDyeHelper.BLACK, Potion.wither);
		addNetherPotion(ReikaDyeHelper.CYAN, Potion.hunger);
		addNetherPotion(ReikaDyeHelper.GRAY, Potion.blindness);
		addNetherPotion(ReikaDyeHelper.GREEN, Potion.poison);
		addNetherPotion(ReikaDyeHelper.LIGHTBLUE, Potion.moveSlowdown);
		addNetherPotion(ReikaDyeHelper.LIGHTGRAY, Potion.weakness);
		addNetherPotion(ReikaDyeHelper.YELLOW, Potion.digSlowdown);
		addNetherPotion(ReikaDyeHelper.WHITE, Potion.invisibility);
		addNetherPotion(ReikaDyeHelper.BROWN, Potion.confusion);
		addNetherPotion(ReikaDyeHelper.BLUE, Potion.nightVision);
		addNetherPotion(ReikaDyeHelper.PINK, Potion.damageBoost);
		addNetherPotion(ReikaDyeHelper.MAGENTA, Potion.regeneration);
	}

	private static void addColorPotion(ReikaDyeHelper color, Potion pot) {
		map.put(color, pot);
	}

	private static void addNetherPotion(ReikaDyeHelper color, Potion pot) {
		nethermap.put(color, pot);
	}

	public static PotionEffect getEffectFromColor(ReikaDyeHelper color, int dura, int level) {
		Potion pot = map.get(color);
		if (pot == null)
			return null;
		return new PotionEffect(pot.id, dura, level);
	}

	public static PotionEffect getNetherEffectFromColor(ReikaDyeHelper color, int dura, int level) {
		Potion pot = nethermap.get(color);
		if (pot == null)
			return null;
		return new PotionEffect(pot.id, dura, level);
	}

	public static String getPotionName(ReikaDyeHelper color) {
		if (color == ReikaDyeHelper.BLACK)
			return "corrupting";
		if (color == ReikaDyeHelper.BROWN)
			return "lengthening";
		if (color == ReikaDyeHelper.PURPLE)
			return "enhancing";
		Potion pot = map.get(color);
		if (pot == null)
			return "";
		return StatCollector.translateToLocal(pot.getName());
	}

	public static boolean requiresCustomPotion(ReikaDyeHelper color) {
		if (color == ReikaDyeHelper.CYAN)
			return true;
		if (color == ReikaDyeHelper.YELLOW)
			return true;
		if (color == ReikaDyeHelper.LIME)
			return true;
		if (color == ReikaDyeHelper.RED)
			return true;
		return false;
	}

	public static boolean isCorruptedPotion(ReikaDyeHelper color) {
		if (color == ReikaDyeHelper.GRAY)
			return true;
		if (color == ReikaDyeHelper.LIGHTGRAY)
			return true;
		if (color == ReikaDyeHelper.WHITE)
			return true;
		return false;
	}

	public static boolean isPotionModifier(ReikaDyeHelper color) {
		if (color == ReikaDyeHelper.BLACK)
			return true;
		if (color == ReikaDyeHelper.BROWN)
			return true;
		if (color == ReikaDyeHelper.PURPLE)
			return true;
		return false;
	}

	public static String getEffectName(ReikaDyeHelper color) {
		if (color == ReikaDyeHelper.BLACK)
			return "Confuses Mobs";
		if (color == ReikaDyeHelper.PURPLE)
			return "Gives XP";
		if (color == ReikaDyeHelper.WHITE)
			return "Clears Effects";
		return StatCollector.translateToLocal(map.get(color).getName());
	}

	public static String getNetherEffectName(ReikaDyeHelper color) {
		if (color == ReikaDyeHelper.BROWN)
			return "Nausea";
		if (color == ReikaDyeHelper.PURPLE)
			return "Takes XP";
		if (color == ReikaDyeHelper.ORANGE)
			return "Fire Damage";
		if (color == ReikaDyeHelper.LIME)
			return "Jump Disability";
		if (color == ReikaDyeHelper.RED)
			return "Direct Damage";
		return StatCollector.translateToLocal(nethermap.get(color).getName());
	}
}
