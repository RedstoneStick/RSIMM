package net.guwy.rsimm.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class AtmosphericDensity {

    static int SEA_LEVEL = 62, NO_ATMOSPHERE_Y = 1000;

    public static double getAtmosphericDensity(Entity entity){

        // Overworld has a density changing depending on height
        if(entity.level().dimension() == Level.OVERWORLD){
            return Math.min(1.0, Math.max(0.0, 1 - ((entity.getY() - SEA_LEVEL) / ((double) NO_ATMOSPHERE_Y - SEA_LEVEL))));
        }
        // End is 0.3
        else if (entity.level().dimension() == Level.END){
            return 0.3;
        }
        // Any other dimension is 1
        else {
            return 1;
        }
    }
}
