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
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.OnAStickItem;
import net.minecraft.item.TridentItem;

@Environment(EnvType.CLIENT)
public class BackToolFeatureRenderer
    extends HeldItemFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

  public BackToolFeatureRenderer(
      FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
    super(featureRendererContext);
  }

  @Override
  public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i,
      AbstractClientPlayerEntity livingEntity, float f, float g, float h, float j, float k, float l) {
    PlayerEntity player = (PlayerEntity) livingEntity;
    ItemStack backSlotStack = player.inventory.getStack(41);
    if (livingEntity instanceof AbstractClientPlayerEntity && !backSlotStack.isEmpty()) {
      matrixStack.push();
      ModelPart modelPart = this.getContextModel().torso;
      modelPart.rotate(matrixStack);
      if (!(backSlotStack.getItem() instanceof TridentItem)) {
        matrixStack.translate(0.0D, 0.0D, 0.22D);
        matrixStack.scale(BackSlotMain.CONFIG.backslot_scale, BackSlotMain.CONFIG.backslot_scale,
            BackSlotMain.CONFIG.backslot_scale);
        if (backSlotStack.getItem() instanceof FishingRodItem || backSlotStack.getItem() instanceof OnAStickItem) {
          matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
          matrixStack.translate(0.0D, -0.3D, 0.0D);
        }
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, backSlotStack,
            ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i);
      } else {
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(52.0F));
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(40.0F));
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-25.F));
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        if (!livingEntity.hasStackEquipped(EquipmentSlot.CHEST)) {
          matrixStack.translate(0.05F, 0.0F, 0.0F);
        }
        matrixStack.translate(-0.28F, 0.0F, 0.0F);
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, backSlotStack,
            ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
      }
      matrixStack.pop();
    }
  }

}