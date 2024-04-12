package net.guwy.rsimm.content.items.dev;

import net.guwy.rsimm.mechanics.capabilities.forge.item_continer_item.IItemContainerItem;
import net.guwy.rsimm.mechanics.capabilities.forge.item_continer_item.ItemStorageItemImpl;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class DevWand3Item extends Item implements IItemContainerItem {
    public DevWand3Item(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide){
            ItemStack itemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack extraItem = pPlayer.getItemInHand(InteractionHand.OFF_HAND);

            itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(inv -> {


                pPlayer.sendSystemMessage(Component.literal("1. capability present"));

                pPlayer.sendSystemMessage(Component.literal("2. stack in slot 0: " + inv.getStackInSlot(0).getDisplayName().getString()));

                if(!pPlayer.isCrouching()){
                    ItemStack remainingItem = extraItem;
                    pPlayer.getInventory().removeItem(extraItem);

                    for(int i = 0; remainingItem != ItemStack.EMPTY && i < inv.getSlots(); i++){
                        remainingItem = inv.insertItem(i, extraItem, false);
                        pPlayer.sendSystemMessage(Component.literal("3. set stack in slot " + i + " to: " + inv.getStackInSlot(i).getDisplayName().getString()));
                    }

                    pPlayer.getInventory().placeItemBackInInventory(remainingItem);

                }
                if(pPlayer.isCrouching()){
                    ItemStack extractedItem;

                    for(int i = 0; inv.getSlots() > i; i++){
                        extractedItem = inv.extractItem(i, inv.getSlotLimit(i), false);
                        pPlayer.getInventory().placeItemBackInInventory(extractedItem);
                        pPlayer.sendSystemMessage(Component.literal("3. removed item from slot " + i + ": " + inv.getStackInSlot(i).getDisplayName().getString()));
                    }
                }

            });

            pPlayer.getCooldowns().addCooldown(itemStack.getItem(), 5);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(inv -> {
            pTooltipComponents.add(Component.literal("hi"));

            ItemStack displayItem;
            for(int i = 0; inv.getSlots() > i; i++){

                displayItem = inv.getStackInSlot(i);
                if(displayItem != ItemStack.EMPTY){
                    pTooltipComponents.add(Component.literal(i + ": " + displayItem.getDisplayName().getString() + " x" + displayItem.getCount()));
                }
            }
        });
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }




    /** Item having forge Item Storage Capabilities Implementation */
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        IItemContainerItem container = this;
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == ForgeCapabilities.ITEM_HANDLER)
                    return LazyOptional.of(() -> new ItemStorageItemImpl(stack, container, 10) {
                    }).cast();
                return LazyOptional.empty();
            }
        };
    }

    @Override
    public boolean isItemValid(ItemStack container, int slot, @NotNull ItemStack stack) {
        return true;
    }
}
