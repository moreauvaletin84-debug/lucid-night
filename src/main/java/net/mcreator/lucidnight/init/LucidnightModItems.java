/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.lucidnight.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

import net.minecraft.world.item.Item;

import net.mcreator.lucidnight.item.TestItem;
import net.mcreator.lucidnight.LucidnightMod;

import java.util.function.Function;

public class LucidnightModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(LucidnightMod.MODID);
	public static final DeferredItem<Item> TEST;
	static {
		TEST = register("test", TestItem::new);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
		return REGISTRY.registerItem(name, supplier, Item.Properties::new);
	}
}