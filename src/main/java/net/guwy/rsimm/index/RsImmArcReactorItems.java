package net.guwy.rsimm.index;

import net.guwy.rsimm.RsImm;
import net.guwy.rsimm.content.items.arc_reactors.GenericArcReactorItem;
import net.guwy.rsimm.content.items.arc_reactors.UnchargedArcReactorItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RsImmArcReactorItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RsImm.MOD_ID);



    // Arc Reactors
    public static final RegistryObject<Item> MARK_1_ARC_REACTOR = ITEMS.register("mark_1_arc_reactor",
            () -> new GenericArcReactorItem(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN),
                    "mark 1", 1000000, 100000, 5, 4, new ResourceLocation("item.rsimm.mark_1_arc_reactor_depleted"),
                    new ResourceLocation(RsImm.MOD_ID, "textures/overlay/armor/edith_glasses/mk1_overlay_sprites.png")));

    public static final RegistryObject<Item> MARK_1_ARC_REACTOR_UNCHARGED = ITEMS.register("mark_1_arc_reactor_uncharged",
            () -> new UnchargedArcReactorItem(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN),
                    MARK_1_ARC_REACTOR.get(), 10000000, 200));



    public static final RegistryObject<Item> MARK_2_ARC_REACTOR = ITEMS.register("mark_2_arc_reactor",
            () -> new GenericArcReactorItem(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN),
                    "mark 2", 10000000, 1000000, 25, 8, new ResourceLocation("item.rsimm.mark_2_arc_reactor_depleted"),
                    new ResourceLocation(RsImm.MOD_ID, "textures/overlay/armor/edith_glasses/edith_glasses_overlay.png")));

    public static final RegistryObject<Item> MARK_2_ARC_REACTOR_UNCHARGED = ITEMS.register("mark_2_arc_reactor_uncharged",
            () -> new UnchargedArcReactorItem(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN),
                    MARK_2_ARC_REACTOR.get(), 50000000, 1000));



    // Non Fission Reactors
    public static final RegistryObject<Item> BATTERY_REACTOR = ITEMS.register("battery_reactor",
            () -> new GenericArcReactorItem(new Item.Properties().stacksTo(1).tab(RsImmCreativeModeTabs.MAIN),
                    "AA Battery", 6000, 5, 1, 0, null,
                    new ResourceLocation(RsImm.MOD_ID, "textures/overlay/armor/edith_glasses/battery_overlay_sprites.png")){
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    pTooltipComponents.add(Component.translatable("tooltip.rsimm.battery_reactor"));
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });




    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
