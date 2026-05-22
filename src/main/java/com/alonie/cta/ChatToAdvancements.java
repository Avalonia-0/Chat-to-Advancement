package com.alonie.cta;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatToAdvancements implements ModInitializer {
	public static final String MOD_ID = "chat-to-advancements";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Chat to Advancements loaded.");
	}
}
