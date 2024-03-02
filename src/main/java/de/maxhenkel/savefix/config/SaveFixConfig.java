package de.maxhenkel.savefix.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;

public class SaveFixConfig {

    public final ConfigEntry<Boolean> logSaveTime;
    public final ConfigEntry<Boolean> useBufferedIo;
    public final ConfigEntry<Boolean> useUncompressedNbtIo;

    public SaveFixConfig(ConfigBuilder builder) {
        logSaveTime = builder.booleanEntry(
                "log_save_time",
                true,
                "Logs the time it takes to save data"
        );
        useBufferedIo = builder.booleanEntry(
                "use_buffered_io",
                true,
                "Uses buffered IO when saving data"
        );
        useUncompressedNbtIo = builder.booleanEntry(
                "use_uncompressed_nbt_io",
                true,
                "Whether to use uncompressed NBT IO when saving data",
                "Uncompressed NBT files are readable by the vanilla game, so your saves won't break without the mod",
                "Speeds up saving large data files like if you have a lot of scoreboard entries"
        );
    }

}
