package de.maxhenkel.savefix;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.savefix.config.SaveFixConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFix implements ModInitializer {

    public static final String MODID = "savefix";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static SaveFixConfig SERVER_CONFIG;

    @Override
    public void onInitialize() {
        SERVER_CONFIG = ConfigBuilder
                .builder(SaveFixConfig::new)
                .path(FabricLoader.getInstance().getConfigDir().resolve(MODID).resolve("savefix.properties"))
                .build();
    }

}
