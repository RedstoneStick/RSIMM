package net.guwy.rsimm.content.items.armors.gen_2;

import net.guwy.rsimm.content.entities.projectiles.RepulsorBeamEntity;
import net.guwy.rsimm.enums.RepulsorAttackType;
import net.guwy.rsimm.index.RsImmEntityTypes;
import net.guwy.rsimm.mechanics.capabilities.forge.energy_item.ItemEnergyStorageImpl;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;

public class RepulsorItem extends Item {
    int blastMaxCharge, blastMaxDamage, blastRange, blastMaxEnergyConsumption;
    int beamChargeTime, beamMaxDamage, beamRange, beamEnergyConsumptionPerTick;
    int flightMaxThrust, flightMaxEnergyConsumption;
    double blastHeat, beamHeat, flightMaxThrustHeat;
    int energyBuffer;

    String BEAM_CHARGE_TAG = "beam_charge", BLAST_CHARGE_TAG = "blast_charge", STARTED_CHARGING_TAG = "started_charging";

    public RepulsorItem(Properties pProperties, int blastMaxCharge, int blastMaxDamage, int blastRange, int blastMaxEnergyConsumption,
                        int beamChargeTime, int beamMaxDamage, int beamRange, int beamEnergyConsumptionPerTick,
                        int flightMaxThrust, int flightMaxEnergyConsumption,
                        double blastHeat, double beamHeat, double flightMaxThrustHeat,
                        int energyBuffer) {
        super(pProperties);

        this.blastMaxCharge = blastMaxCharge;
        this.blastMaxDamage = blastMaxDamage;
        this.blastRange = blastRange;
        this.blastMaxEnergyConsumption = blastMaxEnergyConsumption;

        this.beamChargeTime = beamChargeTime;
        this.beamMaxDamage = beamMaxDamage;
        this.beamRange = beamRange;
        this.beamEnergyConsumptionPerTick = beamEnergyConsumptionPerTick;

        this.flightMaxThrust = flightMaxThrust;
        this.flightMaxEnergyConsumption = flightMaxEnergyConsumption;

        this.blastHeat = blastHeat;
        this.beamHeat = beamHeat;
        this.flightMaxThrustHeat = flightMaxThrustHeat;

        this.energyBuffer = energyBuffer;
    }



    public void useRepulsor(ItemStack itemStack, Level level, Player player, RepulsorAttackType attackType, @Nullable ItemStack armorItem){
        double effectiveness = 1 - ((double)itemStack.getDamageValue() / itemStack.getMaxDamage());

        // Offset for shooting from hand
        double playerY = player.getYRot();
        playerY += player.getItemInHand(InteractionHand.MAIN_HAND) == itemStack ? 180 : 0;      // Fires from the right side if the item is detected on the main hand
        playerY += player.getMainArm() == HumanoidArm.LEFT ? 180 : 0;      // Switches side if the player is left-handed
        double x = Math.cos(Math.toRadians(playerY)) * 0.35;
        double z = Math.sin(Math.toRadians(playerY)) * 0.35;


        // Beam Attack
        if(attackType == RepulsorAttackType.BEAM){
            beamAttack(itemStack, player, level, effectiveness, x, z, armorItem,
                    this.beamEnergyConsumptionPerTick, this.beamChargeTime, this.beamRange, this.beamMaxDamage);
        }
    }

    public void stopUsingRepulsor(ItemStack itemStack, Level level, Player player){
        if(ItemTagUtils.getBoolean(itemStack, STARTED_CHARGING_TAG)){
            ItemTagUtils.putBoolean(itemStack, STARTED_CHARGING_TAG, false);
            player.displayClientMessage(Component.literal("! missing sound (Repulsor Powering Down)"), true);
        }

        // Beam Attack
        beamRelease(itemStack);

        // Blast Attack
        int blastCharge = ItemTagUtils.getInt(itemStack, BLAST_CHARGE_TAG);
        if(blastCharge > 0){
            player.displayClientMessage(Component.literal("! missing sound"), false);

            ItemTagUtils.putInt(itemStack, BLAST_CHARGE_TAG, 0);
        }
    }

    public void onRepulsorFail(ItemStack itemStack, Level level, Player player){
        player.displayClientMessage(Component.literal("! missing sound (Repulsor Failed To Power Up)"), true);
        // Stop firing the repulsor (in armor)
    }



    private void beamAttack(ItemStack itemStack, Player player, Level level, double effectiveness, double xNudge, double yNudge,
                            @Nullable ItemStack armorItem,
                            int energyConsumption, int chargeTime, int range, int maxDamage){
        int charge = ItemTagUtils.getInt(itemStack, BEAM_CHARGE_TAG);
        AtomicInteger energy = new AtomicInteger(0);

        itemStack.getCapability(ForgeCapabilities.ENERGY).ifPresent(e -> {
            energy.set(e.getEnergyStored());
        });

        // Fire when there's enough energy in the buffer
        if(energy.get() >= energyConsumption){

            // Fire if it is sufficiently charged
            if(charge >= chargeTime){

                RepulsorBeamEntity repulsorBeam = new RepulsorBeamEntity(RsImmEntityTypes.REPULSOR_BEAM.get(), player, level,
                        range, (int) (maxDamage * Math.pow(effectiveness, 1f/3 )));

                float speedMul = 3;
                repulsorBeam.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, speedMul, (float) (1 - effectiveness) * 3);

                repulsorBeam.setPos(repulsorBeam.getX() + xNudge, repulsorBeam.getY() -0.2, repulsorBeam.getZ() + yNudge);

                level.addFreshEntity(repulsorBeam);
                player.displayClientMessage(Component.literal("! missing sound (Repulsor Beam Firing)"), true);

                // Decrease energy
                itemStack.getCapability(ForgeCapabilities.ENERGY).ifPresent(e -> {
                    e.extractEnergy(energyConsumption, false);
                });
            }
            // Increase charge if the charge hasn't reached sufficient level yet
            else {

                if(!ItemTagUtils.getBoolean(itemStack, STARTED_CHARGING_TAG)){
                    player.displayClientMessage(Component.literal("! missing sound (Repulsor Powering Up)"), true);
                    ItemTagUtils.putBoolean(itemStack, STARTED_CHARGING_TAG, true);
                }
                ItemTagUtils.putInt(itemStack, BEAM_CHARGE_TAG, charge + 1);
            }
        }
        else {
            onRepulsorFail(itemStack, level, player);
        }
    }

    private void beamRelease(ItemStack itemStack){
        if(ItemTagUtils.getInt(itemStack, BEAM_CHARGE_TAG) > 0){
            ItemTagUtils.putInt(itemStack, BEAM_CHARGE_TAG, 0);
        }
    }



    /** Forge Energy Capability */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == ForgeCapabilities.ENERGY)
                    return LazyOptional.of(() -> new ItemEnergyStorageImpl(stack, energyBuffer)).cast();
                return LazyOptional.empty();
            }
        };
    }
}
