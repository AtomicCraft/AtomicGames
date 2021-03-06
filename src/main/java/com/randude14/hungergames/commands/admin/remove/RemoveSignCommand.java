package com.randude14.hungergames.commands.admin.remove;

import com.randude14.hungergames.Defaults.Commands;
import com.randude14.hungergames.Defaults.Perm;
import com.randude14.hungergames.commands.PlayerCommand;
import com.randude14.hungergames.listeners.SessionListener;
import com.randude14.hungergames.utils.ChatUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoveSignCommand extends PlayerCommand {

	public RemoveSignCommand() {
		super(Perm.ADMIN_REMOVE_SIGN, Commands.ADMIN_REMOVE_HELP.getCommand(), "sign");
	}

	@Override
	public void handlePlayer(Player player, String cmd, String[] args) {
		SessionListener.addSession(SessionListener.SessionType.SIGN_REMOVER, player, "");
		ChatUtils.send(player, ChatColor.GREEN, "Hit a sign to remove it. If you do not hit a sign, nothing will happen.");
	}

	@Override
	public String getInfo() {
		return "remove a sign or an info wall that contains the sign";
	}

	@Override
	public String getUsage() {
		return "/%s remove sign";
	}
	
}
