/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2014
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.GeoStrata.Rendering;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.Registry.ReikaDyeHelper;
import Reika.GeoStrata.GeoStrata;
import Reika.GeoStrata.Base.CrystalBlock;
import Reika.GeoStrata.Registry.GeoBlocks;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class CrystalRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb) {
		//GL11.glDisable(GL11.GL_LIGHTING);
		Tessellator v5 = Tessellator.instance;
		//v5.setBrightness(240);
		ReikaDyeHelper color = ReikaDyeHelper.dyes[meta];
		Color dye = color.getJavaColor();
		int red = dye.getRed();
		int green = dye.getGreen();
		int blue = dye.getBlue();
		int alpha = 256;
		Icon ico = GeoBlocks.CRYSTAL.getBlockInstance().getIcon(0, meta);
		double u = ico.getMinU();
		double v = ico.getMinV();
		double xu = ico.getMaxU();
		double xv = ico.getMaxV();
		int w = ico.getIconWidth();

		v5.startDrawingQuads();
		v5.setNormal(0, 0.8F, 0);
		v5.setColorRGBA_F(red/255F, green/255F, blue/255F, alpha/255F);
		this.renderSpike(v5, u, v, xu, xv, w);
		v5.draw();

		v5.startDrawingQuads();
		//v5.setBrightness(240);
		v5.setNormal(0, 0.5F, 0);
		v5.setColorRGBA_F(red/255F, green/255F, blue/255F, alpha/255F);
		this.renderXAngledSpike(v5, u, v, xu, xv, 0.1875, w);
		v5.draw();

		v5.startDrawingQuads();
		//v5.setBrightness(240);
		v5.setNormal(0, 0.5F, 0);
		v5.setColorRGBA_F(red/255F, green/255F, blue/255F, alpha/255F);
		this.renderZAngledSpike(v5, u, v, xu, xv, 0.1875, w);
		v5.draw();
		//GL11.glEnable(GL11.GL_LIGHTING);

		if (((CrystalBlock)b).renderBase()) {

			v5.startDrawingQuads();
			//v5.setBrightness(240);
			v5.setColorRGBA_F(red/255F, green/255F, blue/255F, alpha/255F);
			//this.renderXAngledSpike(v5, u, v, xu, xv, -0.1875, w);
			v5.draw();

			v5.startDrawingQuads();
			//v5.setBrightness(240);
			v5.setColorRGBA_F(red/255F, green/255F, blue/255F, alpha/255F);
			//this.renderZAngledSpike(v5, u, v, xu, xv, -0.1875, w);
			v5.draw();

			v5.startDrawingQuads();
			//v5.setBrightness(240);
			v5.setColorRGBA_F(red/255F, green/255F, blue/255F, alpha/255F);
			this.renderBase(v5, (CrystalBlock)b);
			v5.draw();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block b, int modelId, RenderBlocks rb) {
		int meta = world.getBlockMetadata(x, y, z);
		ReikaDyeHelper dye = ReikaDyeHelper.dyes[meta];
		int red = dye.getRed();
		int green = dye.getGreen();
		int blue = dye.getBlue();
		int alpha = 220;

		Icon ico = GeoBlocks.CRYSTAL.getBlockInstance().getIcon(0, meta);
		//ico = Block.blockNetherQuartz.getIcon(0, 0);
		double u = ico.getMinU();
		double v = ico.getMinV();
		double xu = ico.getMaxU();
		double xv = ico.getMaxV();

		//xu = u = xv = v = 0;

		Tessellator v5 = Tessellator.instance;

		double maxx = b.getBlockBoundsMaxX();
		double minx = b.getBlockBoundsMinX();
		double miny = b.getBlockBoundsMinY();
		double maxy = b.getBlockBoundsMaxY();
		double maxz = b.getBlockBoundsMaxZ();
		double minz = b.getBlockBoundsMinZ();

		int l = b.getMixedBrightnessForBlock(world, x, y, z);

		//v5.setBrightness(rb.renderMaxY < 1.0D ? l : b.getMixedBrightnessForBlock(world, x, y+1, z));
		v5.setBrightness(240);
		v5.addTranslation(x, y, z);
		v5.setColorRGBA_F(red/255F, green/255F, blue/255F, alpha/255F);

		int w = ico.getIconWidth();

		this.renderSpike(v5, u, v, xu, xv, w);
		int val = Math.abs(x)%9+Math.abs(z)%9; //16 combos -> binary selector
		if (val > 15 || ((CrystalBlock)b).renderAllArms())
			val = 15;
		if ((val & 8) == 8)
			this.renderXAngledSpike(v5, u, v, xu, xv, 0.1875, w); //8,9,10,11,12,13,14,15
		if ((val & 4) == 4)
			this.renderXAngledSpike(v5, u, v, xu, xv, -0.1875, w); //4,5,6,7,12,13,14,15
		if ((val & 2) == 2)
			this.renderZAngledSpike(v5, u, v, xu, xv, 0.1875, w); //2,3,6,7,10,11,14,15
		if ((val & 1) == 1)
			this.renderZAngledSpike(v5, u, v, xu, xv, -0.1875, w); //1,3,5,7,9,11,13,15

		//v5.setColorOpaque(0, 0, 0);
		//this.renderOutline(v5);

		if (((CrystalBlock)b).renderBase()) {
			this.renderBase(v5, (CrystalBlock)b);
		}

		v5.addTranslation(-x, -y, -z);
		return true;
	}

	private void renderBase(Tessellator v5, CrystalBlock b) {
		Icon ico = b.getBaseBlock(ForgeDirection.UP).getIcon(0, 0);
		int w = ico.getIconWidth();

		v5.setColorOpaque(255, 255, 255);

		double u = ico.getMinU();
		double v = ico.getMinV();
		double xu = ico.getMaxU();
		double xv = ico.getMaxV();

		double top = 0.125;

		v5.addVertexWithUV(0, top, 1, u, xv);
		v5.addVertexWithUV(1, top, 1, xu, xv);
		v5.addVertexWithUV(1, top, 0, xu, v);
		v5.addVertexWithUV(0, top, 0, u, v);

		v5.setColorOpaque(110, 110, 110);
		v5.addVertexWithUV(0, 0, 1, u, xv);
		v5.addVertexWithUV(1, 0, 1, xu, xv);
		v5.addVertexWithUV(1, 0, 0, xu, v);
		v5.addVertexWithUV(0, 0, 0, u, v);

		ico = b.getBaseBlock(ForgeDirection.EAST).getIcon(0, 0);
		u = ico.getMinU();
		v = ico.getMinV();
		xu = ico.getMaxU();
		xv = ico.getMaxV();

		double vv = v+(xv-v)/(w)*2;

		v5.setColorOpaque(200, 200, 200);
		v5.addVertexWithUV(0, 0, 0, u, v);
		v5.addVertexWithUV(1, 0, 0, xu, v);
		v5.addVertexWithUV(1, top, 0, xu, vv);
		v5.addVertexWithUV(0, top, 0, u, vv);

		v5.setColorOpaque(170, 170, 170);
		v5.addVertexWithUV(0, 0, 1, u, v);
		v5.addVertexWithUV(1, 0, 1, xu, v);
		v5.addVertexWithUV(1, top, 1, xu, vv);
		v5.addVertexWithUV(0, top, 1, u, vv);

		v5.setColorOpaque(200, 200, 200);
		v5.addVertexWithUV(0, top, 0, u, vv);
		v5.addVertexWithUV(0, top, 1, xu, vv);
		v5.addVertexWithUV(0, 0, 1, xu, v);
		v5.addVertexWithUV(0, 0, 0, u, v);

		v5.setColorOpaque(170, 170, 170);
		v5.addVertexWithUV(1, top, 0, u, vv);
		v5.addVertexWithUV(1, top, 1, xu, vv);
		v5.addVertexWithUV(1, 0, 1, xu, v);
		v5.addVertexWithUV(1, 0, 0, u, v);
	}

	private void renderOutline(Tessellator v5) {
		double core = 0.15;
		double vl = 0.8;
		double dd = 0.01;
		double tip = 1;
		double zf = 0.4;

		v5.addVertexWithUV(0.5+core-dd, 0, 0.5-core, 0, 0);
		v5.addVertexWithUV(0.5+core+dd, 0, 0.5-core, 0, 0);
		v5.addVertexWithUV(0.5+core+dd, vl, 0.5-core, 0, 0);
		v5.addVertexWithUV(0.5+core-dd, vl, 0.5-core, 0, 0);

		v5.addVertexWithUV(0.5+core, 0, 0.5-core-dd, 0, 0);
		v5.addVertexWithUV(0.5+core, 0, 0.5-core+dd, 0, 0);
		v5.addVertexWithUV(0.5+core, vl, 0.5-core+dd, 0, 0);
		v5.addVertexWithUV(0.5+core, vl, 0.5-core-dd, 0, 0);

		v5.addVertexWithUV(0.5-core-dd, 0, 0.5-core, 0, 0);
		v5.addVertexWithUV(0.5-core+dd, 0, 0.5-core, 0, 0);
		v5.addVertexWithUV(0.5-core+dd, vl, 0.5-core, 0, 0);
		v5.addVertexWithUV(0.5-core-dd, vl, 0.5-core, 0, 0);

		v5.addVertexWithUV(0.5-core, 0, 0.5-core-dd, 0, 0);
		v5.addVertexWithUV(0.5-core, 0, 0.5-core+dd, 0, 0);
		v5.addVertexWithUV(0.5-core, vl, 0.5-core+dd, 0, 0);
		v5.addVertexWithUV(0.5-core, vl, 0.5-core-dd, 0, 0);

		v5.addVertexWithUV(0.5+core-dd, 0, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5+core+dd, 0, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5+core+dd, vl, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5+core-dd, vl, 0.5+core, 0, 0);

		v5.addVertexWithUV(0.5+core, 0, 0.5-core-dd, 0, 0);
		v5.addVertexWithUV(0.5+core, 0, 0.5-core+dd, 0, 0);
		v5.addVertexWithUV(0.5+core, vl, 0.5-core+dd, 0, 0);
		v5.addVertexWithUV(0.5+core, vl, 0.5-core-dd, 0, 0);

		v5.addVertexWithUV(0.5-core-dd, 0, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5-core+dd, 0, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5-core+dd, vl, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5-core-dd, vl, 0.5+core, 0, 0);

		v5.addVertexWithUV(0.5+core, 0, 0.5-core-dd, 0, 0);
		v5.addVertexWithUV(0.5+core, 0, 0.5-core+dd, 0, 0);
		v5.addVertexWithUV(0.5+core, vl, 0.5-core+dd, 0, 0);
		v5.addVertexWithUV(0.5+core, vl, 0.5-core-dd, 0, 0);

		v5.addVertexWithUV(0.5+core, vl, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5+core, vl+dd, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5, tip+dd, 0.5, 0, 0);
		v5.addVertexWithUV(0.5, tip, 0.5, 0, 0);

		v5.addVertexWithUV(0.5+core-dd*zf, vl, 0.5+core+dd*zf, 0, 0);
		v5.addVertexWithUV(0.5+core+dd*zf, vl, 0.5+core-dd*zf, 0, 0);
		v5.addVertexWithUV(0.5+dd*zf, tip, 0.5-dd*zf, 0, 0);
		v5.addVertexWithUV(0.5-dd*zf, tip, 0.5+dd*zf, 0, 0);

		v5.addVertexWithUV(0.5-core, vl, 0.5-core, 0, 0);
		v5.addVertexWithUV(0.5-core, vl+dd, 0.5-core, 0, 0);
		v5.addVertexWithUV(0.5, tip+dd, 0.5, 0, 0);
		v5.addVertexWithUV(0.5, tip, 0.5, 0, 0);

		v5.addVertexWithUV(0.5-core-dd*zf, vl, 0.5-core+dd*zf, 0, 0);
		v5.addVertexWithUV(0.5-core+dd*zf, vl, 0.5-core-dd*zf, 0, 0);
		v5.addVertexWithUV(0.5+dd*zf, tip, 0.5-dd*zf, 0, 0);
		v5.addVertexWithUV(0.5-dd*zf, tip, 0.5+dd*zf, 0, 0);

		v5.addVertexWithUV(0.5-core, vl, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5-core, vl+dd, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5, tip+dd, 0.5, 0, 0);
		v5.addVertexWithUV(0.5, tip, 0.5, 0, 0);

		v5.addVertexWithUV(0.5+core+dd, vl+dd, 0.5-core, 0, 0);
		v5.addVertexWithUV(0.5+core-dd, vl+dd, 0.5-core, 0, 0);
		v5.addVertexWithUV(0.5-dd, tip+dd, 0.5, 0, 0);
		v5.addVertexWithUV(0.5+dd, tip+dd, 0.5, 0, 0);

		v5.addVertexWithUV(0.5-core, vl, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5-core, vl+dd, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5, tip+dd, 0.5, 0, 0);
		v5.addVertexWithUV(0.5, tip, 0.5, 0, 0);

		v5.addVertexWithUV(0.5-core+dd, vl+dd, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5-core-dd, vl+dd, 0.5+core, 0, 0);
		v5.addVertexWithUV(0.5-dd, tip+dd, 0.5, 0, 0);
		v5.addVertexWithUV(0.5+dd, tip+dd, 0.5, 0, 0);
	}

	private void renderSpike(Tessellator v5, double u, double v, double xu, double xv, int w) {
		double core = 0.15;
		double vl = 0.8;

		v5.addVertexWithUV(0.5-core, vl, 0.5-core, u, v);
		v5.addVertexWithUV(0.5-core, vl, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5, 1, 0.5, xu, xv);
		v5.addVertexWithUV(0.5, 1, 0.5, xu, xv);

		v5.addVertexWithUV(0.5-core, vl, 0.5+core, u, v);
		v5.addVertexWithUV(0.5+core, vl, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5, 1, 0.5, xu, xv);
		v5.addVertexWithUV(0.5, 1, 0.5, xu, xv);

		v5.addVertexWithUV(0.5+core, vl, 0.5-core, u, v);
		v5.addVertexWithUV(0.5+core, vl, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5, 1, 0.5, xu, xv);
		v5.addVertexWithUV(0.5, 1, 0.5, u, xv);

		v5.addVertexWithUV(0.5-core, vl, 0.5-core, u, v);
		v5.addVertexWithUV(0.5+core, vl, 0.5-core, xu, v);
		v5.addVertexWithUV(0.5, 1, 0.5, xu, xv);
		v5.addVertexWithUV(0.5, 1, 0.5, u, xv);

		xv -= (xv-v)/w;
		v += (xv-v)/(w*1.2);
		u += (xu-u)/(w*2);
		xu -= (xu-u)/(w*2);

		v5.addVertexWithUV(0.5-core, 0, 0.5-core, u, v);
		v5.addVertexWithUV(0.5+core, 0, 0.5-core, xu, v);
		v5.addVertexWithUV(0.5+core, vl, 0.5-core, xu, xv);
		v5.addVertexWithUV(0.5-core, vl, 0.5-core, u, xv);

		v5.addVertexWithUV(0.5-core, 0, 0.5+core, u, v);
		v5.addVertexWithUV(0.5+core, 0, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5+core, vl, 0.5+core, xu, xv);
		v5.addVertexWithUV(0.5-core, vl, 0.5+core, u, xv);

		v5.addVertexWithUV(0.5+core, 0, 0.5-core, u, v);
		v5.addVertexWithUV(0.5+core, 0, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5+core, vl, 0.5+core, xu, xv);
		v5.addVertexWithUV(0.5+core, vl, 0.5-core, u, xv);

		v5.addVertexWithUV(0.5-core, 0, 0.5-core, u, v);
		v5.addVertexWithUV(0.5-core, 0, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5-core, vl, 0.5+core, xu, xv);
		v5.addVertexWithUV(0.5-core, vl, 0.5-core, u, xv);

		v5.addVertexWithUV(0.5-core, 0, 0.5-core, u, v);
		v5.addVertexWithUV(0.5-core, 0, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5+core, 0, 0.5+core, xu, xv);
		v5.addVertexWithUV(0.5+core, 0, 0.5-core, u, xv);
	}

	private void renderXAngledSpike(Tessellator v5, double u, double v, double xu, double xv, double out, int w) {
		double core = 0.12;
		double vl = 0.55;
		double dvl = vl/6D;
		double dy = -0.05;
		double tout = out;
		double htip = 0.1;
		int dir = out > 0 ? 1 : -1;

		v5.addVertexWithUV(0.5+core*dir+out, dy+vl+dvl, 0.5+core, u, v);
		v5.addVertexWithUV(0.5+core*dir+out, dy+vl+dvl, 0.5-core, xu, v);
		v5.addVertexWithUV(0.5+core*dir+out+tout, dy+vl+dvl+htip, 0.5, xu, xv); //tip
		v5.addVertexWithUV(0.5+core*dir+out+tout, dy+vl+dvl+htip, 0.5, xu, xv);

		v5.addVertexWithUV(0.5+core*dir*3+out, dy+vl, 0.5+core, u, v);
		v5.addVertexWithUV(0.5+core*dir*3+out, dy+vl, 0.5-core, xu, v);
		v5.addVertexWithUV(0.5+core*dir+out+tout, dy+vl+dvl+htip, 0.5, xu, xv); //tip
		v5.addVertexWithUV(0.5+core*dir+out+tout, dy+vl+dvl+htip, 0.5, u, xv);

		v5.addVertexWithUV(0.5+core*dir+out, dy+vl+dvl, 0.5+core, u, v);
		v5.addVertexWithUV(0.5+core*dir*3+out, dy+vl, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5+core*dir+out+tout, dy+vl+dvl+htip, 0.5, xu, xv); //tip
		v5.addVertexWithUV(0.5+core*dir+out+tout, dy+vl+dvl+htip, 0.5, u, xv);

		v5.addVertexWithUV(0.5+core*dir+out, dy+vl+dvl, 0.5-core, u, v);
		v5.addVertexWithUV(0.5+core*dir*3+out, dy+vl, 0.5-core, xu, v);
		v5.addVertexWithUV(0.5+core*dir+out+tout, dy+vl+dvl+htip, 0.5, xu, xv); //tip
		v5.addVertexWithUV(0.5+core*dir+out+tout, dy+vl+dvl+htip, 0.5, xu, xv);

		xv -= (xv-v)/w;
		v += (xv-v)/(w*1.2);
		u += (xu-u)/(w*2);
		xu -= (xu-u)/(w*2);

		v5.addVertexWithUV(0.5+core*dir, dy+dvl, 0.5-core, u, v);
		v5.addVertexWithUV(0.5+core*dir*3, dy+0, 0.5-core, xu, v);
		v5.addVertexWithUV(0.5+core*dir*3+out, dy+vl, 0.5-core, xu, xv);
		v5.addVertexWithUV(0.5+core*dir+out, dy+vl+dvl, 0.5-core, u, xv);

		v5.addVertexWithUV(0.5+core*dir, dy+dvl, 0.5+core, u, v);
		v5.addVertexWithUV(0.5+core*dir*3, dy+0, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5+core*dir*3+out, dy+vl, 0.5+core, xu, xv);
		v5.addVertexWithUV(0.5+core*dir+out, dy+vl+dvl, 0.5+core, u, xv);

		v5.addVertexWithUV(0.5+core*dir+out, dy+vl+dvl, 0.5-core, u, xv);
		v5.addVertexWithUV(0.5+core*dir+out, dy+vl+dvl, 0.5+core, xu, xv);
		v5.addVertexWithUV(0.5+core*dir, dy+dvl, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5+core*dir, dy+dvl, 0.5-core, u, v);

		v5.addVertexWithUV(0.5+core*dir*3+out, dy+vl, 0.5-core, u, xv);
		v5.addVertexWithUV(0.5+core*dir*3+out, dy+vl, 0.5+core, xu, xv);
		v5.addVertexWithUV(0.5+core*dir*3, dy, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5+core*dir*3, dy, 0.5-core, u, v);

		v5.addVertexWithUV(0.5+core*dir*3, dy, 0.5-core, u, v);
		v5.addVertexWithUV(0.5+core*dir*3, dy, 0.5+core, xu, v);
		v5.addVertexWithUV(0.5-core*dir*3+out*2, dy+dvl+0.04, 0.5+core, xu, xv);
		v5.addVertexWithUV(0.5-core*dir*3+out*2, dy+dvl+0.04, 0.5-core, u, xv);
	}

	private void renderZAngledSpike(Tessellator v5, double u, double v, double xu, double xv, double out, int w) {
		double core = 0.12;
		double vl = 0.55;
		double dvl = vl/6D;
		double dy = -0.1;
		double tout = out;//0.1875;
		double htip = 0.1;
		int dir = out > 0 ? 1 : -1;

		v5.addVertexWithUV(0.5, dy+vl+dvl+htip, 0.5+core*dir+out+tout, u, xv);
		v5.addVertexWithUV(0.5, dy+vl+dvl+htip, 0.5+core*dir+out+tout, xu, xv); //tip
		v5.addVertexWithUV(0.5-core, dy+vl+dvl, 0.5+core*dir+out, xu, v);
		v5.addVertexWithUV(0.5+core, dy+vl+dvl, 0.5+core*dir+out, u, v);

		v5.addVertexWithUV(0.5, dy+vl+dvl+htip, 0.5+core*dir+out+tout, xu, xv);
		v5.addVertexWithUV(0.5, dy+vl+dvl+htip, 0.5+core*dir+out+tout, xu, xv); //tip
		v5.addVertexWithUV(0.5-core, dy+vl, 0.5+core*dir*3+out, xu, v);
		v5.addVertexWithUV(0.5+core, dy+vl, 0.5+core*dir*3+out, u, v);

		v5.addVertexWithUV(0.5+core, dy+vl+dvl, 0.5+core*dir+out, u, v);
		v5.addVertexWithUV(0.5+core, dy+vl, 0.5+core*dir*3+out, xu, v);
		v5.addVertexWithUV(0.5, dy+vl+dvl+htip, 0.5+core*dir+out+tout, xu, xv); //tip
		v5.addVertexWithUV(0.5, dy+vl+dvl+htip, 0.5+core*dir+out+tout, xu, xv);

		v5.addVertexWithUV(0.5-core, dy+vl+dvl, 0.5+core*dir+out, u, v);
		v5.addVertexWithUV(0.5-core, dy+vl, 0.5+core*dir*3+out, xu, v);
		v5.addVertexWithUV(0.5, dy+vl+dvl+htip, 0.5+core*dir+out+tout, xu, xv); //tip
		v5.addVertexWithUV(0.5, dy+vl+dvl+htip, 0.5+core*dir+out+tout, u, xv);

		xv -= (xv-v)/w;
		v += (xv-v)/(w*1.2);
		u += (xu-u)/(w*2);
		xu -= (xu-u)/(w*2);

		v5.addVertexWithUV(0.5-core, dy+vl+dvl, 0.5+core*dir+out, u, xv);
		v5.addVertexWithUV(0.5-core, dy+vl, 0.5+core*dir*3+out, xu, xv);
		v5.addVertexWithUV(0.5-core, dy+0, 0.5+core*dir*3, xu, v);
		v5.addVertexWithUV(0.5-core, dy+dvl, 0.5+core*dir, u, v);

		v5.addVertexWithUV(0.5+core, dy+vl+dvl, 0.5+core*dir+out, u, xv);
		v5.addVertexWithUV(0.5+core, dy+vl, 0.5+core*dir*3+out, xu, xv);
		v5.addVertexWithUV(0.5+core, dy+0, 0.5+core*dir*3, xu, v);
		v5.addVertexWithUV(0.5+core, dy+dvl, 0.5+core*dir, u, v);

		v5.addVertexWithUV(0.5-core, dy+dvl, 0.5+core*dir, u, v);
		v5.addVertexWithUV(0.5+core, dy+dvl, 0.5+core*dir, xu, v);
		v5.addVertexWithUV(0.5+core, dy+vl+dvl, 0.5+core*dir+out, xu, xv);
		v5.addVertexWithUV(0.5-core, dy+vl+dvl, 0.5+core*dir+out, u, xv);

		v5.addVertexWithUV(0.5-core, dy, 0.5+core*dir*3, u, v);
		v5.addVertexWithUV(0.5+core, dy, 0.5+core*dir*3, xu, v);
		v5.addVertexWithUV(0.5+core, dy+vl, 0.5+core*dir*3+out, xu, xv);
		v5.addVertexWithUV(0.5-core, dy+vl, 0.5+core*dir*3+out, u, xv);

		v5.addVertexWithUV(0.5-core, dy, 0.5+core*dir*3, u, v);
		v5.addVertexWithUV(0.5+core, dy, 0.5+core*dir*3, xu, v);
		v5.addVertexWithUV(0.5+core, dy+dvl+0.0025, 0.5-core*dir*3+out*2.56, xu, xv);
		v5.addVertexWithUV(0.5-core, dy+dvl+0.0025, 0.5-core*dir*3+out*2.56, u, xv);
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return GeoStrata.proxy.crystalRender;
	}

}
