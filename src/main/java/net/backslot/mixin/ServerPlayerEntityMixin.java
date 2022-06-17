package net.backslot.mixin;

import java.util.Collection;

import com.mojang.authlib.GameProfile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.buffer.Unpooled;

import org.spongepowered.asm.mixin.injection.At;

import net.backslot.network.SyncPacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.world.World;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    @Unique
    ItemStack backSlotStack = ItemStack.EMPTY;
    @Unique
    ItemStack beltSlotStack = ItemStack.EMPTY;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Inject(method = "playerTick", at = @At("HEAD"))
    private void playerTickMixin(CallbackInfo info) {
        if (!this.world.isClient) {
            if (!ItemStack.areItemsEqualIgnoreDamage(backSlotStack, this.getInventory().getStack(41))) {
                sendPacket(41);
            }
            backSlotStack = this.getInventory().getStack(41);
            if (!ItemStack.areItemsEqualIgnoreDamage(beltSlotStack, this.getInventory().getStack(42))) {
                sendPacket(42);
            }
            beltSlotStack = this.getInventory().getStack(42);
        }
    }

    private void sendPacket(int slot) {
        Collection<ServerPlayerEntity> players = PlayerLookup.tracking((ServerWorld) world, this.getBlockPos());
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeIntArray(new int[] { this.getId(), slot });
        data.writeItemStack(this.getInventory().getStack(slot));
        players.forEach(player -> ServerPlayNetworking.send(player, SyncPacket.VISIBILITY_UPDATE_PACKET, data));
    }

}
