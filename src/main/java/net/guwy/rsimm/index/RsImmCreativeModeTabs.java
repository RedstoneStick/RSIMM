package net.guwy.rsimm.index;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class RsImmCreativeModeTabs {
    public static final CreativeModeTab MAIN = new CreativeModeTab("rsimm_tab_main") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RsImmItems.CHEST_CUTTER.get());
        }
    };
    public static final CreativeModeTab ARC_REACTORS = new CreativeModeTab("rsimm_tab_arc_reactors") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RsImmArcReactorItems.MARK_1_ARC_REACTOR.get());
        }
    };
    public static final CreativeModeTab SUIT_COMPONENTS = new CreativeModeTab("rsimm_tab_suit_components") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RsImmArmorParts.REPULSOR.get());
        }
    };


}
