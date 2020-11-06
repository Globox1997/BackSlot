package net.backslot.client.feature;

import chronosacaria.mcdw.bases.McdwBow;
import chronosacaria.mcdw.bases.McdwHammer;
import chronosacaria.mcdw.bases.McdwStaff;
import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.medievalweapons.item.Long_Bow_Item;
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
        float downScaling = 0.0F;
        FabricLoader loader = FabricLoader.getInstance();
        if (loader.isModLoaded("medievalweapons")) {
          if (backSlotStack.getItem() instanceof Long_Bow_Item) {
            downScaling = -1.3F;
          }
          if (backSlotStack.getItem() instanceof Small_Axe_Item) {
            downScaling = -0.5F;
          }
          if (backSlotStack.getItem() instanceof Long_Sword_Item) {
            downScaling = -0.2F;
          }
        }
        if (loader.isModLoaded("mcdw")) {
          if (backSlotStack.getItem() instanceof McdwHammer) {
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270.0F));
            matrixStack.translate(0.0D, -0.4D, -0.2D);
          }
          if (backSlotStack.getItem() instanceof McdwBow
              && !backSlotStack.getItem().getTranslationKey().contains("bow_bonebow")
              && !backSlotStack.getItem().getTranslationKey().contains("bow_longbow")
              && !backSlotStack.getItem().getTranslationKey().contains("bow_red_snake")
              && !backSlotStack.getItem().getTranslationKey().contains("bow_guardian_bow")) {
            matrixStack.translate(0.0D, 0.23D, 0.0D);
          }
          if (backSlotStack.getItem() instanceof McdwStaff) {
            matrixStack.translate(0.3D, 0.3D, 0.0D);
          }
        }
        matrixStack.scale(BackSlotMain.CONFIG.backslot_scale + downScaling,
            BackSlotMain.CONFIG.backslot_scale + downScaling, BackSlotMain.CONFIG.backslot_scale + downScaling);
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