package net.backslot.client.feature;

import chronosacaria.mcdw.weapons.SoulDaggers;
import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.medievalweapons.item.Long_Sword_Item;
import net.medievalweapons.item.Small_Axe_Item;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;

@Environment(EnvType.CLIENT)
public class BeltSlotFeatureRenderer
    extends HeldItemFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

  public BeltSlotFeatureRenderer(
      FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
    super(featureRendererContext);
  }

  @Override
  public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i,
      AbstractClientPlayerEntity livingEntity, float f, float g, float h, float j, float k, float l) {
    PlayerEntity player = (PlayerEntity) livingEntity;
    ItemStack beltSlotStack = player.inventory.getStack(42);
    if (livingEntity instanceof AbstractClientPlayerEntity && !beltSlotStack.isEmpty()) {
      matrixStack.push();
      ModelPart modelPart = this.getContextModel().torso;
      modelPart.rotate(matrixStack);
      double switchBeltSide = 0.29D;
      if (BackSlotMain.CONFIG.switch_beltslot_side) {
        switchBeltSide = -0.29D;
      }
      matrixStack.translate(switchBeltSide, 0.5D, 0.05D);
      if (beltSlotStack.getItem() instanceof FlintAndSteelItem) {
        matrixStack.translate(0.01F, 0.0F, -0.1F);
      }
      matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
      float downScaling = 0.0F;
      FabricLoader loader = FabricLoader.getInstance();
      if (loader.isModLoaded("medievalweapons")) {
        if (beltSlotStack.getItem() instanceof Small_Axe_Item) {
          downScaling = -0.5F;
        }
        if (beltSlotStack.getItem() instanceof Long_Sword_Item) {
          downScaling = -0.3F;
        }
      }
      if (loader.isModLoaded("mcdw") && (beltSlotStack.getItem() == SoulDaggers.DAGGER_ETERNAL_KNIFE
          || beltSlotStack.getItem() == SoulDaggers.SWORD_TRUTHSEEKER)) {
        downScaling = -0.3F;
      }
      matrixStack.scale(BackSlotMain.CONFIG.beltslot_scale + downScaling,
          BackSlotMain.CONFIG.beltslot_scale + downScaling, BackSlotMain.CONFIG.beltslot_scale + downScaling);
      if (beltSlotStack.getItem() instanceof ShearsItem || beltSlotStack.getItem() instanceof FlintAndSteelItem) {
        matrixStack.scale(0.65F, 0.65F, 0.65F);
        if (!livingEntity.hasStackEquipped(EquipmentSlot.CHEST)) {
          matrixStack.translate(0.0F, 0.0F, 0.015F);
        }
      }
      MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, beltSlotStack,
          ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i);
      matrixStack.pop();
    }
  }

}