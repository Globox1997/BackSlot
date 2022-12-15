package net.backslot.client.feature;

import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.OnAStickItem;
import net.minecraft.item.TridentItem;

@Environment(EnvType.CLIENT)
public class BackToolFeatureRenderer extends HeldItemFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private final HeldItemRenderer heldItemRenderer;

    public BackToolFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context, HeldItemRenderer heldItemRenderer) {
        super(context, heldItemRenderer);
        this.heldItemRenderer = heldItemRenderer;
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

            if (backSloItem instanceof TridentItem) {
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(52.0F));
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(40.0F));
                matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-25.0F));
                matrixStack.translate(-0.26D, 0.0D, 0.0D);
                if (!livingEntity.hasStackEquipped(EquipmentSlot.CHEST))
                    matrixStack.translate(0.05F, 0.0F, 0.0F);
                matrixStack.scale(1.0F, -1.0F, -1.0F);
                heldItemRenderer.renderItem(livingEntity, backSlotStack, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
            } else {
                matrixStack.translate(0.0D, 0.0D, 0.16D);
                matrixStack.scale(BackSlotMain.CONFIG.backslot_scaling, BackSlotMain.CONFIG.backslot_scaling, BackSlotMain.CONFIG.backslot_scaling);
                if (backSlotStack.getItem() instanceof FishingRodItem || backSlotStack.getItem() instanceof OnAStickItem) {
                    matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
                    matrixStack.translate(0.0D, -0.3D, 0.0D);
                }
                if (livingEntity.hasStackEquipped(EquipmentSlot.CHEST))
                    matrixStack.translate(0.0F, 0.0F, 0.06F);

                heldItemRenderer.renderItem(livingEntity, backSlotStack, ModelTransformation.Mode.HEAD, false, matrixStack, vertexConsumerProvider, i);
            }
            matrixStack.pop();
        }
    }

}