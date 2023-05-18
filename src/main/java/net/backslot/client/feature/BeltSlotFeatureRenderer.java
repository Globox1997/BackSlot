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
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class BeltSlotFeatureRenderer extends HeldItemFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private final HeldItemRenderer heldItemRenderer;

    public BeltSlotFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context, HeldItemRenderer heldItemRenderer) {
        super(context, heldItemRenderer);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity livingEntity, float f, float g, float h, float j, float k, float l) {
        PlayerEntity player = (PlayerEntity) livingEntity;
        ItemStack beltSlotStack = player.getInventory().getStack(42);
        if (livingEntity instanceof AbstractClientPlayerEntity && !beltSlotStack.isEmpty()) {
            matrixStack.push();
            ModelPart modelPart = this.getContextModel().body;
            modelPart.rotate(matrixStack);
            double switchBeltSide = 0.29D;
            if (BackSlotMain.CONFIG.switch_beltslot_side) {
                switchBeltSide = -0.29D;
            }
            matrixStack.translate(switchBeltSide, 0.5D, 0.05D);
            if (beltSlotStack.getItem() instanceof FlintAndSteelItem) {
                matrixStack.translate(0.01F, 0.0F, -0.1F);
            }
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
            matrixStack.scale(BackSlotMain.CONFIG.beltslot_scaling, BackSlotMain.CONFIG.beltslot_scaling, BackSlotMain.CONFIG.beltslot_scaling);
            if (beltSlotStack.getItem() instanceof ShearsItem || beltSlotStack.getItem() instanceof FlintAndSteelItem) {
                matrixStack.scale(0.65F, 0.65F, 0.65F);
                if (!livingEntity.hasStackEquipped(EquipmentSlot.CHEST)) {
                    matrixStack.translate(0.0F, 0.0F, 0.015F);
                }
            }
            heldItemRenderer.renderItem(livingEntity, beltSlotStack, ModelTransformationMode.HEAD, false, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }
    }

}