package de.raik.friendtags;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.renderer.RenderNameTagEvent;
import net.labymod.labyconnect.user.ChatUser;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.labymod.user.group.EnumGroupDisplayType;
import net.labymod.utils.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.UUID;

public class NameTagRenderer {

    private final FriendTagsAddon addon;

    public NameTagRenderer(FriendTagsAddon addon) {
        this.addon = addon;
    }

    @Subscribe(priority = 10)
    public void onRender(RenderNameTagEvent event) {
        if (!this.addon.isEnabled()) {
            return;
        }
        if (event.getPosition() != RenderNameTagEvent.Position.AFTER_NAME_TAG) {
            return;
        }
        PlayerEntity player = event.getEntity();
        if (player.getUniqueID().equals(LabyMod.getInstance().getPlayerUUID())) {
            return;
        }
        if (player.isSneaking()) {
            return;
        }
        if (this.isNotFriend(player.getUniqueID())) {
            return;
        }

        this.render(event);
    }

    private void render(RenderNameTagEvent eventPayload) {
        eventPayload.getMatrixStack().push();
        Minecraft.getInstance().fontRenderer.getClass();
        User user = LabyMod.getInstance().getUserManager().getUser(eventPayload.getEntity().getUniqueID());
        if (user.getGroup() != null && user.getGroup().getDisplayType() == EnumGroupDisplayType.ABOVE_HEAD) {
            eventPayload.getMatrixStack().translate(0.0D, 9.0F * 1.15 * 0.01666668F * 0.8F, 0.0D);
        }
        eventPayload.getMatrixStack().translate(0.0D, 0.15F, 0.0D);
        float scale = 0.01666668F * 1.8F;
        eventPayload.getMatrixStack().rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
        eventPayload.getMatrixStack().scale(-scale, -scale, scale);
        String tag = ModUtils.translateAlternateColorCodes('&', this.addon.getFriendFormat());
        float x = -Minecraft.getInstance().fontRenderer.getStringPropertyWidth(new StringTextComponent(tag)) / 2.0F;
        Minecraft.getInstance().fontRenderer.renderString(tag, x, 0.0F, -1, false, eventPayload.getMatrixStack().getLast().getMatrix(), eventPayload.getBuffer(), false, 0, eventPayload.getPackedLight());
        Minecraft.getInstance().fontRenderer.getClass();
        eventPayload.getMatrixStack().pop();
    }

    private boolean isNotFriend(UUID targetUUID) {
        for (ChatUser chatUser: LabyMod.getInstance().getLabyConnect().getFriends()) {
            if (chatUser.getGameProfile().getId().equals(targetUUID)) {
                return false;
            }
        }
        return true;
    }


}
