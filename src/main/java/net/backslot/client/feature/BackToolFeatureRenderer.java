package net.backslot.client.feature;

import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.medievalweapons.item.Big_Axe_Item;
import net.medievalweapons.item.Healing_Staff_Item;
import net.medievalweapons.item.Javelin_Item;
import net.medievalweapons.item.Lance_Item;
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
public class BackToolFeatureRenderer extends HeldItemFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public BackToolFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity livingEntity, float f, float g, float h, float j, float k, float l) {
        PlayerEntity player = (PlayerEntity) livingEntity;
        ItemStack backSlotStack = player.getInventory().getStack(41);
        if (livingEntity instanceof AbstractClientPlayerEntity && !backSlotStack.isEmpty()) {
            matrixStack.push();
            ModelPart modelPart = this.getContextModel().body;
            modelPart.rotate(matrixStack);
            Item backSloItem = backSlotStack.getItem();

            if (!this.isSpecialModelItem(backSloItem)) {
                matrixStack.translate(0.0D, 0.0D, 0.22D);
                matrixStack.scale(BackSlotMain.CONFIG.backslot_scale, BackSlotMain.CONFIG.backslot_scale, BackSlotMain.CONFIG.backslot_scale);
                if (backSlotStack.getItem() instanceof FishingRodItem || backSlotStack.getItem() instanceof OnAStickItem) {
                    matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
                    matrixStack.translate(0.0D, -0.3D, 0.0D);
                }
                if (!livingEntity.hasStackEquipped(EquipmentSlot.CHEST)) {
                    matrixStack.translate(0.0F, 0.0F, -0.04F);
                }
                MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, backSlotStack, ModelTransformation.Mode.HEAD, false, matrixStack, vertexConsumerProvider, i);
            } else {
                if (backSloItem instanceof TridentItem) {
                    matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(52.0F));
                    matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(40.0F));
                    matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-25.0F));
                    matrixStack.translate(-0.26D, 0.0D, 0.0D);
                } else
                // Check for custom models
                if (BackSlotMain.isMedievalWeaponsLoaded) {
                    if (backSloItem instanceof Lance_Item || backSloItem instanceof Healing_Staff_Item) {
                        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F));
                        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(50F));
                        matrixStack.translate(-0.2D, 0.2D, 0.0D);
                    } else if (backSloItem instanceof Thalleous_Sword_Item) {
                        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F));
                        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(240F));
                        matrixStack.translate(-0.23D, 0.5D, 0.3D);
                    } else if (backSloItem instanceof Javelin_Item) {
                        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F));
                        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(40F));
                        matrixStack.translate(-0.2D, 0.5D, 0.0D);
                    } else if (backSloItem instanceof Big_Axe_Item) {
                        if (!player.getOffHandStack().isEmpty()) {
                            matrixStack.translate(0.1D, 0.2D, -0.68D);
                            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(20.0F));
                            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-30.0F));
                            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(60.0F));
                        }
                        matrixStack.translate(0.4D, 0.7D, 0.14D);
                        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(50.0F));
                        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-30.0F));
                        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-120.0F));
                        if (livingEntity.hasStackEquipped(EquipmentSlot.CHEST))
                            matrixStack.translate(0.1F, 0.0F, 0.0F);
                    }
                }

                if (!livingEntity.hasStackEquipped(EquipmentSlot.CHEST))
                    matrixStack.translate(0.05F, 0.0F, 0.0F);
                matrixStack.scale(1.0F, -1.0F, -1.0F);
                MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, backSlotStack, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, false, matrixStack,
                        vertexConsumerProvider, i);
            }
            matrixStack.pop();
        }
    }

    private boolean isSpecialModelItem(Item item) {
        if (item instanceof TridentItem) {
            return true;
        } else if (BackSlotMain.isMedievalWeaponsLoaded
                && (item instanceof Healing_Staff_Item || item instanceof Big_Axe_Item || item instanceof Javelin_Item || item instanceof Lance_Item || item instanceof Thalleous_Sword_Item)) {
            return true;
        } else
            return false;
    }
}