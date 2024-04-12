package net.guwy.rsimm.events.server_events;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.content.items.armors.gen_3.GenericIronManArmorItem;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class AttachCapabilitiesHandler {

    public static void initEntity(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(RsImmCapabilities.Player.ARC_REACTOR).isPresent()) {
                event.addCapability(new ResourceLocation(RsImm.MOD_ID, "properties"), new RsImmCapabilities.Player());
            }
        }
    }
}
