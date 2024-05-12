/*
 * Copyright 2022 Vulpes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sh.talonfox.vulpes_std.mixins.client;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.*;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.talonfox.vulpes_std.mixins.IPackRepoAccessor;
import sh.talonfox.vulpesloader.mod.VulpesModLoader;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "allowsTelemetry", at = @At("HEAD"), cancellable = true)
    public void vulpes$disableTelemetry(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Redirect(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;reload()V"),
            method = "<init>(Lnet/minecraft/client/main/GameConfig;)V"
    )
    private void vulpes$addResources(PackRepository packRepository) {
        final var access = (IPackRepoAccessor)packRepository;
        final var sources = Sets.newHashSet(Objects.requireNonNull(access.getSources()));
        sources.add((packList) -> VulpesModLoader.INSTANCE.getModJars().forEach((id, jar) -> {
            final var info = new PackLocationInfo(id + "_resources", Component.literal(Objects.requireNonNull(VulpesModLoader.INSTANCE.getMods().get(id).getName())), PackSource.BUILT_IN, Optional.empty());
            final Pack packResourceInfo = Pack.readMetaAndCreate(info, new FilePackResources.FileResourcesSupplier(Paths.get(jar)), PackType.SERVER_DATA, new PackSelectionConfig(true,Pack.Position.TOP,false));
            packList.accept(packResourceInfo);
        }));
        access.setSources(Set.copyOf(sources));
        packRepository.reload();
    }
}
