/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.GeoStrata.Bees;
import Reika.DragonAPI.ModInteract.Bees.BeeSpecies.Fertility;
import Reika.DragonAPI.ModInteract.Bees.BeeSpecies.Flowering;
import Reika.DragonAPI.ModInteract.Bees.BeeSpecies.Life;
import Reika.DragonAPI.ModInteract.Bees.BeeSpecies.Speeds;
import Reika.DragonAPI.ModInteract.Bees.BeeSpecies.Territory;
import Reika.DragonAPI.ModInteract.Bees.BeeSpecies.Tolerance;
import Reika.DragonAPI.ModInteract.Bees.BeeTraits;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

public enum CrystalBeeTypes {
	BLACK(Speeds.SLOW, Life.LONGEST, Fertility.LOW, Flowering.SLOWER, Territory.DEFAULT, Tolerance.NONE, Tolerance.NONE, 0, 0, EnumTemperature.NORMAL, EnumHumidity.NORMAL),
	RED(Speeds.NORMAL, Life.SHORTEST, Fertility.NORMAL, Flowering.FAST, Territory.DEFAULT, Tolerance.DOWN, Tolerance.DOWN, 1, 1, EnumTemperature.HOT, EnumHumidity.ARID),
	GREEN(Speeds.SLOWEST, Life.NORMAL, Fertility.LOW, Flowering.SLOWEST, Territory.LARGE, Tolerance.BOTH, Tolerance.NONE, 1, 0, EnumTemperature.NORMAL, EnumHumidity.DAMP),
	BROWN(Speeds.SLOWER, Life.SHORTENED, Fertility.HIGH, Flowering.SLOW, Territory.DEFAULT, Tolerance.NONE, Tolerance.DOWN, 0, 1, EnumTemperature.NORMAL, EnumHumidity.DAMP),
	BLUE(Speeds.FAST, Life.LONG, Fertility.NORMAL, Flowering.SLOWEST, Territory.LARGE, Tolerance.BOTH, Tolerance.NONE, 2, 0, EnumTemperature.NORMAL, EnumHumidity.NORMAL),
	PURPLE(Speeds.NORMAL, Life.SHORTEST, Fertility.LOW, Flowering.SLOWER, Territory.LARGER, Tolerance.NONE, Tolerance.NONE, 0, 0, EnumTemperature.NORMAL, EnumHumidity.DAMP),
	CYAN(Speeds.SLOW, Life.SHORT, Fertility.HIGH, Flowering.FASTER, Territory.DEFAULT, Tolerance.BOTH, Tolerance.NONE, 1, 0, EnumTemperature.NORMAL, EnumHumidity.DAMP),
	LIGHTGRAY(Speeds.SLOWEST, Life.SHORTER, Fertility.LOW, Flowering.SLOWEST, Territory.DEFAULT, Tolerance.BOTH, Tolerance.NONE, 4, 0, EnumTemperature.NORMAL, EnumHumidity.NORMAL),
	GRAY(Speeds.SLOWEST, Life.LONG, Fertility.LOW, Flowering.SLOWEST, Territory.DEFAULT, Tolerance.NONE, Tolerance.BOTH, 0, 2, EnumTemperature.NORMAL, EnumHumidity.NORMAL),
	PINK(Speeds.NORMAL, Life.SHORT, Fertility.NORMAL, Flowering.FAST, Territory.DEFAULT, Tolerance.NONE, Tolerance.NONE, 0, 0, EnumTemperature.NORMAL, EnumHumidity.NORMAL),
	LIME(Speeds.SLOW, Life.SHORTER, Fertility.NORMAL, Flowering.AVERAGE, Territory.LARGEST, Tolerance.NONE, Tolerance.NONE, 0, 0, EnumTemperature.NORMAL, EnumHumidity.NORMAL),
	YELLOW(Speeds.FAST, Life.SHORTEST, Fertility.HIGH, Flowering.SLOW, Territory.DEFAULT, Tolerance.UP, Tolerance.NONE, 1, 0, EnumTemperature.NORMAL, EnumHumidity.ARID),
	LIGHTBLUE(Speeds.FASTEST, Life.SHORTEST, Fertility.LOW, Flowering.SLOWEST, Territory.DEFAULT, Tolerance.NONE, Tolerance.UP, 0, 0, EnumTemperature.NORMAL, EnumHumidity.NORMAL),
	MAGENTA(Speeds.NORMAL, Life.SHORT, Fertility.LOW, Flowering.MAXIMUM, Territory.DEFAULT, Tolerance.NONE, Tolerance.BOTH, 0, 2, EnumTemperature.NORMAL, EnumHumidity.NORMAL),
	ORANGE(Speeds.FASTER, Life.SHORT, Fertility.NORMAL, Flowering.SLOW, Territory.DEFAULT, Tolerance.NONE, Tolerance.NONE, 0, 0, EnumTemperature.HOT, EnumHumidity.ARID),
	WHITE(Speeds.SLOWER, Life.LONGER, Fertility.MAXIMUM, Flowering.SLOWEST, Territory.DEFAULT, Tolerance.NONE, Tolerance.NONE, 0, 0, EnumTemperature.NORMAL, EnumHumidity.NORMAL);

	public static final CrystalBeeTypes[] list = values();

	protected final BeeTraits traits = new BeeTraits();

	private CrystalBeeTypes(Speeds s, Life l, Fertility f, Flowering f2, Territory a, Tolerance d1, Tolerance d2, int tt, int ht, EnumTemperature t, EnumHumidity h) {
		traits.speed = s;
		traits.lifespan = l;
		traits.fertility = f;
		traits.flowering = f2;
		traits.area = a;
		traits.tempDir = d1;
		traits.humidDir = d2;
		traits.tempTol = tt;
		traits.humidTol = ht;
		traits.temperature = t;
		traits.humidity = h;
	}

	public BeeTraits getTraits() {
		return traits;
	}
}
