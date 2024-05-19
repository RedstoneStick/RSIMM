package net.guwy.rsimm.events.player_tick;

import net.guwy.rsimm.events.player_tick.content.ChestSlotCheck;
import net.guwy.rsimm.events.player_tick.content.ReactorUtilization;
import net.minecraftforge.event.TickEvent;

public class PlayerTickEventOrganizer {
    public static void init(TickEvent.PlayerTickEvent event){
        ChestSlotCheck.init(event);
        ReactorUtilization.init(event);
    }
}
