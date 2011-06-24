package com.celeo.hidelogin;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

import java.util.ArrayList;

public class Util {
	
	public final HideLogin plugin;
		
	public Util(HideLogin instance) {
		plugin = instance;
	}
	
	public static ArrayList<String> hiddenPlayers = new ArrayList<String>();
	public static String pre = "[HideLogin] ";	
	public static Configuration config;
	public static ChatColor cyellow = ChatColor.YELLOW;
	public static ChatColor cgreen = ChatColor.GREEN;
	public static ChatColor cred = ChatColor.RED;
	public static boolean hideAll = false;
	
	public static void load(HideLogin plugin) {
		config = plugin.getConfiguration();
		try
		{
			hideAll = config.getBoolean("config.hideall", hideAll);
			hiddenPlayers = (ArrayList<String>) config.getProperty("hiddenplayers");
			if(hiddenPlayers == null)
			{
				hiddenPlayers = new ArrayList<String>();
				HideLogin.log.info(pre + "null");
			}
		}
		catch(Exception ex)
		{
			saveAll();
			HideLogin.log.info(pre + "Error with loading of hidden logins");
		}
		HideLogin.log.info(pre + "settings loaded");
	}
	
	public static void saveAll() {
		config.setProperty("hiddenplayers", hiddenPlayers);
		config.setProperty("config.hideall", hideAll);
		config.save();
		HideLogin.log.info(pre + "settings saved");
	}
	
}