package net.backslot.client.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.backslot.BackSlotMain;
import net.backslot.client.render.BackSlotRenderState;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;

public class BeltSlotFeatureRenderer extends RenderLayer<AvatarRenderState, PlayerModel> {

    public BeltSlotFeatureRenderer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, AvatarRenderState state, float yRot, float xRot) {
        BackSlotRenderState data = (BackSlotRenderState) state;
        ItemStack stack = data.backslot$getBeltStack();
        if (stack.isEmpty()) {
            return;
        }

        BlockModelRenderState block = data.backslot$getBeltBlock();
        if (!block.isEmpty()) {
            poseStack.pushPose();
            this.getParentModel().body.translateAndRotate(poseStack);
            poseStack.rotateDegrees(Axis.ZP, 180.0F);
            poseStack.rotateDegrees(Axis.XP, 20.0F);
            poseStack.translate(-0.1F, -1.0F, -0.28F);
            float s = BackSlotMain.CONFIG.beltslotScaling * 0.6F;
            poseStack.scale(s, s, s);
            block.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
            poseStack.popPose();
            return;
        }

        ItemStackRenderState item = data.backslot$getBeltItem();
        if (item.isEmpty()) {
            return;
        }

        boolean hasChest = !state.chestEquipment.isEmpty();
        boolean small = stack.getItem() instanceof ShearsItem || stack.getItem() instanceof FlintAndSteelItem;

        poseStack.pushPose();
        this.getParentModel().body.translateAndRotate(poseStack);

        boolean doubleBackSword = BackSlotMain.CONFIG.doubleBackSword;
        if (doubleBackSword && stack.is(ItemTags.SWORDS) && data.backslot$getBackStack().is(ItemTags.SWORDS)) {
            poseStack.rotateDegrees(Axis.ZP, -270.0F);
            poseStack.translate(0.23D, -0.25D, 0.28D);
            if (!hasChest) {
                poseStack.translate(0.0F, 0.0F, -0.04F);
            }
        } else if (doubleBackSword && stack.is(ItemTags.SWORDS) && data.backslot$getBackStack().getItem() instanceof ShieldItem) {
            poseStack.translate(-0.07D, -0.05D, 0.23D);
            if (hasChest) {
                poseStack.translate(0.0F, 0.0F, 0.02F);
            }
            if (!BackSlotMain.CONFIG.shieldClipping) {
                poseStack.translate(0.0F, 0.0F, -0.06F);
            }
        } else {
            float side = BackSlotMain.CONFIG.switchBeltslotSide ? -0.29F : 0.29F;
            poseStack.translate(side, 0.5F, 0.05F);
            if (stack.getItem() instanceof FlintAndSteelItem) {
                poseStack.translate(0.01F, 0.0F, -0.1F);
            }
            poseStack.rotateDegrees(Axis.YP, -90.0F);
        }
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
