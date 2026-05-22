package com.alonie.cta.client;

import com.alonie.cta.ChatToAdvancements;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientAdvancementManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class AdvancementsReloadedCompat {
	public static final boolean LOADED;

	static {
		LOADED = FabricLoader.getInstance().isModLoaded("advancements_reloaded");
		if (LOADED) ChatToAdvancements.LOGGER.info("advr compat enabled");
	}

	public static void openAndSelect(ClientAdvancementManager mgr, AdvancementEntry targetEntry) {
		try {
			Class<?> screenCls = Class.forName("codes.atomys.advr.screens.AdvancementReloadedScreen");
			Class<?> widgetCls = Class.forName("codes.atomys.advr.screens.AdvancementReloadedWidget");
			Class<?> tabCls = Class.forName("codes.atomys.advr.screens.AdvancementReloadedTab");

			Screen screen = (Screen) screenCls.getConstructor(ClientAdvancementManager.class).newInstance(mgr);
			MinecraftClient.getInstance().setScreen(screen);

			PlacedAdvancement node = mgr.getManager().get(targetEntry);
			Method getWidget = screenCls.getMethod("getAdvancementWidget", PlacedAdvancement.class);
			Object widget = getWidget.invoke(screen, node);
			if (widget == null) return;

			Field tabField = widgetCls.getDeclaredField("tab"); tabField.setAccessible(true);
			Object tab = tabField.get(widget);

			screenCls.getMethod("setSelectedTab", tabCls).invoke(screen, tab);
			screenCls.getMethod("setSelectedWidget", widgetCls).invoke(screen, widget);

			ChatToAdvancements.LOGGER.info("advr: selected '{}'", targetEntry.id());
		} catch (Exception e) {
			ChatToAdvancements.LOGGER.error("advr compat failed", e);
		}
	}
}
