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

package sh.talonfox.vulpes_std.mixins.listeners;

import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.talonfox.vulpes_std.debug.VulpesEarlyLog;
import sh.talonfox.vulpes_std.listeners.IRegisterListener;
import sh.talonfox.vulpesloader.api.VulpesListenerManager;

import java.util.concurrent.atomic.AtomicInteger;


@Mixin(BuiltInRegistries.class)
public class RegistryMixin {
    @Inject(
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true,
            method = "Lnet/minecraft/core/registries/BuiltInRegistries;freeze()V"
    )
    private static void vulpes$registryHook(CallbackInfo ci) {
        VulpesEarlyLog.addToLog("REGISTER BuiltInRegistries");
        var instances = VulpesListenerManager.getListeners(IRegisterListener.class);
        if (instances != null) {
            instances.forEach((clazz) -> ((IRegisterListener) clazz).register());
        }
    }
}
