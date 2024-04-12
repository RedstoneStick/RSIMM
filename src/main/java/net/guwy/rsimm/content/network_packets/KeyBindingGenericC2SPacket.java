package net.guwy.rsimm.content.network_packets;

import net.guwy.rsimm.enums.KeyActionTypes;
import net.guwy.rsimm.enums.KeyBinds;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.index.RsImmSounds;
import net.guwy.rsimm.mechanics.capabilities.custom.ArcReactorSlot;
import net.guwy.rsimm.mechanics.keybind.IIronmanKeybindCapableArmor;
import net.guwy.rsimm.mechanics.keybind.IIronmanKeybindCapableItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KeyBindingGenericC2SPacket {

    private KeyActionTypes keyActionType;
    private KeyBinds keyBind;
    public KeyBindingGenericC2SPacket(KeyActionTypes keyActionType, KeyBinds keyBind) {
        this.keyActionType = keyActionType;
        this.keyBind = keyBind;

    }

    public KeyBindingGenericC2SPacket(FriendlyByteBuf buf) {
        this.keyActionType = buf.readEnum(KeyActionTypes.class);
        this.keyBind = buf.readEnum(KeyBinds.class);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(keyActionType);
        buf.writeEnum(keyBind);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!

            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            boolean stop = false;
            ItemStack itemStack;

            // First look for functionality on the held items
            itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(itemStack.getItem() instanceof IIronmanKeybindCapableItem item && !stop){
                stop = !item.keybindInput(player, itemStack, this.keyActionType, this.keyBind);
            }
            itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
            if(itemStack.getItem() instanceof IIronmanKeybindCapableItem item && !stop){
                stop = !item.keybindInput(player, itemStack, this.keyActionType, this.keyBind);
            }

            // Second look for functionality on the worn armor (only 1 piece of your armor should handle this if you have a set)
            for(EquipmentSlot equipmentSlot : EquipmentSlot.values()){
                if(equipmentSlot.getType() == EquipmentSlot.Type.ARMOR){
                    itemStack = player.getItemBySlot(equipmentSlot);
                    if(itemStack.getItem() instanceof IIronmanKeybindCapableArmor item && !stop){
                        stop = !item.keybindArmorInput(player, itemStack, this.keyActionType, this.keyBind, equipmentSlot);
                    }
                }
            }

            // Lastly look for functionality on custom
            if(!stop){
                if(this.keyBind == KeyBinds.ARMOR_KEY) arcReactorFunctionality(player, keyActionType);
            }
        });
        return true;
    }



    private void arcReactorFunctionality(Player player, KeyActionTypes keyActionType){
        // Send charge level as chat message when pressed
        if(keyActionType == KeyActionTypes.PRESS){
            sendArcReactorEnergyMesage(player);
        }
        // Extract reactor when starting to hold
        if(keyActionType == KeyActionTypes.HOLD_START){
            ArcReactorSlot.removeArcReactor(player);
        }
    }

    private void sendArcReactorEnergyMesage(Player player){
        Level level = player.getLevel();

        player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {
            if(arcReactor.hasArcReactor()){

                Component titleText = Component.translatable("arc_reactor.rsimm.chat_display_title").withStyle(ChatFormatting.GOLD);
                Component nameText = Component.translatable("arc_reactor.rsimm.chat_display_name").append(arcReactor.getArcReactorTypeName()).withStyle(ChatFormatting.AQUA);
                Component energyText = Component.translatable("arc_reactor.rsimm.energy").withStyle(ChatFormatting.AQUA)
                        .append(getEnergyBar(arcReactor.getArcReactorEnergyCapacity(), arcReactor.getArcReactorEnergy()))
                        .withStyle(getDisplayColour(arcReactor.getArcReactorEnergyCapacity(), arcReactor.getArcReactorEnergy()));

                player.sendSystemMessage(titleText);
                player.sendSystemMessage(nameText);
                player.sendSystemMessage(energyText);

                // Arc Reactor Check Sound
                level.playSound(null, player, RsImmSounds.ARC_REACTOR_CHECK.get(), SoundSource.PLAYERS, 1, 1);
            }
        });
    }

    private String getEnergyBar(long maxEnergy, long energy){
        StringBuilder str = new StringBuilder();
        long increments = maxEnergy / 20;
        for(int i=1; i <= 20; i++){
            if(energy >= increments * i){
                str.append("|");
            }   else {
                str.append(".");
            }
        }
        return str.toString();
    }

    private ChatFormatting getDisplayColour(long maxEnergy, long energy){
        long increments = maxEnergy / 10;
        if(energy == 0){
            return ChatFormatting.DARK_GRAY;
        } else if(energy <= increments){
            return ChatFormatting.DARK_RED;
        } else if (energy <= increments*2) {
            return ChatFormatting.RED;
        } else if (energy <= increments*4) {
            return ChatFormatting.YELLOW;
        }   else {
            return ChatFormatting.AQUA;
        }
    }
}
