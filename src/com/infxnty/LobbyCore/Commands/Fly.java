package com.infxnty.LobbyCore.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.infxnty.LobbyCore.Main;

public class Fly implements CommandExecutor {
	
	private Main main;
	public Fly(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String prefix = main.getConfig().getString("prefix");
		
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("fly")) {
				Player p = (Player) sender;
				if (p.hasPermission("lobbycore.fly")) {
					if (main.fly.contains(p)) {
						p.setAllowFlight(false);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Flight turned &coff&7!"));
						main.fly.remove(p);
					} else {
						p.setAllowFlight(true);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Flight turned &aon&7!"));
						main.fly.add(p);
					}
				} else {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Purchase a rank at &bstore.minedome.net &7to do this!"));
				}
			}
		} else {
			sender.sendMessage("Only players can use this command.");
		}
		
		return false;
	}

}
