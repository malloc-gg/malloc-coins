package gg.malloc.coins;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Plugin extends JavaPlugin {

  Economy m_vault;

  @Override
  public void onEnable() {
    if (setupVault()) {
      getLogger().info("Malloc Coins registered");
      getServer().getPluginManager().registerEvents(new CoinPickupHandler(m_vault), this);
      getCommand("coins").setExecutor(new CoinsCommand());
    } else {
      getLogger().info("Unable to register Malloc Coins: no vaul!");
    }
  }

  private boolean setupVault() {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
				return false;
		}
		m_vault = rsp.getProvider();
		return m_vault != null;
  }
}
