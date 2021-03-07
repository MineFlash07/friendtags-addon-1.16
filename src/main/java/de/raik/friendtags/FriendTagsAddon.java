package de.raik.friendtags;

import com.google.gson.JsonObject;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Material;

import java.util.List;

public class FriendTagsAddon extends LabyModAddon {

    private boolean enabled = true;

    private String friendFormat = "&lFriend";

    @Override
    public void onEnable() {

    }

    @Override
    public void loadConfig() {
        JsonObject config = this.getConfig();

        this.enabled = config.has("enabled") ? config.get("enabled").getAsBoolean() : this.enabled;
        this.friendFormat = config.has("friendformat") ? config.get("friendformat").getAsString() : this.friendFormat;
    }

    @Override
    protected void fillSettings(List<SettingsElement> settings) {
        settings.add(new BooleanElement("Enabled", this, new ControlElement.IconData(Material.LEVER)
                , "enabled", this.enabled));
        settings.add(new StringElement("Tag format", this, new ControlElement.IconData("")
                , "friendformat", this.friendFormat));
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getFriendFormat() {
        return this.friendFormat;
    }
}
