package com.infxnty.LobbyCore.Listeners;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import org.bukkit.inventory.meta.ItemMeta;

import com.infxnty.LobbyCore.Main;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class LobbyJoin implements Listener {
	
	private Main main;
	public LobbyJoin(Main main) {
		this.main = main;
	}
	
	// Allows us to modify the tablist header/footer
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
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		if (p.hasPlayedBefore()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-------------------------------------------"));
			p.sendMessage("");
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7  Welcome back to &bMineDome &7" + p.getDisplayName() + "!"));
			p.sendMessage("");
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8  » &7Forums: &bminedome.net"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8  » &7Store: &bstore.minedome.net"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8  » &7Teamspeak: &bts.minedome.net"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8  » &7Discord: &bdiscord.minedome.net"));
			p.sendMessage("");
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-------------------------------------------"));
			
			e.setJoinMessage(null);
		} else {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-------------------------------------------"));
			p.sendMessage("");
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7  Welcome to &bMineDome &7" + p.getDisplayName() + "!"));
			p.sendMessage("");
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8  » &7Forums: &bminedome.net"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8  » &7Store: &bstore.minedome.net"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8  » &7Teamspeak: &bts.minedome.net"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&   » &7Discord: &bdiscord.minedome.net"));
			p.sendMessage("");
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-------------------------------------------"));
			
			e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&dWelcome &n" + p.getDisplayName() + "to the server!"));
		}
		
		sendTablistHeaderAndFooter(p, ChatColor.translateAlternateColorCodes('&', " \n &7You are playing on &bMineDome \n "), ChatColor.translateAlternateColorCodes('&', " \n &bForums &8» &7minedome.net \n &bStore &8» &7store.minedome.net \n"));
		
		p.setMaxHealth(20.0);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setGameMode(GameMode.ADVENTURE);
		
		// Fly perk
		if (p.hasPermission("lobbycore.fly")) {
			p.setAllowFlight(true);
			main.fly.add(p);
		}
		
		List<String> dlore = new ArrayList<>();
		dlore.add(ChatColor.GRAY + "Right-Click to toggle player");
		dlore.add(ChatColor.GRAY + "visibility!");
		main.d = new ItemStack(351, 1, (short) 10);
		main.dm = main.d.getItemMeta();
		main.dm.setLore(dlore);
		main.dm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		main.dm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Player Visibility &8» &aOn &8(&7Right-Click&8)"));
		main.d.setItemMeta(main.dm);
		
		List<String> blore = new ArrayList<>();
		blore.add(ChatColor.GRAY + "Right-Click to get the link to");
		blore.add(ChatColor.GRAY + "our store in chat!");
		main.b = new ItemStack(Material.CHEST);
		main.bm = main.d.getItemMeta();
		main.bm.setLore(blore);
		main.bm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		main.bm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&dServer Store &8(&7Right-Click&8)"));
		main.b.setItemMeta(main.bm);
		
		List<String> slore = new ArrayList<>();
		slore.add(ChatColor.GRAY + "Right-Click to open the server");
		slore.add(ChatColor.GRAY + "selector menu!");
		main.s = new ItemStack(Material.NETHER_STAR);
		main.sm = main.s.getItemMeta();
		main.sm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bServer Selector &8(&7Right-Click&8)"));
		main.sm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		main.sm.setLore(slore);
		main.s.setItemMeta(main.sm);
		
		List<String> flore = new ArrayList<>();
		flore.add(ChatColor.GRAY + "Right-Click to get the link to");
		flore.add(ChatColor.GRAY + "our forums in chat!");
		ItemStack f;
		ItemMeta fm;
		f = new ItemStack(Material.ENCHANTED_BOOK);
		fm = f.getItemMeta();
		fm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&dForums &8(&7Right-Click&8)"));
		fm.setLore(flore);
		fm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		f.setItemMeta(fm);
		
		p.getInventory().clear();
		p.getInventory().setItem(0, main.s);
		p.getInventory().setItem(1, main.d);
		p.getInventory().setItem(7, f);
		p.getInventory().setItem(8, main.b);
		
		// Editing the Tablist player names
		Bukkit.getScheduler().runTaskLater(main, new Runnable() {
			public void run() {
				String format = "";
				format = main.getConfig().getString("tablistnames");
				format = main.replaceVault(p, format);
				format = format.replace("{PLAYER_NAME}", p.getDisplayName());
				format = ChatColor.translateAlternateColorCodes('&', format);
				p.setPlayerListName(format);
			}
		}, 20L);
		
		main.enabled.add(p);
		
	}
	
}
