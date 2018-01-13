package com.infxnty.LobbyCore;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.infxnty.LobbyCore.Listeners.LobbyEnvironment;
import com.infxnty.LobbyCore.Listeners.LobbyJoin;
import com.infxnty.LobbyCore.Listeners.PlayerListener;

public class Main extends JavaPlugin implements Listener {
	
	// Arrays
	public ArrayList<Player> enabled = new ArrayList<>();
	
	// HashMaps
	public HashMap<Player, Long> cooldown = new HashMap<>();
	
	// ItemStacks
	public ItemStack d;
	public ItemMeta dm;
	public ItemStack doff;
	public ItemMeta doffm;
	public ItemStack s;
	public ItemMeta sm;
	public ItemStack b;
	public ItemMeta bm;
	
	// Booleans
	
	// GUIs
	String stitle = this.getConfig().getString("serverSelector");
	public Inventory serverGUI = 
			Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', stitle));
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		
		// Setting up Config
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		// Enable Message
		PrintStream sysout = System.out;
		sysout.println(ChatColor.translateAlternateColorCodes('&', "&7&m+--------------------------------------------+"));
		sysout.println(ChatColor.translateAlternateColorCodes('&', "&7          &aLobbyCore has been enabled!"));
		sysout.println(ChatColor.LIGHT_PURPLE + "               Coded by: Infxnty");
		sysout.println(ChatColor.translateAlternateColorCodes('&', "&7                 &bVersion: 1.0"));
		sysout.println(ChatColor.translateAlternateColorCodes('&', "&7&m+--------------------------------------------+"));
		
		// Registering Events
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new LobbyJoin(this), this);
		pm.registerEvents(new LobbyEnvironment(), this);
		pm.registerEvents(new PlayerListener(this), this);
		
		// Stopping Time Change
		Bukkit.getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
			public void run() {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule sendCommandFeedback false");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule doDaylightCycle false");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "time set 6000");
			}
		}, 100L);
		
	}
	
}
