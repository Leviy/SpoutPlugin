/*
 * This file is part of SpoutPlugin.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutPlugin is licensed under the GNU Lesser General Public License.
 *
 * SpoutPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.spout.block.mcblock;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import gnu.trove.map.hash.TIntIntHashMap;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.BlockChest;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.TileEntity;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.getspout.spout.block.SpoutCraftChunk;
import org.getspout.spout.player.SpoutCraftPlayer;
import org.getspout.spoutapi.SpoutManager;

public class CustomChest extends BlockChest implements CustomMCBlock {
	protected BlockChest parent;

	protected CustomChest(BlockChest parent) {
		super(parent.id);
		this.parent = parent;

		updateField(parent, this, "strength");
		updateField(parent, this, "durability");
		updateField(parent, this, "cd");
		updateField(parent, this, "ce");
		updateField(parent, this, "cf");
		this.minX = parent.minX;
		this.minY = parent.minY;
		this.minZ = parent.minZ;
		this.maxX = parent.maxX;
		this.maxY = parent.maxY;
		this.maxZ = parent.maxZ;
		this.stepSound = parent.stepSound;
		this.co = parent.co;
		this.frictionFactor = parent.frictionFactor;
		updateField(parent, this, "name");

		isTileEntity = true;
	}

	@Override
	public Block getParent() {
		return parent;
	}

	@Override
	public void setHardness(float hardness) {
		this.strength = hardness;
		updateField(this, parent, "strength");
	}

	public float getExplosionResistance() {
		return this.durability;
	}

	public void setExplosionResistance(float resistance) {
		this.durability = resistance;
	}

	@Override
	public TileEntity a(World world) {
		try {
			Method a = BlockContainer.class.getDeclaredMethod("a_", (Class[]) null);
			a.setAccessible(true);
			return (TileEntity) a.invoke(parent, (Object[]) null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void r_() {
		try {
			Method r_ = Block.class.getDeclaredMethod("r_", (Class[]) null);
			r_.setAccessible(true);
			r_.invoke(parent, (Object[]) null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean c() {
		return parent.c();
	}

	@Override
	public float m(World world, int i, int j, int k) {
		return parent.m(world, i, j, k);
	}

	@Override
	public Block c(float f) {
		return super.c(f);
	}

	@Override
	public void a(float f, float f1, float f2, float f3, float f4, float f5) {
		if (parent != null) {
			parent.a(f, f1, f2, f3, f4, f5);
		} else {
			super.a(f, f1, f2, f3, f4, f5);
		}
	}

	@Override
	public boolean c(IBlockAccess iblockaccess, int i, int j, int k) {
		return parent.c(iblockaccess, i, j, k);
	}

	@Override
	public int a(int i, int j) {
		return parent.a(i, j);
	}

	@Override
	public int a(int i) {
		return parent.a(i);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List list, Entity entity) {
		parent.a(world, i, j, k, axisalignedbb, list, entity);
	}

	@Override
	public AxisAlignedBB e(World world, int i, int j, int k) {
		return parent.e(world, i, j, k);
	}

	@Override
	public boolean d() {
		if (parent != null) {
			return parent.d();
		}
		return super.d();
	}

	@Override
	public boolean a(int i, boolean flag) {
		return parent.a(i, flag);
	}

	@Override
	public boolean l() {
		return parent.l();
	}

	@Override
	public void b(World world, int i, int j, int k, Random random) {
		parent.b(world, i, j, k, random);
	}

	@Override
	public void postBreak(World world, int i, int j, int k, int l) {
		parent.postBreak(world, i, j, k, l);
	}

	@Override
	public void doPhysics(World world, int i, int j, int k, int l) {
		parent.doPhysics(world, i, j, k, l);
	}

	@Override
	public int b() {
		return parent.b();
	}

	@Override
	public void onPlace(World world, int i, int j, int k) {
		parent.onPlace(world, i, j, k);
	}

	@Override
	public void remove(World world, int i, int j, int k, int l, int i1) {
		parent.remove(world, i, j, k, l, i1);
	}

	@Override
	public int a(Random random) {
		return parent.a(random);
	}

	@Override
	public int getDropCount(int i, Random random) {
		return parent.getDropCount(i, random);
	}

	@Override
	public float getDamage(EntityHuman entityhuman, World world, int i, int j, int k) {
		return parent.getDamage(entityhuman, world, i, j, k);
	}


	@Override
	public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
		parent.dropNaturally(world, i, j, k, l, f, i1);
	}

	@Override
	public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
		return parent.a(world, i, j, k, vec3d, vec3d1);
	}

	@Override
	public void wasExploded(World world, int i, int j, int k) {
		parent.wasExploded(world, i, j, k);
	}

	@Override
	public boolean canPlace(World world, int i, int j, int k, int l) {
		return parent.canPlace(world, i, j, k, l);
	}

	@Override
	public boolean canPlace(World world, int i, int j, int k) {
		return parent.canPlace(world, i, j, k);
	}

	@Override
	public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
		return parent.interact(world, i, j, k, entityhuman, l, f, f1, f2);
	}

	@Override
	public void b(World world, int i, int j, int k, Entity entity) {
		parent.b(world, i, j, k, entity);
	}

	@Override
	public void postPlace(World world, int i, int j, int k, int l, float f, float f1, float f2) {
		parent.postPlace(world, i, j, k, l, f, f1, f2);
	}

	@Override
	public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {
		if (entityhuman instanceof EntityPlayer) {
			SpoutCraftPlayer player = (SpoutCraftPlayer) SpoutManager.getPlayer((Player) ((EntityPlayer) entityhuman).getBukkitEntity());
			player.setLastClickedLocation(new Location(player.getWorld(), i, j, k));
		}
		parent.attack(world, i, j, k, entityhuman);
	}

	@Override
	public void a(World world, int i, int j, int k, Entity entity, Vec3D vec3d) {
		parent.a(world, i, j, k, entity, vec3d);
	}

	@Override
	public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
		parent.updateShape(iblockaccess, i, j, k);
	}

	@Override
	public boolean a(IBlockAccess iblockaccess, int x, int y, int z, int face) {
		int index = CustomBlock.getIndex(((World) iblockaccess), x, y, z);
		Chunk chunk = ((World) iblockaccess).getChunkAt(x >> 4, z >> 4).bukkitChunk;
		if (chunk.getClass().equals(SpoutCraftChunk.class)) {
			TIntIntHashMap powerOverrides = ((SpoutCraftChunk) chunk).powerOverrides;
			if (powerOverrides.containsKey(index)) {
				int powerbits = powerOverrides.get(index);
				switch (face) {
					case 0:
						return (powerbits & (1 << 0)) != 0;
					case 1:
						return (powerbits & (1 << 1)) != 0;
					case 2:
						return (powerbits & (1 << 2)) != 0;
					case 3:
						return (powerbits & (1 << 3)) != 0;
					case 4:
						return (powerbits & (1 << 4)) != 0;
					case 5:
						return (powerbits & (1 << 5)) != 0;
					default:
						return parent.a(iblockaccess, x, y, z, face);
				}
			}
		}
		return parent.a(iblockaccess, x, y, z, face);
	}

	@Override
	public boolean isPowerSource() {
		return parent.isPowerSource();
	}

	@Override
	public void a(World world, int i, int j, int k, Entity entity) {
		parent.a(world, i, j, k, entity);
	}

	@Override
	public boolean c(World world, int i, int j, int k, int l) {
		return parent.c(world, i, j, k, l);
	}

	@Override
	public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
		parent.a(world, entityhuman, i, j, k, l);
	}

	@Override
	public boolean d(World world, int i, int j, int k) {
		return parent.d(world, i, j, k);
	}

	@Override
	public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
		parent.postPlace(world, i, j, k, entityliving);
	}

	@Override
	public void b(World world, int i, int j, int k, int l, int i1) {
		parent.b(world, i, j, k, l, i1);
	}

	private static void updateField(Block parent, Block child, String fieldName) {
		try {
			Field field = Block.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(child, field.get(parent));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
