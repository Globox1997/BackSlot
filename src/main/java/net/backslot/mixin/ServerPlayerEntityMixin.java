package net.backslot.mixin;

import java.util.stream.Stream;

import com.mojang.authlib.GameProfile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.buffer.Unpooled;

import org.spongepowered.asm.mixin.injection.At;

import net.backslot.network.SyncPacket;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.World;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
  @Unique
  ItemStack backslot = ItemStack.EMPTY;

  public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
    super(world, pos, yaw, profile);
  }

  @Inject(method = "playerTick", at = @At("HEAD"))
  private void playerTickMixin(CallbackInfo info) {
    if (!this.world.isClient) {
      if (!ItemStack.areItemsEqualIgnoreDamage(backslot, this.inventory.getStack(41))) {
        Stream<PlayerEntity> players = PlayerStream.watching(world, this.getBlockPos());
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeInt(this.getEntityId());
        data.writeItemStack(this.inventory.getStack(41));
        players.forEach(player -> ServerSidePacketRegistry.INSTANCE.sendToPlayer(player,
            SyncPacket.VISIBILITY_UPDATE_PACKET, data));
      }
      backslot = this.inventory.getStack(41);
    }
  }

}
