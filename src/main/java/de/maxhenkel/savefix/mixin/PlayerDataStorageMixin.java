package de.maxhenkel.savefix.mixin;

import de.maxhenkel.savefix.SaveFix;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.nio.file.Path;

@Mixin(PlayerDataStorage.class)
public class PlayerDataStorageMixin {

    @Unique
    private long startTime;

    @Redirect(method = "save", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtIo;writeCompressed(Lnet/minecraft/nbt/CompoundTag;Ljava/nio/file/Path;)V"))
    private void writeCompressed(CompoundTag tag, Path path) throws IOException {
        if (!SaveFix.SERVER_CONFIG.useBufferedIo.get()) {
            NbtIo.writeCompressed(tag, path);
            return;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
        NbtIo.writeCompressed(tag, dataOutputStream);
        dataOutputStream.flush();
        dataOutputStream.close();
    }

    @Inject(method = "save", at = @At("HEAD"))
    public void saveHead(Player player, CallbackInfo ci) {
        startTime = System.nanoTime();
    }

    @Inject(method = "save", at = @At("RETURN"))
    public void saveReturn(Player player, CallbackInfo ci) {
        if (SaveFix.SERVER_CONFIG.logSaveTime.get()) {
            SaveFix.LOGGER.info("Saving player data for {} took {}ms", player.getName().getString(), (System.nanoTime() - startTime) / 1_000_000.0D);
        }
    }

}
