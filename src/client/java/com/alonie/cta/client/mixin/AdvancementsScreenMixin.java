package com.alonie.cta.client.mixin;

import com.alonie.cta.client.AdvancementClickHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class AdvancementsScreenMixin {

	@Inject(method = "tick", at = @At("HEAD"))
	private void onTick(CallbackInfo ci) {
		if ((Object) this instanceof AdvancementsScreen screen) {
			AdvancementClickHandler.doBlink(screen);
		}
	}
}
