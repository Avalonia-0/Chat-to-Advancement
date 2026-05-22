package com.alonie.cta.mixin;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixes into the server-side method that creates the announcement text
 * for an earned advancement.  Before the text is sent to the client,
 * a {@code /cta:open <id>} RunCommand click event is embedded so that
 * clicking the advancement name in chat opens the advancements screen
 * centred on that advancement.
 */
@Mixin(AdvancementFrame.class)
public class AdvancementFrameMixin {

	@Inject(method = "getChatAnnouncementText", at = @At("RETURN"), cancellable = true)
	private void onAnnouncement(AdvancementEntry entry, ServerPlayerEntity player,
	                            CallbackInfoReturnable<MutableText> cir) {
		MutableText text = cir.getReturnValue();
		if (text != null && entry != null) {
			text.setStyle(text.getStyle().withClickEvent(
					new ClickEvent.RunCommand("cta:open " + entry.id())));
			cir.setReturnValue(text);
		}
	}
}
