package net.guwy.rsimm.content.items.armors.parts.power_supply;

import net.guwy.rsimm.content.items.armors.parts.EIronManBootPriority;
import net.guwy.rsimm.content.items.armors.parts.EIronManPartSlots;
import net.guwy.rsimm.content.items.armors.parts.GenericIronManArmorPart;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractPowerSupply extends GenericIronManArmorPart {
    SuitPowerSupplyEnergyTypes energyType;

    /**
     * @param pProperties
     * @param speedReduction amount of deceleration caused by the component when flying
     * @param energyType     the behaviour the suit power supply will have when powering the suit
     */
    public AbstractPowerSupply(Properties pProperties, float speedReduction, SuitPowerSupplyEnergyTypes energyType) {
        super(pProperties, speedReduction, EIronManPartSlots.POWER_SUPPLY_SLOT);
        this.energyType = energyType;
    }

    @Override
    public void armorPartTick(Entity player, ItemStack partStack, HashMap<EIronManBootPriority, Boolean> bootState) {
        super.armorPartTick(player, partStack, bootState);
    }

    private void distributeEnergy(Entity entity, ItemStack partStack){

    }


    // Is a bad method, access the arc reactor with the capability instead
    //private ItemStack getPlayerReactor(Entity entity){
    //    AtomicReference<ItemStack> reactor = new AtomicReference<>(ItemStack.EMPTY);
    //    entity.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactorSlot -> {
    //        ItemStack itemStack = new ItemStack(Item.byId(arcReactorSlot.getArcReactorTypeId()));
    //        reactor.set(itemStack);
    //    });
    //    return reactor.get();
    //}
}
