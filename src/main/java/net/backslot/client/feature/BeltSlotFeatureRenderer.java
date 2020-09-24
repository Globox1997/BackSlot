package net.backslot.client.feature;

import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
      matrixStack.translate(0.29D, 0.5D, 0.05D);
      matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
      matrixStack.scale(BackSlotMain.CONFIG.beltslot_scale, BackSlotMain.CONFIG.beltslot_scale,
          BackSlotMain.CONFIG.beltslot_scale);
      if (beltSlotStack.getItem() instanceof ShearsItem || beltSlotStack.getItem() instanceof FlintAndSteelItem) {
        matrixStack.scale(0.65F, 0.65F, 0.65F);
      }
      MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, beltSlotStack,
          ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i);
      matrixStack.pop();
    }
  }

}