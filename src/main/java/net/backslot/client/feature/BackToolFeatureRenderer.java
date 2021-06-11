package net.backslot.client.feature;

import chronosacaria.mcdw.bases.McdwBow;
import chronosacaria.mcdw.bases.McdwHammer;
import chronosacaria.mcdw.bases.McdwStaff;
import chronosacaria.mcdw.bases.McdwSword;
import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.medievalweapons.item.Big_Axe_Item;
import net.medievalweapons.item.Healing_Staff_Item;
import net.medievalweapons.item.Javelin_Item;
import net.medievalweapons.item.Lance_Item;
import net.medievalweapons.item.Long_Bow_Item;
import net.medievalweapons.item.Long_Sword_Item;
import net.medievalweapons.item.Small_Axe_Item;
import net.medievalweapons.item.Thalleous_Sword_Item;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
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
    ItemStack backSlotStack = player.getInventory().getStack(41);
    if (livingEntity instanceof AbstractClientPlayerEntity && !backSlotStack.isEmpty()) {
      matrixStack.push();
      ModelPart modelPart = this.getContextModel().body;
      modelPart.rotate(matrixStack);
      Item backSloItem = backSlotStack.getItem();
      FabricLoader loader = FabricLoader.getInstance();
      if (!this.isSpecialModelItem(backSloItem, loader)) {
        matrixStack.translate(0.0D, 0.0D, 0.22D);
        float downScaling = 0.0F; // Can be used to downSlace specific items
        if (loader.isModLoaded("medievalweapons")) {
          if (backSlotStack.getItem() instanceof Long_Bow_Item) {
            matrixStack.scale(1.0F, 1.0F, 0.5F);
          }
          if (backSlotStack.getItem() instanceof Small_Axe_Item) {
            matrixStack.translate(0.3D, 0.0D, 0.0D);
          }
          if (backSlotStack.getItem() instanceof Long_Sword_Item) {
            matrixStack.translate(0.3D, 0.0D, 0.0D);
          }
        }
        if (loader.isModLoaded("mcdw")) {
          if (backSlotStack.getItem() instanceof McdwHammer) {
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270.0F));
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
          if (backSlotStack.getItem() instanceof McdwSword
              && backSlotStack.getItem().getTranslationKey().contains("sword_dancers_sword")) {
            matrixStack.translate(0.3D, 0.3D, 0.0D);
          }
        }
        matrixStack.scale(BackSlotMain.CONFIG.backslot_scale + downScaling,
            BackSlotMain.CONFIG.backslot_scale + downScaling, BackSlotMain.CONFIG.backslot_scale + downScaling);
        if (backSlotStack.getItem() instanceof FishingRodItem || backSlotStack.getItem() instanceof OnAStickItem) {
          matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
          matrixStack.translate(0.0D, -0.3D, 0.0D);
        }
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, backSlotStack,
            ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i);
      } else {
        if (backSloItem instanceof TridentItem) {
          matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(52.0F));
          matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(40.0F));
          matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-25.F));
        } else if (loader.isModLoaded("medievalweapons")) {
          if (backSloItem instanceof Lance_Item || backSloItem instanceof Healing_Staff_Item) {
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F));
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(50F));
            matrixStack.translate(0.0D, 0.2D, 0.0D);
          } else if (backSloItem instanceof Thalleous_Sword_Item) {
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F));
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(240F));
            matrixStack.translate(-0.01D, 0.5D, 0.3D);
          } else if (backSloItem instanceof Javelin_Item) {
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F));
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(40F));
            matrixStack.translate(0.02D, 0.5D, 0.0D);
          } else if (backSloItem instanceof Big_Axe_Item) {
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(48F));
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(220F));
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(267F));
            matrixStack.translate(0.9D, 0.05D, 0.1D);
          }
        }

        matrixStack.scale(1.0F, -1.0F, -1.0F);
        if (!livingEntity.hasStackEquipped(EquipmentSlot.CHEST)) {
          matrixStack.translate(0.05F, 0.0F, 0.0F);
        } else if (loader.isModLoaded("medievalweapons")
            && (backSloItem instanceof Lance_Item || backSloItem instanceof Healing_Staff_Item
                || backSloItem instanceof Thalleous_Sword_Item || backSloItem instanceof Javelin_Item)) {
          matrixStack.translate(0.05D, 0.0D, 0.0D);
        }
        matrixStack.translate(-0.28D, 0.0D, 0.0D);
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, backSlotStack,
            ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
      }
      matrixStack.pop();
    }
  }

  // Belt slot has to get fixed too

  private boolean isSpecialModelItem(Item item, FabricLoader loader) {
    if (item instanceof TridentItem) {
      return true;
    } else if (loader.isModLoaded("medievalweapons")
        && (item instanceof Healing_Staff_Item || item instanceof Big_Axe_Item || item instanceof Javelin_Item
            || item instanceof Lance_Item || item instanceof Thalleous_Sword_Item)) {
      return true;
    } else
      return false;
  }

}