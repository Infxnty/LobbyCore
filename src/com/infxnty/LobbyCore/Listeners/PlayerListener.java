package com.infxnty.LobbyCore.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.infxnty.LobbyCore.Main;

public class PlayerListener implements Listener {
	
	private Main main;
	public PlayerListener(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		String prefix = main.getConfig().getString("prefix");
		
		List<String> hlore = new ArrayList<>();
		hlore.add(ChatColor.GRAY + "Click to connect to HCF!");
		ItemStack h;
		ItemMeta hm;
		h = new ItemStack(Material.DIAMOND_SWORD);
		hm = h.getItemMeta();
		hm.setDisplayName(ChatColor.AQUA + "HCF");
		hm.setLore(hlore);
		hm.addEnchant(Enchantment.DURABILITY, 10, true);
		hm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		h.setItemMeta(hm);
		main.serverGUI.setItem(2, h);
		
		List<String> ulore = new ArrayList<>();
		ulore.add(ChatColor.GRAY + "Click to connect to UHC!");
		ItemStack u;
		ItemMeta um;
		u = new ItemStack(Material.GOLDEN_APPLE);
		um = u.getItemMeta();
		um.setDisplayName(ChatColor.AQUA + "UHC");
		um.setLore(ulore);
		um.addEnchant(Enchantment.DURABILITY, 10, true);
		um.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		u.setItemMeta(um);
		main.serverGUI.setItem(4, u);
		
		List<String> blore = new ArrayList<>();
		blore.add(ChatColor.GRAY + "Click to connect to Practice PvP!");
		ItemStack b;
		ItemMeta bm;
		b = new ItemStack(Material.BOW);
		bm = b.getItemMeta();
		bm.setDisplayName(ChatColor.AQUA + "Practice PvP");
		bm.setLore(blore);
		bm.addEnchant(Enchantment.DURABILITY, 10, true);
		bm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		b.setItemMeta(bm);
		main.serverGUI.setItem(6, b);
		
		List<String> dlore = new ArrayList<>();
		dlore.add(ChatColor.GRAY + "Right-Click to toggle player");
		dlore.add(ChatColor.GRAY + "visibility!");
		main.doff = new ItemStack(351, 1, (short) 8);
		main.doffm = main.doff.getItemMeta();
		main.doffm.setLore(dlore);
		main.doffm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		main.doffm.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Player Visibility &8» &cOff"));
		main.doff.setItemMeta(main.doffm);
		
		if (p.getItemInHand().getType().equals(Material.NETHER_STAR)) {
			p.openInventory(main.serverGUI);
		}
		
		if (p.getItemInHand().getType().equals(Material.BOOK)) {
			p.performCommand("buy");
		}
		
		if (main.cooldown.containsKey(p) && main.cooldown.get(p) > System.currentTimeMillis()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7You must wait &c5s &7between uses!"));
		} else if (p.getItemInHand().getType().equals(Material.INK_SACK)) {
			if (main.enabled.contains(p)) { // Can see players
				for (Player player : Bukkit.getOnlinePlayers()) {
					p.hidePlayer(player);
				}
				main.cooldown.put(p, System.currentTimeMillis() + (5 * 1000));
				main.enabled.remove(p);
				
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Player visibility disabled!"));
				
				p.getInventory().setItem(8, main.doff);
				
			} else { // Can not see players
				for (Player player : Bukkit.getOnlinePlayers()) {
					p.showPlayer(player);
				}
				main.cooldown.put(p, System.currentTimeMillis() + (5 * 1000));
				main.enabled.add(p);
				
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Player visibility enabled!"));
				
				p.getInventory().setItem(8, main.d);
				
			}
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		e.setCancelled(true);
		
	}
	
}
