package me.pranshutg.minecart_speedplusplus;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.*;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

public class VehicleListener implements Listener {

	int[] xmodifier = { -1, 0, 1 };
	int[] ymodifier = { -2, -1, 0, 1, 2 };
	int[] zmodifier = { -1, 0, 1 };

	Location cartLocation;
	int blockx, blocky, blockz;
	Location blockLocation;

	Block block;
	BlockData blockData;

	double line1;

	public static App plugin;
	Logger log = Logger.getLogger("Minecraft");

	boolean error;
	
	Vector flyingmod = new Vector(10 , 0.01 , 10);
	Vector noflyingmod = new Vector(1, 1, 1);

	public VehicleListener(App instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onVehicleCreate(VehicleCreateEvent event) {
		if (event.getVehicle() instanceof Minecart) {

			Minecart cart = (Minecart) event.getVehicle();
			cart.setMaxSpeed(0.4 * App.getSpeedMultiplier());

		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onVehicleMove(VehicleMoveEvent event) {

		if (event.getVehicle() instanceof Minecart) {

			Minecart cart = (Minecart) event.getVehicle();
			for (int xmod : xmodifier) {
				for (int ymod : ymodifier) {
					for (int zmod : zmodifier) {
						cartLocation = cart.getLocation();
						blockx = (int)cartLocation.getX() + xmod;
						blocky = (int)cartLocation.getY() + ymod;
						blockz = (int)cartLocation.getZ() + zmod;
						blockLocation.setX(blockx);
						blockLocation.setY(blocky);
						blockLocation.setZ(blockz);
						block = cart.getWorld().getBlockAt(blockx, blocky,
								blockz);
						blockData = cart.getWorld().getBlockData(blockLocation);

						if (blockData instanceof WallSign
						    || blockData instanceof Sign)
						{
							Sign sign = (Sign) block.getState();
							String[] text = sign.getLines();

							if (text[0].equalsIgnoreCase("[msp]")) {

								if (text[1].equalsIgnoreCase("fly")) {
									cart.setFlyingVelocityMod(flyingmod);
									
								} else if (text[1].equalsIgnoreCase("nofly")) {
									
									cart.setFlyingVelocityMod(noflyingmod);
									
								} else {

									error = false;
									try {

										line1 = Double.parseDouble(text[1]);

									} catch (Exception e) {

										sign.setLine(2, "  ERROR");
										sign.setLine(3, "WRONG VALUE");
										sign.update();
										error = true;

									}
									if (!error) {

										if (0 < line1 & line1 <= 50) {

											cart.setMaxSpeed(0.4D * Double.parseDouble(text[1]));

										} else {
											
											sign.setLine(2, "  ERROR");
											sign.setLine(3, "WRONG VALUE");
											sign.update();
										}
									}
								}
							}

						}

					}
				}
			}

		}
	}

}
