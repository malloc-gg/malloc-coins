package gg.malloc.coins;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTCompound;


public class CoinsCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      PlayerInventory inventory = player.getInventory();
      ItemStack itemStack = new ItemStack(Material.IRON_NUGGET, 64);
      ItemMeta coinMeta = itemStack.getItemMeta();
      coinMeta.setCustomModelData(93197);
      coinMeta.setDisplayName("Grist");
      // TODO: Lore
      itemStack.setItemMeta(coinMeta);

      NBTItem nbt = new NBTItem(itemStack);
      nbt.addCompound("malloc").setInteger("coinValue", 1);
      inventory.addItem(nbt.getItem());
      sender.sendMessage("You received 64 grist");
      return true;
    } else {
      sender.sendMessage("You must be a player.");
      return false;
    }
  }

}
