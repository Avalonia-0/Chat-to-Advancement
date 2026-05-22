package com.alonie.cta.client.mixin;

import com.alonie.cta.client.AdvancementClickHandler;
import com.alonie.cta.client.AdvancementsReloadedCompat;
import com.alonie.cta.client.ChatToAdvancementsClient;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class RunCommandMixin {

	@Inject(method = "runClickEventCommand", at = @At("HEAD"), cancellable = true)
	private void onRunClickEventCommand(String command, net.minecraft.client.gui.screen.Screen currentScreen,
	                                    CallbackInfo ci) {
		if (command == null || !command.startsWith(ChatToAdvancementsClient.COMMAND_PREFIX)) return;
		ci.cancel();

		String idStr = command.substring(ChatToAdvancementsClient.COMMAND_PREFIX.length());
		Identifier targetId = Identifier.tryParse(idStr);
		if (targetId == null) return;

		MinecraftClient client = MinecraftClient.getInstance();
		if (client == null || client.player == null) return;

		ClientAdvancementManager mgr = client.player.networkHandler.getAdvancementHandler();
		AdvancementEntry entry = mgr.get(targetId);
		if (entry == null) return;

		if (AdvancementsReloadedCompat.LOADED) {
			// Use advancements_reloaded's screen with widget highlighting
			AdvancementsReloadedCompat.openAndSelect(mgr, entry);
		} else {
			// Vanilla: open screen, select tab, start blink
			AdvancementEntry root = findRoot(mgr, entry);
			if (root == null) return;
			AdvancementsScreen screen = new AdvancementsScreen(mgr);
			client.setScreen(screen);
			mgr.selectTab(root, true);
			AdvancementClickHandler.startBlink(mgr.getManager().get(entry));
			ChatToAdvancementsClient.LOGGER.info("Blink started for '{}'", targetId);
		}
	}

	@Unique
	private static AdvancementEntry findRoot(ClientAdvancementManager mgr, AdvancementEntry entry) {
		AdvancementEntry cur = entry;
		for (int i = 0; i < 50; i++) {
			if (cur.value().isRoot()) return cur;
			Identifier parentId = cur.value().parent().orElse(null);
			if (parentId == null) return cur;
			AdvancementEntry parent = mgr.get(parentId);
			if (parent == null) return cur;
			cur = parent;
		}
		return entry;
	}
}
