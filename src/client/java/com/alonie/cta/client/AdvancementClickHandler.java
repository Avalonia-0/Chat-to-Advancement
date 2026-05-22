package com.alonie.cta.client;

import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;

/**
 * Shared state for the blink animation and click-to-advancement flow.
 * This is a plain utility class, not a mixin, so it can have any methods.
 */
public final class AdvancementClickHandler {

	// ── Blink state ──
	private static final int BLINK_TICKS = 160;
	private static final int BLINK_INTERVAL = 10;

	private static PlacedAdvancement blinkingNode;
	private static int blinkCounter;

	private static final AdvancementProgress DONE = new AdvancementProgress() {
		@Override public boolean isDone() { return true; }
		@Override public float getProgressBarPercentage() { return 1.05f; }
	};
	private static final AdvancementProgress EMPTY = new AdvancementProgress();

	private AdvancementClickHandler() {}

	// ── Blink API ──

	public static void startBlink(PlacedAdvancement node) {
		blinkingNode = node;
		blinkCounter = 0;
	}

	public static void doBlink(AdvancementsScreen screen) {
		if (blinkingNode == null) return;

		blinkCounter++;
		if (blinkCounter > BLINK_TICKS) {
			blinkingNode = null;
			return;
		}

		if (blinkCounter < BLINK_INTERVAL) return;

		boolean showDone = ((blinkCounter / BLINK_INTERVAL) % 2) == 0;
		screen.setProgress(blinkingNode, showDone ? DONE : EMPTY);
	}
}
