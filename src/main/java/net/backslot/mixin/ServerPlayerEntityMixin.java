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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.GameRules;
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

  public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
    super(world, pos, yaw, profile);
  }

  @Inject(method = "playerTick", at = @At("HEAD"))
  private void playerTickMixin(CallbackInfo info) {
    if (!this.world.isClient) {
      if (!ItemStack.areItemsEqualIgnoreDamage(backSlotStack, this.inventory.getStack(41))) {
        sendPacket(41);
      }
      backSlotStack = this.inventory.getStack(41);
      if (!ItemStack.areItemsEqualIgnoreDamage(beltSlotStack, this.inventory.getStack(42))) {
        sendPacket(42);
      }
      beltSlotStack = this.inventory.getStack(42);
    }
  }

  // Gravestone compat
  @Inject(method = "onDeath", at = @At("HEAD"))
  public void onDeathMixin(DamageSource source, CallbackInfo info) {
    if (FabricLoader.getInstance().isModLoaded("gravestones")
        && !this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
      if (!this.inventory.getStack(41).isEmpty()) {
        this.dropStack(this.inventory.getStack(41));
        this.inventory.removeStack(41);
      }
      if (!this.inventory.getStack(42).isEmpty()) {
        this.dropStack(this.inventory.getStack(42));
        this.inventory.removeStack(42);
      }
    }
  }

  private void sendPacket(int slot) {
    Collection<ServerPlayerEntity> players = PlayerLookup.tracking((ServerWorld) world, this.getBlockPos());
    PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
    data.writeIntArray(new int[] { this.getEntityId(), slot });
    data.writeItemStack(this.inventory.getStack(slot));
    players.forEach(player -> ServerPlayNetworking.send(player, SyncPacket.VISIBILITY_UPDATE_PACKET, data));
  }

}
