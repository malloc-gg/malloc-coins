package gg.malloc.coins;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.SoundCategory;
import org.bukkit.Sound;
import net.milkbowl.vault.economy.Economy;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTCompound;

public class CoinPickupHandler implements Listener {

  Economy m_vault;

  public CoinPickupHandler(Economy vault) {
    m_vault = vault;
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent evt) {
    if (evt.getItem() != null) {
      NBTItem nbt = new NBTItem(evt.getItem());
      System.out.println(nbt);
      if (nbt.hasKey("malloc")) {
        NBTCompound mallocData = nbt.getCompound("malloc");
        if (mallocData.hasKey("coinValue")) {
          evt.setCancelled(true);
          int coinValue = mallocData.getInteger("coinValue") * evt.getItem().getAmount();
          m_vault.depositPlayer(evt.getPlayer(), coinValue);
          evt.getPlayer().getWorld().playSound(evt.getPlayer(), Sound.BLOCK_CHAIN_PLACE, SoundCategory.PLAYERS, (float)1.0, (float)1.0);
          evt.getPlayer().getInventory().setItem(evt.getHand(), null);
          evt.setCancelled(true);
        }
      }
    }
  }
  
  @EventHandler
  public void onItemCraftPrepare(PrepareItemCraftEvent evt) {
    for(ItemStack item : evt.getInventory().getMatrix()) {
      if (item != null) {
        NBTItem nbt = new NBTItem(item);
        if (nbt.hasKey("malloc")) {
          evt.getInventory().setResult(null);
          return;
        }
      }
    }
  }

  @EventHandler
  public void onItemPickup(PlayerPickupItemEvent evt) {
    NBTItem nbt = new NBTItem(evt.getItem().getItemStack());
    System.out.println(nbt);
    if (nbt.hasKey("malloc")) {
      NBTCompound mallocData = nbt.getCompound("malloc");
      if (mallocData.hasKey("coinValue")) {
        evt.setCancelled(true);
        int coinValue = mallocData.getInteger("coinValue") * evt.getItem().getItemStack().getAmount();
        m_vault.depositPlayer(evt.getPlayer(), coinValue);
        evt.getPlayer().getWorld().playSound(evt.getPlayer(), Sound.BLOCK_CHAIN_PLACE, SoundCategory.PLAYERS, (float)1.0, (float)1.0);
        evt.getItem().remove();
      }
    }
  }
}
