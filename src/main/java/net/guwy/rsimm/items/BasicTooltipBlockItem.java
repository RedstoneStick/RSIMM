package net.guwy.rsimm.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class BasicTooltipBlockItem extends BlockItem {
    Component[] tooltip;

    public BasicTooltipBlockItem(Block block, Properties properties, Component... tooltip) {
        super(block, properties);
        this.tooltip = tooltip;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.addAll(List.of(tooltip));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
