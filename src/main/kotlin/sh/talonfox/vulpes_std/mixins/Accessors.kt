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
package sh.talonfox.vulpes_std.mixins

import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.resources.ResourceKey
import net.minecraft.server.packs.repository.PackRepository
import net.minecraft.server.packs.repository.RepositorySource
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTabs
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Mutable
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(PackRepository::class)
interface IPackRepoAccessor {
    @get:Accessor
    @set:Accessor
    @set:Mutable
    var sources: Set<RepositorySource?>?
}