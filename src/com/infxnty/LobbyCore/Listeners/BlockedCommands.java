package com.infxnty.LobbyCore.Listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.infxnty.LobbyCore.Main;

import net.md_5.bungee.api.ChatColor;

public class BlockedCommands implements Listener {
	
	private Main main;
	public BlockedCommands(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void preCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		
		String prefix = main.getConfig().getString("prefix");
		
		@SuppressWarnings("unchecked")
		List<String> l = (List<String>) main.getConfig().getList("blockedcmds");
		
		for (String i : l) {
			if (e.getMessage().toLowerCase().contains(i) && !p.hasPermission("lobbycore.bypass")) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Invalid permission!"));
			}
		}
		
	}
	
}
