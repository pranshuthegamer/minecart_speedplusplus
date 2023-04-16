package me.pranshutg.minecart_speedplusplus;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
  Logger log = Logger.getLogger("Minecraft");
  
  private final VehicleListener VehicleListener = new VehicleListener(this);
  
  private final SignListener SignListener = new SignListener(this);
  
  static double speedmultiplier = 1.25D;
  
  boolean result;
  
  double multiplier;
  
  public static double getSpeedMultiplier() {
    return speedmultiplier;
  }
  
  public boolean setSpeedMultiplier(double multiplier) {
    if ((((0.0D < multiplier) ? 1 : 0) & ((multiplier <= 4.0D) ? 1 : 0)) != 0) {
      speedmultiplier = multiplier;
      return true;
    } 
    return false;
  }
  
  public void onEnable() {
    this.log.info(getDescription().getName() + " version " + getDescription().getVersion() + " started.");
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(this.VehicleListener, (Plugin)this);
    pm.registerEvents(this.SignListener, (Plugin)this);
  }
  
  public void onDisable() {
    this.log.info(getDescription().getName() + " version " + getDescription().getVersion() + " stopped.");
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (cmd.getName().equalsIgnoreCase("msp")) {
      if (sender instanceof Player) {
        Player player = (Player)sender;
        if (!player.hasPermission("msp.cmd")) {
          player.sendMessage("You don't have permission to do that");
          return true;
        } 
      } 
      try {
        this.multiplier = Double.parseDouble(args[0]);
      } catch (Exception e) {
        sender.sendMessage(ChatColor.YELLOW + "should be a number");
        return false;
      } 
      this.result = setSpeedMultiplier(this.multiplier);
      if (this.result) {
        sender.sendMessage(ChatColor.YELLOW + "multiplier for new Minecarts set to: + this.multiplier");
        return true;
      } 
      sender.sendMessage(ChatColor.YELLOW + "can not be set to zero and must be below");
      return true;
    } 
    return false;
  }
}
