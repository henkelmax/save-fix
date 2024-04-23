package de.maxhenkel.savefix.mixin;

import de.maxhenkel.savefix.SaveFix;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.saveddata.SavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.nio.file.Path;

@Mixin(SavedData.class)
public abstract class SavedDataMixin {

    @Redirect(method = "save(Ljava/io/File;Lnet/minecraft/core/HolderLookup$Provider;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtIo;writeCompressed(Lnet/minecraft/nbt/CompoundTag;Ljava/nio/file/Path;)V"))
    private void writeCompressed(CompoundTag tag, Path path) throws IOException {
        if (!SaveFix.SERVER_CONFIG.useBufferedIo.get()) {
            if (SaveFix.SERVER_CONFIG.useUncompressedNbtIo.get()) {
                NbtIo.write(tag, path);
            } else {
                NbtIo.writeCompressed(tag, path);
            }
            return;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
        if (SaveFix.SERVER_CONFIG.useUncompressedNbtIo.get()) {
            NbtIo.write(tag, dataOutputStream);
        } else {
            NbtIo.writeCompressed(tag, dataOutputStream);
        }
        dataOutputStream.flush();
        dataOutputStream.close();
    }

    @Unique
    private long startTime;

    @Inject(method = "save(Ljava/io/File;Lnet/minecraft/core/HolderLookup$Provider;)V", at = @At("HEAD"))
    public void saveHead(File file, HolderLookup.Provider provider, CallbackInfo ci) {
        startTime = System.nanoTime();
    }

    @Inject(method = "save(Ljava/io/File;Lnet/minecraft/core/HolderLookup$Provider;)V", at = @At("RETURN"))
    public void saveReturn(File file, HolderLookup.Provider provider, CallbackInfo ci) {
        if (SaveFix.SERVER_CONFIG.logSaveTime.get()) {
            SaveFix.LOGGER.info("Saving {} took {}ms", file.getName(), (System.nanoTime() - startTime) / 1_000_000.0D);
        }
    }

}
