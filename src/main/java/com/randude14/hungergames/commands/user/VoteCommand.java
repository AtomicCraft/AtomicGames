package com.randude14.hungergames.commands.user;

import com.randude14.hungergames.Defaults.Commands;
import com.randude14.hungergames.Defaults.Perm;
import com.randude14.hungergames.GameManager;
import com.randude14.hungergames.HungerGames;
import com.randude14.hungergames.commands.PlayerCommand;
import com.randude14.hungergames.utils.ChatUtils;

import org.bukkit.entity.Player;

public class VoteCommand extends PlayerCommand {

	public VoteCommand() {
		super(Perm.USER_VOTE, "vote", USER_COMMAND);
	}

	@Override
	public void handlePlayer(Player player, String cmd, String[] args) {
		game = GameManager.INSTANCE.getRawSession(player);
		if (game == null) {
			ChatUtils.error(player, "You must be in a game to vote. You can a game join by '" + Commands.USER_JOIN.getCommand().getUsage() + "'", HungerGames.CMD_USER);
			return;
		}
		game.addReadyPlayer(player);
		ChatUtils.send(player, "You have voted that you are ready.");
	}

	@Override
	public String getInfo() {
		return "cast your vote that you are ready to play";
	}

	@Override
	public String getUsage() {
		return "/%s vote";
	}
    
}
