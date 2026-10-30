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
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;

public class BackToolFeatureRenderer extends RenderLayer<AvatarRenderState, PlayerModel> {

    public BackToolFeatureRenderer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, AvatarRenderState state, float yRot, float xRot) {
        BackSlotRenderState data = (BackSlotRenderState) state;
        ItemStack stack = data.backslot$getBackStack();
        ItemStackRenderState item = data.backslot$getBackItem();
        if (stack.isEmpty() || item.isEmpty()) {
            return;
        }

        boolean hasChest = !state.chestEquipment.isEmpty();
        Item type = stack.getItem();

        poseStack.pushPose();
        this.getParentModel().body.translateAndRotate(poseStack);

        if (type instanceof TridentItem) {
            poseStack.rotateDegrees(Axis.YP, 52.0F);
            poseStack.rotateDegrees(Axis.XP, 40.0F);
            poseStack.rotateDegrees(Axis.ZP, -25.0F);
            poseStack.translate(-0.26F, 0.0F, 0.0F);
            if (!hasChest) poseStack.translate(0.05F, 0.0F, 0.0F);
            poseStack.scale(1.0F, -1.0F, -1.0F);
        } else {
            poseStack.translate(0.0F, 0.0F, 0.16F);
            float s = BackSlotMain.CONFIG.backslotScaling;
            poseStack.scale(s, s, s);
            if (type instanceof FishingRodItem || type instanceof FoodOnAStickItem) {
                poseStack.rotateDegrees(Axis.ZP, 180.0F);
                poseStack.translate(0.0F, -0.3F, 0.0F);
            }
            if (hasChest) {
                poseStack.translate(0.0F, 0.0F, 0.06F);
            }
        }

        item.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
    }
}
