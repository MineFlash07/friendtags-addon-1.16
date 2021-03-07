package de.raik.friendtags;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.renderer.RenderEntityEvent;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class NameTagRenderer {

    private final FriendTagsAddon addon;

    public NameTagRenderer(FriendTagsAddon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onRender(RenderEntityEvent event) {
        if (!this.addon.isEnabled()) {
            return;
        }
        if (!(event.getEntity() instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntity();
        if (player.getUniqueID().equals(LabyMod.getInstance().getPlayerUUID())) {
            return;
        }
        if (player.isInvisibleToPlayer(Minecraft.getInstance().player) || player.isBeingRidden() || player.isSneaking()) {
            return;
        }

        double distance = player.getDistanceSq(Minecraft.getInstance().player);

        if (distance >= (64D * 64D)) {
            return;
        }

    }



}
