package com.infxnty.LobbyCore.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.infxnty.LobbyCore.Main;

public class ChatFormat implements Listener {
	
	private Main main;
	public ChatFormat(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		
		String format = "";
		format = main.getConfig().getString("chatformat");
		format = format.replace("{PLAYER_NAME}", p.getName());
		format = main.replaceVault(p, format);
		format = ChatColor.translateAlternateColorCodes('&', format);
		format = format.replace("{MESSAGE}", "%2$s");
		
		e.setFormat(format);
		
	}
	
}
