package net.backslot.mixin.client;

import net.backslot.client.feature.BackToolFeatureRenderer;
import net.backslot.client.feature.BeltSlotFeatureRenderer;
import net.backslot.client.render.BackSlotRenderState;
import net.backslot.slot.BackSlot;
import net.backslot.slot.BeltSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> extends LivingEntityRenderer<AvatarlikeEntity, AvatarRenderState, PlayerModel> {

    public AvatarRendererMixin(EntityRendererProvider.Context ctx, PlayerModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void backslot$init(EntityRendererProvider.Context ctx, boolean slim, CallbackInfo info) {
        this.addLayer(new BackToolFeatureRenderer(this));
        this.addLayer(new BeltSlotFeatureRenderer(this));
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void backslot$extract(Avatar entity, AvatarRenderState state, float partialTicks, CallbackInfo info) {
        BackSlotRenderState data = (BackSlotRenderState) state;

        data.backslot$getBackItem().clear();
        data.backslot$getBeltItem().clear();
        data.backslot$setBackStack(ItemStack.EMPTY);
        data.backslot$setBeltStack(ItemStack.EMPTY);

        if (!(entity instanceof Player player)) return;

        ItemStack back = player.getInventory().getItem(BackSlot.INVENTORY_INDEX);
        ItemStack belt = player.getInventory().getItem(BeltSlot.INVENTORY_INDEX);

        if (!back.isEmpty()) {
            data.backslot$setBackStack(back);
            ItemDisplayContext ctx = back.getItem() instanceof TridentItem ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.HEAD;
            this.itemModelResolver.updateForLiving(data.backslot$getBackItem(), back, ctx, entity);
        }
        if (!belt.isEmpty()) {
            data.backslot$setBeltStack(belt);
            this.itemModelResolver.updateForLiving(data.backslot$getBeltItem(), belt, ItemDisplayContext.HEAD, entity);
        }
    }
}
