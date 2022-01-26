package net.backslot.client.feature;

import chronosacaria.mcdw.enums.SoulDaggersID;
import chronosacaria.mcdw.items.ItemsInit;
import net.backslot.BackSlotMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.util.math.Vec3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;

@Environment(EnvType.CLIENT)
public class BeltSlotFeatureRenderer extends HeldItemFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public BeltSlotFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
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
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
            float downScaling = 0.0F;
            /*This code is no longer needed with the change from ModelTransformation.Mode.GROUND to .HEAD down below.
              All the scaling, positioning for MCDW/Most other mods can be done within the .json files, in the [head] category.
              Leaving it as comments just in case. */
            /*if (BackSlotMain.isMedievalWeaponsLoaded) {
                if (beltSlotStack.getItem() instanceof Small_Axe_Item) {
                    downScaling = -0.5F;
                }
                if (beltSlotStack.getItem() instanceof Long_Sword_Item) {
                    downScaling = -0.3F;
                }
            }
            if (BackSlotMain.isMcdwLoaded && (beltSlotStack.getItem() == ItemsInit.soulDaggerItems.get(SoulDaggersID.DAGGER_ETERNAL_KNIFE)
                    || beltSlotStack.getItem() == ItemsInit.soulDaggerItems.get(SoulDaggersID.SWORD_TRUTHSEEKER))) {
                downScaling = -0.3F;
            }*/
            matrixStack.scale(BackSlotMain.CONFIG.beltslot_scale + downScaling, BackSlotMain.CONFIG.beltslot_scale + downScaling, BackSlotMain.CONFIG.beltslot_scale + downScaling);
            if (beltSlotStack.getItem() instanceof ShearsItem || beltSlotStack.getItem() instanceof FlintAndSteelItem) {
                matrixStack.scale(0.65F, 0.65F, 0.65F);
                if (!livingEntity.hasStackEquipped(EquipmentSlot.CHEST)) {
                    matrixStack.translate(0.0F, 0.0F, 0.015F);
                }
            }
            MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, beltSlotStack, ModelTransformation.Mode.HEAD, false, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }
    }

}