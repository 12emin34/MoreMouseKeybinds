package me._12emin34.moremousekeybinds.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
    @Entry(name = "Periodic attack speed matches attack cooldown speed")
    public static boolean periodicAttackMatchCooldownSpeed = true;

    @Entry(name = "Periodic attack delay (in ticks)")
    public static int periodicAttackDelay = 40;
}
