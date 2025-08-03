package net.guwy.rsimm.items.armor;

import net.guwy.rsimm.index.IMArmorParts;
import net.guwy.rsimm.index.ModDataComponents;
import net.guwy.rsimm.items.armor.parts.AbstractArmorPart;
import net.guwy.rsimm.items.armor.parts.ArmorPartContainerContents;
import net.guwy.rsimm.items.armor.parts.EBootPriority;
import net.guwy.rsimm.items.armor.parts.EPartSlots;
import net.guwy.rsimm.items.armor.parts.chassis.AbstractChassisArmorPart;
import net.guwy.rsimm.keybinds.EKeyActionTypes;
import net.guwy.rsimm.keybinds.EKeyBinds;
import net.guwy.rsimm.keybinds.IModKeybindCapableArmor;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;


public class GenericIronManArmorItem extends ArmorItem implements IModKeybindCapableArmor {
    private float weight;
    private boolean fullSetEquipped;

    public GenericIronManArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties.stacksTo(1)
                .component(ModDataComponents.ARMOR_PARTS, ArmorPartContainerContents.EMPTY));
    }



    /**
     * Only the chestplate will handle armor ticks
     */
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (((Player) entity).getItemBySlot(EquipmentSlot.CHEST) == stack) {

            // PREQUISITES
            checkFullSetEquipped((LivingEntity) entity);

            List<ItemStack> arr = getPartStackList(stack);
            Supplier<List<ItemStack>> partItemStacks = () -> arr;

            // SERVER SPECIFIC
            if (!level.isClientSide) {
                calculateWeight(partItemStacks);
            }

            // CLIENT SPECIFIC
            else {
                Vec3 moveVec = entity.getDeltaMovement().multiply(1, 0, 1);
                //entity.sendSystemMessage(Component.literal("Speed: " + moveVec.length() * 20));
            }

            // COMMON
            if (partItemStacks.get().get(EPartSlots.CHASSIS.getSlot()).getItem() instanceof AbstractChassisArmorPart chasisPart) {
                HashMap<EBootPriority, Boolean> bootLevels = new HashMap<>();
                bootLevels.put(EBootPriority.LOCOMOTION, true);
                chasisPart.partTick(() -> entity, partItemStacks, stack, bootLevels);
            }

        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public boolean keybindArmorInput(Player player, ItemStack armorStack, EKeyActionTypes keyActionType, EKeyBinds keyBind, EquipmentSlot equipmentSlot) {
        return false;
    }



    @Override
    public InteractionResult useOn(UseOnContext context) {
        context.getItemInHand().set(ModDataComponents.ARMOR_PARTS, ArmorPartContainerContents.fromItems(List.of(new ItemStack(IMArmorParts.TEST_ARMOR_CHASSIS.get()), new ItemStack(Items.ACACIA_FENCE), ItemStack.EMPTY, new ItemStack(Blocks.COBBLESTONE))));
        //ArmorPartContainerContents armorPartStorage = context.getItemInHand().get(ModDataComponents.ARMOR_PARTS);
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ArmorPartContainerContents armorPartStorage = stack.get(ModDataComponents.ARMOR_PARTS);

        if (armorPartStorage != null) {

            for (int i = 0; i < armorPartStorage.getSlots(); i++) {
                if (armorPartStorage.getStackInSlot(i) != ItemStack.EMPTY) {
                    tooltipComponents.add(Component.literal(
                            Component.translatable(EPartSlots.getTypeFromSlotNumber(i).getLocalization()).getString()
                                    + ": " + Component.translatable(armorPartStorage.getStackInSlot(i).getDescriptionId()).getString()).withStyle(ChatFormatting.GRAY));
                }
            }
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }



    /// Functions

    private NonNullList<ItemStack> getPartStackList(ItemStack armorStack) {

        ArmorPartContainerContents armorPartStorage = armorStack.get(ModDataComponents.ARMOR_PARTS);
        NonNullList<ItemStack> list = NonNullList.withSize(EPartSlots.LAST_SLOT + 1, ItemStack.EMPTY);

        armorPartStorage.copyInto(list);

        return list;
    }

    public float armorWeight() {
        return this.weight;
    }

    private void calculateWeight(Supplier<List<ItemStack>> partItemStacks) {
        this.weight = 0;

        for (ItemStack stack : partItemStacks.get()) {
            if (!stack.isEmpty() && stack.getItem() instanceof AbstractArmorPart armorPart) {
                this.weight += armorPart.getWeight();
            }
        }
    }

    public boolean isFullSetEqupped() {
        return this.fullSetEquipped;
    }

    private void checkFullSetEquipped(LivingEntity entity) {
        boolean fullSet = true;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR && !(entity.getItemBySlot(slot).getItem() instanceof GenericIronManArmorItem)) {
                fullSet = false;
            }
        }
        this.fullSetEquipped = fullSet;
    }
}
