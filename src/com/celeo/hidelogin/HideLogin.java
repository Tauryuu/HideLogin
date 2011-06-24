package com.celeo.hidelogin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class HideLogin extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public LoginListener loginListener = new LoginListener(this);
	
	@Override
	public void onDisable() {
		Util.saveAll();
		log.info("[HideLogin] <disabled>");
	}

	@Override
	public void onEnable() {
		Util.load(this);
		PluginManager mngr = getServer().getPluginManager();
		mngr.registerEvent(Event.Type.PLAYER_JOIN, this.loginListener, Event.Priority.Normal, this);
		log.info("[HideLogin] <enabled>");
	}
	
	public void checkAll(Player player) {
		if(Util.hideAll == true)
			player.sendMessage(Util.cred + "Note: " + Util.cgreen + "Login messages are turned completely off");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player)sender;
		if(commandLabel.equalsIgnoreCase("hiddenlogins") && player.isOp())
		{
			String list = "";
			for(String str : Util.hiddenPlayers)
			{
				list += str + " ";
			}
			player.sendMessage(ChatColor.AQUA + "Players who hide their logins:");
			player.sendMessage(ChatColor.AQUA + list);
			return true;
		}
		if(commandLabel.equalsIgnoreCase("hideme") && player.isOp())
		{
			Util.hiddenPlayers.add(player.getDisplayName());
			player.sendMessage(Util.cgreen + "You have been added to the hidden login list");
			checkAll(player);
			return true;
		}
		if(commandLabel.equalsIgnoreCase("showme") && player.isOp())
		{
			if(Util.hiddenPlayers.contains(player.getDisplayName()))
			{
				Util.hiddenPlayers.remove(player.getDisplayName());
				player.sendMessage(Util.cgreen + "You have been removed from the hidden login list");
				checkAll(player);
			}
			else
			{
				player.sendMessage(Util.cgreen + "Your login was not being hidden");
				checkAll(player);
			}
			return true;
		}
		if(commandLabel.equalsIgnoreCase("togglelogins") && player.isOp())
		{
			if(args.length >= 1)
			{
				if(args[0].equalsIgnoreCase("on"))
				{
					Util.hideAll = true;
					player.sendMessage(Util.pre + Util.cgreen + "login messages set to on");
				}
				else if(args[0].equalsIgnoreCase("off"))
				{
					Util.hideAll = false;
					player.sendMessage(Util.pre + Util.cgreen + "login messages set to off");
				}
				else
				{
					return false;
				}
			}
			else
			{
				if(Util.hideAll == true)
				{
					Util.hideAll = false;
					player.sendMessage(Util.pre + Util.cgreen + "login messages set to on");
				}
				else
				{
					Util.hideAll = true;
					player.sendMessage(Util.pre + Util.cgreen + "login messages set to off");
				}
			}
		}
		return true;
	}

}