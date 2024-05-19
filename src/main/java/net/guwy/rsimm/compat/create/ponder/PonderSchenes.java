package net.guwy.rsimm.compat.create.ponder;

//import com.simibubi.create.foundation.ponder.SceneBuilder;
//import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class PonderSchenes {
    //public static void arcReactorCharger1(SceneBuilder scene, SceneBuildingUtil util) {
    //    scene.title("arc_reactor_charger", "Using Arc Reactor Charging to Charge Reactors");
    //    scene.configureBasePlate(0, 0, 3);
    //    scene.showBasePlate();
    //    scene.world.showSection(util.select.layersFrom(1), Direction.DOWN);
    //    BlockPos charger = util.grid.at(1, 1, 1);
    //    for (int i = 0; i < 3; i++) {
    //        scene.idle(5);
    //        scene.world.showSection(util.select.position(1 + i, 1, 2), Direction.DOWN);
    //    }
    //    scene.idle(10);
    //    scene.overlay.showText(80)
    //            .text("Arc Reactor Charger Can be used to charge Arc Reactors")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(80);
    //    scene.rotateCameraY(90);
    //    scene.idle(20);
    //    scene.addKeyframe();
    //    scene.overlay.showText(70)
    //            .text("It does this by consuming forge energy (fe)")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(70);
    //    scene.markAsFinished();
    //}
//
    //public static void arcReactorCharger2(SceneBuilder scene, SceneBuildingUtil util) {
    //    scene.title("arc_reactor_charger_2", "Forge Energy");
    //    scene.configureBasePlate(0, 0, 3);
    //    scene.showBasePlate();
    //    scene.world.showSection(util.select.layersFrom(1), Direction.DOWN);
    //    BlockPos charger = util.grid.at(1, 1, 1);
    //    for (int i = 0; i < 3; i++) {
    //        scene.idle(5);
    //        scene.world.showSection(util.select.position(1 + i, 1, 2), Direction.DOWN);
    //    }
    //    scene.idle(10);
    //    scene.overlay.showText(80)
    //            .text("1")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(80);
    //    scene.rotateCameraY(90);
    //    scene.idle(20);
    //    scene.addKeyframe();
    //    scene.overlay.showText(80)
    //            .text("2")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(80);
    //    scene.idle(20);
    //    scene.addKeyframe();
    //    scene.overlay.showText(80)
    //            .text("3")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(80);
    //    scene.markAsFinished();
    //}
//
    //public static void arcReactorCharger3(SceneBuilder scene, SceneBuildingUtil util) {
    //    scene.title("arc_reactor_charger_3", "Where to connect power");
    //    scene.configureBasePlate(0, 0, 3);
    //    scene.showBasePlate();
    //    scene.world.showSection(util.select.layersFrom(1), Direction.DOWN);
    //    BlockPos charger = util.grid.at(1, 1, 1);
    //    Vec3 terracotta = util.vector.of(1, 1.2, 1);
    //    for (int i = 0; i < 3; i++) {
    //        scene.idle(5);
    //        scene.world.showSection(util.select.position(1 + i, 1, 2), Direction.DOWN);
    //    }
    //    scene.idle(10);
    //    scene.overlay.showText(80)
    //            .text("1")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(80);
    //    scene.rotateCameraY(90);
    //    scene.idle(20);
    //    scene.addKeyframe();
    //    scene.overlay.showText(80)
    //            .text("2")
    //            .placeNearTarget()
    //            .pointAt(terracotta);
    //    scene.idle(80);
    //    scene.idle(20);
    //    scene.addKeyframe();
    //    scene.overlay.showText(80)
    //            .text("3")
    //            .placeNearTarget()
    //            .pointAt(terracotta);
    //    scene.idle(80);
    //    scene.idle(20);
    //    scene.addKeyframe();
    //    scene.overlay.showText(80)
    //            .text("1")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(80);
    //    scene.markAsFinished();
    //}
//
    //public static void arcReactorCharger4(SceneBuilder scene, SceneBuildingUtil util) {
    //    scene.title("arc_reactor_charger_4", "Where to connect power");
    //    scene.configureBasePlate(0, 0, 3);
    //    scene.showBasePlate();
    //    scene.world.showSection(util.select.layersFrom(1), Direction.DOWN);
    //    BlockPos charger = util.grid.at(1, 1, 1);
    //    Vec3 terracotta = util.vector.of(1, 0.3, 1);
    //    for (int i = 0; i < 3; i++) {
    //        scene.idle(5);
    //        scene.world.showSection(util.select.position(1 + i, 1, 2), Direction.DOWN);
    //    }
    //    scene.idle(10);
    //    scene.overlay.showText(80)
    //            .text("1")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(80);
    //    scene.rotateCameraY(90);
    //    scene.idle(20);
    //    scene.addKeyframe();
    //    scene.overlay.showText(80)
    //            .text("2")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(80);
    //    scene.idle(20);
    //    scene.addKeyframe();
    //    scene.overlay.showText(80)
    //            .text("3")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(80);
    //    scene.idle(20);
    //    scene.addKeyframe();
    //    scene.overlay.showText(80)
    //            .text("1")
    //            .placeNearTarget()
    //            .pointAt(util.vector.topOf(charger));
    //    scene.idle(80);
    //    scene.markAsFinished();
    //}
//
    //public static void template(SceneBuilder scene, SceneBuildingUtil util) {
    //    scene.title("arc_reactor_charger", "This is a template");
    //    scene.showBasePlate();
    //    scene.idle(10);
    //    scene.world.showSection(util.select.layersFrom(1), Direction.DOWN);
    //}
}
