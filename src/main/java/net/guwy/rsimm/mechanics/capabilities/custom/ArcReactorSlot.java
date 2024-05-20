package net.guwy.rsimm.mechanics.capabilities.custom;

import net.guwy.rsimm.config.RsImmServerConfigs;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.rsimm.index.RsImmSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicReference;

public class ArcReactorSlot {

    private boolean hasSlot = false;
    private ItemStack arcReactorStack = ItemStack.EMPTY;

    public ArcReactorSlot() {
    }

    /** Used for deleting the arc reactor data from the player,
     * use {@link ArcReactorSlot#removeArcReactor(Player)}
     * for removing the arc reactor from a player */
    private void deleteArcReactor(){
        this.arcReactorStack = ItemStack.EMPTY;
    }


    /**
     * Used for removing the arc reactor from a player
     *
     * @param player the player to extract the arc reactor
     */
    public static void removeArcReactor(Player player){
        removeArcReactor(player, true, true, true);
    }

    /** More advanced version of {@link ArcReactorSlot#removeArcReactor(Player)}
     * use it for custom extractions
     * @param player the player to extract the arc reactor
     * @param playSound plays sound upon success
     * @param sendFailMessage sends fail message if the chest is occupied
     * @param giveItem gives the extracted reactor to the player
     * @return the extracted arc reactor for custom removal,
     * */
    public static ItemStack removeArcReactor(Player player, boolean playSound, boolean sendFailMessage, boolean giveItem){
        AtomicReference<ItemStack> toReturn = new AtomicReference<>(ItemStack.EMPTY);
        player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arcReactor -> {

            if(arcReactor.getArcReactorStack() != ItemStack.EMPTY){
                if(player.getItemBySlot(EquipmentSlot.CHEST).isEmpty() || !RsImmServerConfigs.ARC_REACTOR_EXTRACT_INSERT_LIMITS.get()){

                    ItemStack itemStack = arcReactor.getArcReactorStack();

                    // Place the arc reactor in inventory
                    if(giveItem) player.getInventory().placeItemBackInInventory(itemStack);

                    arcReactor.deleteArcReactor();

                    if(playSound){
                        // Arc Reactor Unequip Sound
                        player.getLevel().playSound(null, player, RsImmSounds.ARC_REACTOR_UNEQUIP.get(), SoundSource.PLAYERS, 1, 1);
                    }

                    // Set ItemStack to return
                    toReturn.set(itemStack);
                }
                else if (sendFailMessage) player.sendSystemMessage(Component.translatable("arc_reactor.rsimm.chest_blocked"));
            }
        });

        return toReturn.get();
    }



    /// The part for accessing and setting variables ///
    // hasSlot
    public boolean hasArcReactorSlot() {
        return this.hasSlot;
    }
    public void setHasArcReactorSlot(boolean bool) {
        this.hasSlot = bool;
    }

    // reactorStack
    public ItemStack getArcReactorStack() {
        return this.arcReactorStack;
    }
    public void setArcReactorStack(ItemStack arcReactorStack) {
        this.arcReactorStack = arcReactorStack.copy();
    }



    /// The part after this point handles the saving and loading of data ///
    public void copyFrom(ArcReactorSlot source){
        this.hasSlot = source.hasSlot;
        this.arcReactorStack = source.arcReactorStack;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putBoolean("hasArcReactorSlot", hasSlot);
        nbt.put("arcReactorStack", arcReactorStack.save(new CompoundTag()));
    }

    public void loadNBTData(CompoundTag nbt){
        hasSlot = nbt.getBoolean("hasArcReactorSlot");
        arcReactorStack = ItemStack.of(nbt.getCompound("arcReactorStack"));
    }
}
