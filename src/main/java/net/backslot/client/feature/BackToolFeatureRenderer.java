package net.backslot.client.feature;

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
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.ToolItem;

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
    ItemStack stack = player.inventory.getStack(41);

    if (livingEntity instanceof AbstractClientPlayerEntity && stack.getItem() instanceof ToolItem
        || stack.getItem() instanceof RangedWeaponItem) {
      matrixStack.push();
      ModelPart modelPart = this.getContextModel().torso;
      modelPart.rotate(matrixStack);
      // matrixStack.scale(1.5F, 1.5F, 1.5F);
      // matrixStack.translate(0.18D, 0.38D, 0.0D);
      // matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
      matrixStack.scale(2.0F, 2.0F, 2.0F);
      matrixStack.translate(0.0D, 0.0D, 0.11D);

      MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, stack,
          ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i);
      matrixStack.pop();

    }
  }

}