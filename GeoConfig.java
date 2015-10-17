/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.GeoStrata;

import java.util.ArrayList;

import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Instantiable.IO.ControlledConfig;
import Reika.DragonAPI.Interfaces.Configuration.ConfigList;
import Reika.DragonAPI.Interfaces.Registry.IDRegistry;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.GeoStrata.Registry.RockTypes;


public class GeoConfig extends ControlledConfig {

	private static final ArrayList<String> entries = ReikaJavaLibrary.getEnumEntriesWithoutInitializing(RockTypes.class);
	private final int[] rockBands = new int[entries.size()];

	public GeoConfig(DragonAPIMod mod, ConfigList[] option, IDRegistry[] id, int cfg) {
		super(mod, option, id, cfg);
	}

	@Override
	protected void loadAdditionalData() {
		for (int i = 0; i < entries.size(); i++) {
			String name = entries.get(i);
			rockBands[i] = config.get("Rock Band Heights", name, 0).getInt();
		}
	}

	public int getRockBand(RockTypes r) {
		return rockBands[r.ordinal()];
	}

}