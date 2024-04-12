package net.guwy.rsimm.content.items;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.compat.curios.Curios;
import net.guwy.rsimm.content.entities.armor.misc.EdithGlassesItemRenderer;
import net.guwy.rsimm.content.items.arc_reactors.AbstractArcReactorItem;
import net.guwy.rsimm.index.RsImmCapabilities;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class EdithGlassesArmorItem extends GeoArmorItem implements IAnimatable {
    public static final String LIT_TAG_KEY = "lit";
    public static final String HAS_SLOT_TAG_KEY = "has_arc_reactor_slot";
    public static final String HAS_REACTOR_TAG_KEY = "has_arc_reactor";
    public static final String ENERGY_TAG_KEY = "reactor_energy";
    public static final String ENERGY_CAPACITY_TAG_KEY = "reactor_capacity";
    public static final String LOAD_TAG_KEY = "reactor_load";
    public static final String MAX_OUTPUT_TAG_KEY = "reactor_max_load";
    public static final String REACTOR_ICON_TAG_KEY = "reactor_icon";

    public EdithGlassesArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);

        if(!level.isClientSide){
            // Save arc reactor data as ntb to use for client sync
            player.getCapability(RsImmCapabilities.Player.ARC_REACTOR).ifPresent(arc_reactor -> {
                // If has a hole and has a reactor
                ItemTagUtils.putBoolean(stack, HAS_SLOT_TAG_KEY, arc_reactor.hasArcReactorSlot());
                ItemTagUtils.putBoolean(stack, HAS_REACTOR_TAG_KEY, arc_reactor.hasArcReactor());

                // Energy, max energy, load and max output
                ItemTagUtils.putLong(stack, ENERGY_TAG_KEY, arc_reactor.getArcReactorEnergy());
                ItemTagUtils.putLong(stack, ENERGY_CAPACITY_TAG_KEY, arc_reactor.getArcReactorEnergyCapacity());
                ItemTagUtils.putLong(stack, LOAD_TAG_KEY, arc_reactor.getEnergyLastLoad());
                ItemTagUtils.putLong(stack, MAX_OUTPUT_TAG_KEY, arc_reactor.getArcReactorEnergyOutput());

                // Reactor Icon
                String iconTexture = "";
                if(Item.byId(arc_reactor.getArcReactorTypeId()) instanceof AbstractArcReactorItem arcReactorItem){
                    if(null != arcReactorItem.OverlayIcon())
                        iconTexture = String.valueOf(arcReactorItem.OverlayIcon());
                }
                ItemTagUtils.putString(stack, REACTOR_ICON_TAG_KEY, iconTexture);
            });
        }
    }

    /**
     * Animation Controllers
     */
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    // Empty since there are no animations
    @Override
    public void registerControllers(AnimationData data) {}
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);

        consumer.accept(new IClientItemExtensions() {

            // Add item rendering
            private EdithGlassesItemRenderer renderer = null;
            // Don't instantiate until ready. This prevents race conditions breaking things
            @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new EdithGlassesItemRenderer();
//
                return renderer;
            }

            // Add armor rendering (this one copied straight from the GeoArmorItem class
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                return (HumanoidModel<?>) GeoArmorRenderer.getRenderer(EdithGlassesArmorItem.class, livingEntity)
                        .applyEntityStats(original).setCurrentItem(livingEntity, itemStack, equipmentSlot)
                        .applySlot(equipmentSlot);
            }
        });
    }

    /**
     * Curio Stuff
     */
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if(RsImm.isCuriosLoaded()){
            return Curios.createEdithGlassesProvider(stack);
        } else {
            return super.initCapabilities(stack, nbt);
        }
    }
}
