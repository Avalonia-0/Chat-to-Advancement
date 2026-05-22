package com.alonie.cta.client;

import com.alonie.cta.ChatToAdvancements;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client entrypoint.
 * <p>
 * The advancement-name-to-click-event injection is handled server-side by
 * {@code AdvancementFrameMixin}.  This class exists only for the entrypoint
 * so the client mixins (RunCommandMixin, AdvancementsScreenMixin) are loaded.
 */
public class ChatToAdvancementsClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(ChatToAdvancements.MOD_ID + "-client");
	public static final String COMMAND_PREFIX = "cta:open ";

	@Override
	public void onInitializeClient() {
		LOGGER.info("Chat to Advancements client ready.");
	}
}
