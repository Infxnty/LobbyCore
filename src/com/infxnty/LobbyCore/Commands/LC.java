package com.infxnty.LobbyCore.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.infxnty.LobbyCore.Main;

public class LC implements CommandExecutor {
	
	private Main main;
	public LC(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String prefix = main.getConfig().getString("prefix");
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("lobbycore.lc")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cInvalid usage!"));
				} else {
					if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
						main.reloadConfig();
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Configuration file successfully reloaded!"));
					}
				}
			} else {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cInvalid permission!"));
			}
		} else {
			sender.sendMessage("Only players can use this command!");
		}
		return false;
	}

}
