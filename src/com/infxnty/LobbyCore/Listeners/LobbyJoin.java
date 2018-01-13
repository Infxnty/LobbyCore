package com.infxnty.LobbyCore.Listeners;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.infxnty.LobbyCore.Main;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class LobbyJoin implements Listener {
	
	private Main main;
	public LobbyJoin(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		sendTablistHeaderAndFooter(p, ChatColor.translateAlternateColorCodes('&', " \n &7You are playing on &bMineDome \n "), ChatColor.translateAlternateColorCodes('&', " \n &bForums &8» &7minedome.net \n &bStore &8» &7store.minedome.net \n"));
		
		p.setFoodLevel(20);
		p.setGameMode(GameMode.ADVENTURE);
		
		List<String> dlore = new ArrayList<>();
		dlore.add(ChatColor.GRAY + "Right-Click to toggle player");
		dlore.add(ChatColor.GRAY + "visibility!");
		main.d = new ItemStack(351, 1, (short) 10);
		main.dm = main.d.getItemMeta();
		main.dm.setLore(dlore);
		main.dm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		main.dm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Player Visibility &8» &aOn"));
		main.d.setItemMeta(main.dm);
		
		List<String> blore = new ArrayList<>();
		blore.add(ChatColor.GRAY + "Right-Click to open the server");
		blore.add(ChatColor.GRAY + "store!");
		main.b = new ItemStack(Material.BOOK);
		main.bm = main.d.getItemMeta();
		main.bm.setLore(blore);
		main.bm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		main.bm.setDisplayName(ChatColor.YELLOW + "Server Store");
		main.b.setItemMeta(main.bm);
		
		List<String> slore = new ArrayList<>();
		slore.add(ChatColor.GRAY + "Right-Click to open the server");
		slore.add(ChatColor.GRAY + "selector menu!");
		main.s = new ItemStack(Material.NETHER_STAR);
		main.sm = main.s.getItemMeta();
		main.sm.setDisplayName(ChatColor.AQUA + "Server Selector");
		main.sm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		main.sm.setLore(slore);
		main.s.setItemMeta(main.sm);
		
		p.getInventory().clear();
		p.getInventory().setItem(0, main.s);
		p.getInventory().setItem(1, main.b);
		p.getInventory().setItem(8, main.d);
		
		main.enabled.add(p);
		
		e.setJoinMessage(null);
		
	}
	
	// Allows us to modify the tablist
	public void sendTablistHeaderAndFooter(Player p, String header, String footer) {
		if(header == null) header = "";
		if(footer == null) footer = "";
		
		IChatBaseComponent tabHeader = ChatSerializer.a("{\"text\":\"" + header + "\"}");
		IChatBaseComponent tabFooter = ChatSerializer.a("{\"text\":\"" + footer + "\"}");
	
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabHeader);
		try {
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFooter);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(headerPacket);
		}
	}
	
}
