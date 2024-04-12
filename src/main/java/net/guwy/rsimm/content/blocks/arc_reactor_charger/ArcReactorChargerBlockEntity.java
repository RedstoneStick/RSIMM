package net.guwy.rsimm.content.blocks.arc_reactor_charger;

import net.guwy.rsimm.content.items.arc_reactors.AbstractArcReactorItem;
import net.guwy.rsimm.content.items.arc_reactors.AbstractUnchargedArcReactorItem;
import net.guwy.rsimm.content.network_packets.ArcReactorChargerClientSyncS2CPacket;
import net.guwy.rsimm.index.RsImmBlockEntities;
import net.guwy.rsimm.index.RsImmNetworking;
import net.guwy.rsimm.index.RsImmTags;
import net.guwy.rsimm.mechanics.capabilities.forge.ModBlockEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArcReactorChargerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.is(RsImmTags.Items.UNCHARGED_ARC_REACTORS);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    public ArcReactorChargerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(RsImmBlockEntities.ARC_REACTOR_CHARGER.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ArcReactorChargerBlockEntity.this.progress;
                    case 1 -> ArcReactorChargerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pValue) {
                    case 0 -> ArcReactorChargerBlockEntity.this.progress = pValue;
                    case 1 -> ArcReactorChargerBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public static void onUse(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        ArcReactorChargerBlockEntity blockEntity = (ArcReactorChargerBlockEntity) pLevel.getBlockEntity(pPos);
        RsImmNetworking.sendToClients(new ArcReactorChargerClientSyncS2CPacket(blockEntity.getEnergyStorage().getEnergyStored(), pPos));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.rsimm.armor_equipping_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ArcReactorChargerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        if(cap == ForgeCapabilities.ENERGY){
            if(side == Direction.DOWN)
            return lazyEnergyStorage.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyStorage = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", this.itemHandler.serializeNBT());
        pTag.putInt("energy", this.ENERGY_STORAGE.getEnergyStored());
        pTag.putInt("progress", this.progress);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        //this.ENERGY_STORAGE.deserializeNBT(pTag.getCompound("energy"));
        this.ENERGY_STORAGE.setEnergy(pTag.getInt("energy"));
        this.progress = pTag.getInt("progress");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i=0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static final DirectionProperty FACING = ArcReactorChargerBlock.FACING;
    public static final BooleanProperty ACTIVE = ArcReactorChargerBlock.ACTIVE;

    public static  void tick(Level level, BlockPos blockPos, BlockState state, ArcReactorChargerBlockEntity pEntity) {
        if(level.isClientSide()){
            return;
        }

        if(hasRecipe(pEntity)){
            ItemStack itemStack = pEntity.itemHandler.getStackInSlot(0);
            AbstractUnchargedArcReactorItem unchargedArcReactor = (AbstractUnchargedArcReactorItem) itemStack.getItem();
            double charge = unchargedArcReactor.getCharge(itemStack);
            double requiredCharge = unchargedArcReactor.getRequiredCharge();

            pEntity.progress = (int) Math.round((charge/requiredCharge) * 200);
            setChanged(level, blockPos, state);

            unchargedArcReactor.setCharge(itemStack,
                    unchargedArcReactor.getCharge(itemStack) + pEntity.ENERGY_STORAGE.extractEnergy(
                            Math.min(pEntity.ENERGY_STORAGE.getEnergyStored(), MAX_ENERGY_DRAIN), false));
            unchargedArcReactor.setCharge(itemStack,
                    unchargedArcReactor.getCharge(itemStack) - unchargedArcReactor.getChargeConsume());

            if(!pEntity.getBlockState().getValue(ArcReactorChargerBlock.ACTIVE)){
                level.setBlock(blockPos, state
                                .setValue(FACING, state.getValue(FACING))
                                .setValue(ACTIVE, true)
                        , 2);
            }

            if(pEntity.progress >= pEntity.maxProgress){
                craftItem(pEntity);
            }
        }   else {
            pEntity.resetProgress();
            setChanged(level,blockPos,state);
            state.setValue(ArcReactorChargerBlock.ACTIVE, false);

            if(pEntity.getBlockState().getValue(ArcReactorChargerBlock.ACTIVE)){
                level.setBlock(blockPos, state
                                .setValue(FACING, state.getValue(FACING))
                                .setValue(ACTIVE, false)
                        , 2);
            }
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(ArcReactorChargerBlockEntity pEntity) {
        if(hasRecipe(pEntity)){
            AbstractUnchargedArcReactorItem unchargedArcReactor = (AbstractUnchargedArcReactorItem) pEntity.itemHandler.getStackInSlot(0).getItem();
            AbstractArcReactorItem arcReactorItem = (AbstractArcReactorItem) unchargedArcReactor.getChargedItem();

            pEntity.itemHandler.extractItem(0, 1, false);

            ItemStack result = new ItemStack(unchargedArcReactor.getChargedItem(), 1);
            CompoundTag nbtTag = new CompoundTag();
            nbtTag.putLong("energy", arcReactorItem.maxEnergy());
            result.setTag(nbtTag);
            pEntity.itemHandler.setStackInSlot(0, result);

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(ArcReactorChargerBlockEntity pEntity) {
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for(int i=0; i < pEntity.itemHandler.getSlots(); i++){
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        boolean hasCorrectItemInSlot = pEntity.itemHandler.getStackInSlot(0).is(RsImmTags.Items.UNCHARGED_ARC_REACTORS);

        return hasCorrectItemInSlot && inventory.getItem(0).getCount() == 1;
    }



    private final ModBlockEnergyStorage ENERGY_STORAGE = new ModBlockEnergyStorage(50000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            RsImmNetworking.sendToClients(new ArcReactorChargerClientSyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();
    private static final int MAX_ENERGY_DRAIN = 50000;

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }
}
