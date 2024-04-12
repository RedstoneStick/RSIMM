package net.guwy.rsimm.events.client_events;

import net.guwy.rsimm.content.entities.armor.Mark1ArmorRenderer;
import net.guwy.rsimm.content.entities.armor.Mark1OpenHelmetArmorRenderer;
import net.guwy.rsimm.content.entities.armor.misc.ArcReactorConnectorRenderer;
import net.guwy.rsimm.content.entities.armor.misc.EdithGlassesArmorRenderer;
import net.guwy.rsimm.content.entities.armor.misc.TestArmorRenderer;
import net.guwy.rsimm.content.items.ArcReactorConnectorArmorItem;
import net.guwy.rsimm.content.items.EdithGlassesArmorItem;
import net.guwy.rsimm.content.items.TestArmorItem;
import net.guwy.rsimm.content.items.armors.old.Mark1ArmorItem;
import net.guwy.rsimm.content.items.armors.old.Mark1OpenHelmetArmorItem;
import net.minecraftforge.client.event.EntityRenderersEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class RegisterArmorRenderersEventHandler {
    public static void init(final EntityRenderersEvent.AddLayers event){
        GeoArmorRenderer.registerArmorRenderer(TestArmorItem.class, TestArmorRenderer::new);

        GeoArmorRenderer.registerArmorRenderer(Mark1ArmorItem.class, Mark1ArmorRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(Mark1OpenHelmetArmorItem.class, Mark1OpenHelmetArmorRenderer::new);

        GeoArmorRenderer.registerArmorRenderer(ArcReactorConnectorArmorItem.class, ArcReactorConnectorRenderer::new);


        GeoArmorRenderer.registerArmorRenderer(EdithGlassesArmorItem.class, EdithGlassesArmorRenderer::new);

    }
}
