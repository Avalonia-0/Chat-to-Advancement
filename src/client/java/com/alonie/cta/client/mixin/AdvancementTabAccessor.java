package com.alonie.cta.client.mixin;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * Exposes private fields on {@link AdvancementTab} so the screen mixin can
 * look up widgets and read the current scroll / pan state.
 */
@Mixin(AdvancementTab.class)
public interface AdvancementTabAccessor {

	@Accessor
	Map<AdvancementEntry, AdvancementWidget> getWidgets();

	@Accessor
	double getOriginX();

	@Accessor
	void setOriginX(double originX);

	@Accessor
	double getOriginY();

	@Accessor
	void setOriginY(double originY);

	@Accessor
	int getMinPanX();

	@Accessor
	int getMaxPanX();

	@Accessor
	int getMinPanY();

	@Accessor
	int getMaxPanY();

	@Accessor
	int getIndex();
}
