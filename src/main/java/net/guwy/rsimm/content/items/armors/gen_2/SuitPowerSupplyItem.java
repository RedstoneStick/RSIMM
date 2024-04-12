package net.guwy.rsimm.content.items.armors.gen_2;

import net.guwy.rsimm.enums.KeyActionTypes;
import net.guwy.rsimm.enums.KeyBinds;
import net.guwy.rsimm.content.items.armors.parts.power_supply.SuitPowerSupplyEnergyTypes;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.mechanics.capabilities.forge.energy_item.IItemEnergyContainer;
import net.guwy.rsimm.mechanics.capabilities.forge.energy_item.ItemEnergyStorageImpl;
import net.guwy.rsimm.mechanics.keybind.IIronmanKeybindCapableItem;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class SuitPowerSupplyItem extends Item implements IItemEnergyContainer, IIronmanKeybindCapableItem {
    int energyStorage, energyTransfer;
    SuitPowerSupplyEnergyTypes suitPowerSupplyType;

    int beamChargeTime, beamMaxDamage, beamRange, beamEnergyConsumptionPerTick;
    double beamHeat;
    Properties properties;

    String FIRE_BEAM_TAG_KEY = "beam_fire", FIRE_BEAM_PREV_TAG_KEY = "beam_fire_previous";

    public SuitPowerSupplyItem(Properties pProperties, SuitPowerSupplyEnergyTypes type, int energyStorage, int energyTransfer){
        this(pProperties, type, energyStorage, energyTransfer, 0, 0, 0, 0, 0);
    }

    public SuitPowerSupplyItem(Properties pProperties, SuitPowerSupplyEnergyTypes type, int energyStorage, int energyTransfer,
                               int beamChargeTime, int beamMaxDamage, int beamRange, int beamEnergyConsumptionPerTick, double beamHeat) {
        super(pProperties);
        this.energyStorage = energyStorage;
        this.energyTransfer = energyTransfer;
        this.suitPowerSupplyType = type;
        this.beamChargeTime = beamChargeTime;
        this.beamMaxDamage = beamMaxDamage;
        this.beamRange = beamRange;
        this.beamEnergyConsumptionPerTick = beamEnergyConsumptionPerTick;
        this.beamHeat = beamHeat;
        this.properties = pProperties;
    }

    /** @return processed Item */
    public ItemStack OnSuitTick(ItemStack itemStack, ItemStack armorItem, Player player){
        // Energy Distribution
        armorItem.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(inv -> {

            // process every item stored in the armor
            for(int i = 0; i < inv.getSlots(); i++){

                // do not process if the item is itself
                if(i != 1){
                    // Extract item to process
                    ItemStack processItem = inv.extractItem(i, 64, false);

                    processItem.getCapability(ForgeCapabilities.ENERGY).ifPresent(e -> {
                        itemStack.getCapability(ForgeCapabilities.ENERGY).ifPresent(energy -> {
                            player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {

                                // If the supply type is main draw energy from the main
                                if(this.suitPowerSupplyType == SuitPowerSupplyEnergyTypes.MAIN && energy.getEnergyStored() > 0){
                                    energy.extractEnergy(e.receiveEnergy(energy.extractEnergy(this.energyTransfer, true), false), false);
                                }
                                // If the main supply doesn't work and the player has enough energy in his reactor supply the energy from there
                                // the player's reactor will not try to charge stuff it can't fully charge
                                else if(0.05 < ((double) arcReactor.getArcReactorEnergy() / arcReactor.getArcReactorEnergyCapacity())){
                                    int requiredEnergy = e.receiveEnergy(Integer.MAX_VALUE, true);
                                    requiredEnergy = Math.min(requiredEnergy, this.energyTransfer);
                                    if(arcReactor.testAddEnergyLoad(requiredEnergy)){
                                        e.receiveEnergy(requiredEnergy, false);
                                        arcReactor.addEnergyLoad(requiredEnergy);
                                    }
                                }
                                // If the supply type is emergency and the player doesn't have enough power (<5%), supply the power
                                else if(this.suitPowerSupplyType == SuitPowerSupplyEnergyTypes.EMERGENCY &&
                                        0.05 > ((double) arcReactor.getArcReactorEnergy() / arcReactor.getArcReactorEnergyCapacity())){
                                    energy.extractEnergy(e.receiveEnergy(energy.extractEnergy(this.energyTransfer, true), false), false);
                                }
                            });
                        });
                    });

                    // Put the item back in storage
                    inv.insertItem(i, processItem, false);
                }
            }
        });



        // Beam Fire
        if(ItemTagUtils.getBoolean(itemStack, FIRE_BEAM_TAG_KEY)){
            player.displayClientMessage(Component.literal("Chest Beam Not Implemented Yet").withStyle(ChatFormatting.RED), true);

            // If wasn't firing previously, play start to fire sound
            if(!ItemTagUtils.getBoolean(itemStack, FIRE_BEAM_PREV_TAG_KEY)){

                ItemTagUtils.putBoolean(itemStack, FIRE_BEAM_PREV_TAG_KEY, true);
            }
        } else {
            // If was firing previously, play the stop sound
            if(ItemTagUtils.getBoolean(itemStack, FIRE_BEAM_PREV_TAG_KEY)){

                ItemTagUtils.putBoolean(itemStack, FIRE_BEAM_PREV_TAG_KEY, false);
            }
        }



        return itemStack;
    }

    @Override
    public boolean keybindInput(Player player, ItemStack itemStack, KeyActionTypes keyActionType, KeyBinds keyBind) {
        boolean res = false;

        // if the charge time is greater than 0 (to check if the supply can fire a beam), try to charge and fire beam
        if(this.beamChargeTime > 0){
            if(keyBind == KeyBinds.SPECIAL_KEY){
                if(keyActionType == KeyActionTypes.HOLD_START){
                    ItemTagUtils.putBoolean(itemStack, FIRE_BEAM_TAG_KEY, true);
                    res = true;
                }
                if(keyActionType == KeyActionTypes.HOLD_RELEASE){
                    ItemTagUtils.putBoolean(itemStack, FIRE_BEAM_TAG_KEY, false);
                    res =true;
                }
            }
        }

        return res;
    }



    /** Forge Energy Capability */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        IItemEnergyContainer container = this;
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == ForgeCapabilities.ENERGY)
                    return LazyOptional.of(() -> new ItemEnergyStorageImpl(stack, container)).cast();
                return LazyOptional.empty();
            }
        };
    }
    @Override
    public int getEnergyCapacity() {
        return this.energyStorage;
    }
    @Override
    public int getEnergyExtract() {
        return this.energyTransfer;
    }
    @Override
    public int getEnergyReceive() {
        return this.energyTransfer;
    }
}
