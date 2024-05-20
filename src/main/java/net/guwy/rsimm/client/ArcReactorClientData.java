package net.guwy.rsimm.client;

import net.guwy.rsimm.content.items.arc_reactors.AbstractArcReactorItem;
import net.guwy.rsimm.content.items.arc_reactors.GenericArcReactorItem;
import net.guwy.sticky_foundations.utils.ItemTagUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArcReactorClientData {
    // You gotta pray these move in sync
    private static List<Integer> itemId = new ArrayList<Integer>();
    private static List<UUID> playerUUID = new ArrayList<UUID>();
    private static List<Double> energyPercentage = new ArrayList<Double>();


    public static void setReactorData(int itemId, UUID uuid, double energyPercentage){

        // Checks if the uuid is ever assigned and if not assigns it to the end of the list
        if(!ArcReactorClientData.playerUUID.contains(uuid)){
            // Generates new places for the itemId and uuid
            ArcReactorClientData.playerUUID.add(uuid);
            ArcReactorClientData.itemId.add(itemId);
            ArcReactorClientData.energyPercentage.add(energyPercentage);

        } else {
            // records the index of the uuid so the item id is assigned in the same index
            int index = ArcReactorClientData.playerUUID.indexOf(uuid);

            // assigns the itemId to the same index as uuid
            ArcReactorClientData.itemId.set(index, itemId);
            ArcReactorClientData.energyPercentage.set(index, energyPercentage);
        }


    }

    public static ItemStack getReactorItem(UUID uuid){
        if(ArcReactorClientData.playerUUID.contains(uuid)){

            // gets the itemId on the same index as the uuid and assigns it to a item
            int index = ArcReactorClientData.playerUUID.indexOf(uuid);

            AbstractArcReactorItem arcReactorItem = (AbstractArcReactorItem) Item.byId(ArcReactorClientData.itemId.get(index));
            double percentage = ArcReactorClientData.energyPercentage.get(index);

            ItemStack itemStack = new ItemStack(arcReactorItem);

            // If energy is below 10% roll a random chance that increases as the energy reaches 0%
            // then if it succeeds set the CustomModelData to 1 which will render the depleted reactor model if it exists
            if(Math.random() >= percentage * 10)
                ItemTagUtils.putInt(itemStack, "CustomModelData", 1);

            return itemStack;
        } else {
            return null;
        }
    }

    public static void removeArcReactorData(UUID uuid){
        if(ArcReactorClientData.playerUUID.contains(uuid)){
            // removes the uuid and index from the list so they won't show up anymore
            // used in the case of arc reactor being removed

            int index = ArcReactorClientData.playerUUID.indexOf(uuid);

            ArcReactorClientData.itemId.remove(index);
            ArcReactorClientData.playerUUID.remove(index);
            ArcReactorClientData.energyPercentage.remove(index);

        }
    }

    /**
     * Used to remove all data upon logging out of a save to prevent crashes
     *
     * DO NOT use unless necessary
     */
    public static void purgeArcReactorData(){
        ArcReactorClientData.itemId.clear();
        ArcReactorClientData.playerUUID.clear();
        ArcReactorClientData.energyPercentage.clear();
    }


}
