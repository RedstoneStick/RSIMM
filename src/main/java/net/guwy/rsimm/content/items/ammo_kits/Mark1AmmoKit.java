package net.guwy.rsimm.content.items.ammo_kits;

import net.guwy.rsimm.index.RsImmArmorItems;
import net.guwy.rsimm.index.RsImmItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class Mark1AmmoKit extends AbstractAmmoKit{
    public Mark1AmmoKit(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Item storage1() {
        return Blocks.MAGMA_BLOCK.asItem();
    }
    @Override
    public Integer storage1Capacity() {
        return 10000;
    }

    @Override
    public Item storage2() {
        return RsImmItems.ROCKET.get();
    }
    @Override
    public Integer storage2Capacity() {
        return 1;
    }

    @Override
    public CompoundTag storageInsertion(CompoundTag nbtTag, Player pPlayer, String nbtKey, Item storage, int storageCapacity) {
        if(storage.equals(storage1())){
            if(nbtTag.getInt(nbtKey) < storageCapacity){
                for(int i = 0; i < pPlayer.getInventory().getContainerSize(); i++){
                    if(nbtTag.getInt(nbtKey) < storageCapacity){
                        ItemStack inventoryItem = pPlayer.getInventory().getItem(i);
                        if(inventoryItem.getItem() == storage){
                            nbtTag.putInt(nbtKey, nbtTag.getInt(nbtKey) + 100);
                            if(!inventoryItem.hurt(1, RandomSource.create(), (ServerPlayer) pPlayer)){
                                inventoryItem.setCount(inventoryItem.getCount() - 1);
                            }

                            // Fake player for sounds
                            Player soundPlayer = new Player(pPlayer.getLevel(), pPlayer.getOnPos(), 0, pPlayer.getGameProfile(), null) {
                                @Override
                                public boolean isSpectator() {
                                    return false;
                                }

                                @Override
                                public boolean isCreative() {
                                    return false;
                                }
                            };
                            soundPlayer.playSound(SoundEvents.ITEM_PICKUP, 1, 0.7f);
                        }
                    }
                }
            }
            return nbtTag;
        }else {
            return super.storageInsertion(nbtTag, pPlayer, nbtKey, storage, storageCapacity);
        }
    }

    @Override
    public CompoundTag storageExtraction(CompoundTag nbtTag, Player pPlayer, String nbtKey, Item itemToGive) {
        if(itemToGive.equals(storage1())){
            if(nbtTag.getInt(nbtKey) > 0){
                pPlayer.addItem(new ItemStack(itemToGive));
                nbtTag.putInt(nbtKey, nbtTag.getInt(nbtKey) - 100);

                // Fake player for sounds
                Player soundPlayer = new Player(pPlayer.getLevel(), pPlayer.getOnPos(), 0, pPlayer.getGameProfile(), null) {
                    @Override
                    public boolean isSpectator() {
                        return false;
                    }

                    @Override
                    public boolean isCreative() {
                        return false;
                    }
                };
                soundPlayer.playSound(SoundEvents.ITEM_PICKUP, 1, 1.4f);
            }
            return nbtTag;
        }else {

            return super.storageExtraction(nbtTag, pPlayer, nbtKey, itemToGive);
        }
    }

    @Override
    public Item UnassembledHelmetItem() {
        return Items.BARRIER;
    }

    @Override
    public Item UnassembledChestplateItem() {
        return Items.BARRIER;
    }

    @Override
    public Item UnassembledLeggingsItem() {
        return Items.BARRIER;
    }

    @Override
    public Item UnassembledBootsItem() {
        return Items.BARRIER;
    }

    @Override
    public Item HelmetItem() {
        return Items.BARRIER;
    }

    @Override
    public Item ChestplateItem() {
        return Items.BARRIER;
    }

    @Override
    public Item LeggingsItem() {
        return Items.BARRIER;
    }

    @Override
    public Item BootsItem() {
        return Items.BARRIER;
    }
}
