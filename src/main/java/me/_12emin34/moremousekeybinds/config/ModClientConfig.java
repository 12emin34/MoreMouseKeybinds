package me._12emin34.moremousekeybinds.config;

import me._12emin34.moremousekeybinds.MoreMouseKeybindsClient;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;

@Version(version = 1)
public class ModClientConfig extends Config {
	public ModClientConfig() {
		super(MoreMouseKeybindsClient.CLIENT_CONFIG_ID);
	}

//	@Entry(name = "Periodic attack speed matches attack cooldown speed")
	public boolean periodicAttackMatchCooldownSpeed = true;

//	@Entry(name = "Periodic attack delay (in ticks)")
	public int periodicAttackDelay = 40;
}
