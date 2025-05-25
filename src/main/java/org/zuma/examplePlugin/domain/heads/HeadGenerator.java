package org.zuma.examplePlugin.domain.heads;


import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;


public class HeadGenerator {

    public static ItemStack createHead(String base64) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        return setHeadMeta(head, base64);
    }

    public static ItemStack setHeadMeta(ItemStack itemStack, String base64) {
        itemStack.editMeta(SkullMeta.class, skullMeta -> {
            final UUID uuid = UUID.randomUUID();
            final PlayerProfile playerProfile = Bukkit.createProfile(uuid, uuid.toString().substring(0, 16));
            playerProfile.setProperty(new ProfileProperty("textures", base64));

            skullMeta.setPlayerProfile(playerProfile);
        });

        return itemStack;
    }


}
