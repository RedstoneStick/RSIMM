package net.guwy.rsimm.content.blocks.armor_equipping_station;

import net.guwy.rsimm.content.items.ammo_kits.AbstractAmmoKit;
import net.guwy.rsimm.content.items.armors.old.AbstractIronmanArmorItem;
import net.guwy.rsimm.index.RsImmBlockEntities;
import net.guwy.rsimm.index.RsImmTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArmorEquippingStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(5){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if(slot == 0){
                return stack.is(RsImmTags.Items.UNASSEMBLED_IRONMAN_HELMETS);
            }
            if(slot == 1){
                return stack.is(RsImmTags.Items.UNASSEMBLED_IRONMAN_CHESTPLATES);
            }
            if(slot == 2){
                return stack.is(RsImmTags.Items.UNASSEMBLED_IRONMAN_LEGGINGS);
            }
            if(slot == 3){
                return stack.is(RsImmTags.Items.UNASSEMBLED_IRONMAN_BOOTS);
            }
            if(slot == 4){
                return stack.is(RsImmTags.Items.AMMO_KITS);
            }
            return super.isItemValid(slot, stack);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    public ArmorEquippingStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(RsImmBlockEntities.ARMOR_EQUIPPING_STATION.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ArmorEquippingStationBlockEntity.this.progress;
                    case 1 -> ArmorEquippingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pValue) {
                    case 0 -> ArmorEquippingStationBlockEntity.this.progress = pValue;
                    case 1 -> ArmorEquippingStationBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.rsimm.armor_equipping_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ArmorEquippingStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("progress", this.progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("progress");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i=0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static  void tick(Level level, BlockPos blockPos, BlockState state, ArmorEquippingStationBlockEntity pEntity) {
        if(level.isClientSide()){
            return;
        }



        if(hasRecipe(pEntity)){

            pEntity.progress = 1;
            setChanged(level, blockPos, state);

        }   else {
            pEntity.resetProgress();
            setChanged(level,blockPos,state);

        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void assembleArmorOnPlayer(ArmorEquippingStationBlockEntity pEntity, Player player) {
        if(hasRecipe(pEntity) && hasArmorSlotsFree(player)){
            if(!player.getLevel().isClientSide) {
                if(hasArmorSlotsFree(player)){
                    ItemStack supplierItem = pEntity.itemHandler.getStackInSlot(4);
                    AbstractAmmoKit ammoKit = (AbstractAmmoKit) supplierItem.getItem();

                    AbstractIronmanArmorItem armorItem = (AbstractIronmanArmorItem) ammoKit.ChestplateItem();

                    if(supplierItem.getTag() != null){
                        addArmor(player, pEntity);
                        //player.getCapability(IronmanArmorDataProvider.PLAYER_IRONMAN_ARMOR_DATA).ifPresent(armorData -> {
                        //    CompoundTag nbtTag = supplierItem.getTag();
//
                        //    armorData.compileArmor(nbtTag.getLong("energy"), armorItem.MaxStableEnergy(), ArmorEnergyType.EMERGENCY, armorItem.MaxEnergyOutput()
                        //            , armorItem.FlightOverSpeedThreshold(), armorItem.FlightStallSpeed());
//
                        //    armorData.setArmorStorage(1, nbtTag.getInt("1"));
                        //    armorData.setArmorStorage(2, nbtTag.getInt("2"));
                        //    armorData.setArmorStorage(3, nbtTag.getInt("3"));
                        //    armorData.setArmorStorage(4, nbtTag.getInt("4"));
                        //    armorData.setArmorStorage(5, nbtTag.getInt("5"));
                        //    armorData.setArmorStorage(6, nbtTag.getInt("6"));
                        //    armorData.setArmorStorage(7, nbtTag.getInt("7"));
                        //    armorData.setArmorStorage(8, nbtTag.getInt("8"));
                        //    armorData.setArmorStorage(9, nbtTag.getInt("9"));
                        //    armorData.setArmorStorage(10, nbtTag.getInt("10"));
                        //});

                        pEntity.itemHandler.extractItem(0, 1, false);
                        pEntity.itemHandler.extractItem(1, 1, false);
                        pEntity.itemHandler.extractItem(2, 1, false);
                        pEntity.itemHandler.extractItem(3, 1, false);
                        pEntity.itemHandler.extractItem(4, 1, false);

                        // Sounds
                        player.getLevel().playSound(null, player, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1, 1);
                    }
                }
            }

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(ArmorEquippingStationBlockEntity pEntity) {
        boolean hasFullUnassambledSetPresent = false;

        if(pEntity.itemHandler.getStackInSlot(4).getItem() instanceof AbstractAmmoKit){
            AbstractAmmoKit ammoKit = (AbstractAmmoKit) pEntity.itemHandler.getStackInSlot(4).getItem();
            Item helmet = pEntity.itemHandler.getStackInSlot(0).getItem();
            Item chestplate = pEntity.itemHandler.getStackInSlot(1).getItem();
            Item leggings = pEntity.itemHandler.getStackInSlot(2).getItem();
            Item boots = pEntity.itemHandler.getStackInSlot(3).getItem();

            hasFullUnassambledSetPresent = helmet.equals(ammoKit.UnassembledHelmetItem())
                    && chestplate.equals(ammoKit.UnassembledChestplateItem())
                    && leggings.equals(ammoKit.UnassembledLeggingsItem())
                    && boots.equals(ammoKit.UnassembledBootsItem());
        }

        return hasFullUnassambledSetPresent;
    }

    private static boolean hasArmorSlotsFree(Player player){
        return player.getItemBySlot(EquipmentSlot.HEAD).isEmpty() &&
                player.getItemBySlot(EquipmentSlot.CHEST).isEmpty() &&
                player.getItemBySlot(EquipmentSlot.LEGS).isEmpty() &&
                player.getItemBySlot(EquipmentSlot.FEET).isEmpty();
    }

    private static void addArmor(Player pPlayer, ArmorEquippingStationBlockEntity pEntity){
        if(pEntity.itemHandler.getStackInSlot(4).getItem() instanceof AbstractAmmoKit){
            AbstractAmmoKit ammoKit = (AbstractAmmoKit) pEntity.itemHandler.getStackInSlot(4).getItem();
            ItemStack itemStack;

            itemStack = new ItemStack(ammoKit.HelmetItem());
            itemStack.setDamageValue(pEntity.itemHandler.getStackInSlot(0).getDamageValue());
            pPlayer.setItemSlot(EquipmentSlot.HEAD, itemStack);

            itemStack = new ItemStack(ammoKit.ChestplateItem());
            itemStack.setDamageValue(pEntity.itemHandler.getStackInSlot(1).getDamageValue());
            pPlayer.setItemSlot(EquipmentSlot.CHEST, itemStack);

            itemStack = new ItemStack(ammoKit.LeggingsItem());
            itemStack.setDamageValue(pEntity.itemHandler.getStackInSlot(2).getDamageValue());
            pPlayer.setItemSlot(EquipmentSlot.LEGS, itemStack);

            itemStack = new ItemStack(ammoKit.BootsItem());
            itemStack.setDamageValue(pEntity.itemHandler.getStackInSlot(3).getDamageValue());
            pPlayer.setItemSlot(EquipmentSlot.FEET, itemStack);
        }
    }

    public static void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity){
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if(pEntity.isCrouching()){
            assembleArmorOnPlayer((ArmorEquippingStationBlockEntity) blockEntity, (Player) pEntity);
            setChanged(pLevel, pPos, pState);
        }
    }
}
