package net.backslot.client.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.backslot.BackSlotMain;
import net.backslot.client.render.BackSlotRenderState;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;

public class BeltSlotFeatureRenderer extends RenderLayer<AvatarRenderState, PlayerModel> {

    public BeltSlotFeatureRenderer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, AvatarRenderState state, float yRot, float xRot) {
        BackSlotRenderState data = (BackSlotRenderState) state;
        ItemStack stack = data.backslot$getBeltStack();
        ItemStackRenderState item = data.backslot$getBeltItem();
        if (stack.isEmpty() || item.isEmpty()) {
            return;
        }

        boolean hasChest = !state.chestEquipment.isEmpty();
        boolean small = stack.getItem() instanceof ShearsItem || stack.getItem() instanceof FlintAndSteelItem;

        poseStack.pushPose();
        this.getParentModel().body.translateAndRotate(poseStack);

        float side = BackSlotMain.CONFIG.switchBeltslotSide ? -0.29F : 0.29F;
        poseStack.translate(side, 0.5F, 0.05F);
        if (stack.getItem() instanceof FlintAndSteelItem) poseStack.translate(0.01F, 0.0F, -0.1F);
        poseStack.rotateDegrees(Axis.YP, -90.0F);
        float s = BackSlotMain.CONFIG.beltslotScaling;
        poseStack.scale(s, s, s);
        if (small) {
            poseStack.scale(0.65F, 0.65F, 0.65F);
            if (!hasChest) {
                poseStack.translate(0.0F, 0.0F, 0.015F);
            }
        }

        item.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
    }
}
