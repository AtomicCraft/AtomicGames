package com.randude14.hungergames.listeners;

import com.randude14.hungergames.Config;
import com.randude14.hungergames.GameManager;
import com.randude14.hungergames.Plugin;
import com.randude14.hungergames.games.HungerGame;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;


public class ChatListener implements Listener {

	@EventHandler
	public void playerChat(PlayerChatEvent event) {
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		if (!Plugin.getSponsors().containsKey(player.getName())) return;

		int choice = 0;
		event.setCancelled(true);
		String mess = event.getMessage();
		String sponsor = Plugin.removeSponsor(player);
		try {
			choice = Integer.parseInt(mess) - 1;
		} catch (Exception ex) {
			Plugin.error(player, "'%s' is not an integer.", mess);
			return;
		}

		Player beingSponsored = Bukkit.getPlayer(sponsor);
		if (beingSponsored == null) {
			Plugin.error(player, "'%s' is not online anymore.", sponsor);
			return;
		}
		HungerGame game = GameManager.getSession(player);
		if (game == null) {
			Plugin.error(player, "'%s' is no longer in a game.", sponsor);
			return;
		}
		Map<ItemStack, Double> itemMap = Config.getAllSponsorLootWithGlobal(game.getItemSets());

		int size = itemMap.size();
		if (choice < 0 || choice >= size) {
			Plugin.error(player, "Choice '%d' does not exist.");
			return;
		}

		ItemStack item = new ArrayList<ItemStack>(itemMap.keySet()).get(choice);
		double price = itemMap.get(item);
		if (!Plugin.hasEnough(beingSponsored, price)) {
			Plugin.error(player, String.format("You do not have enough money."));
			return;
		}
		Plugin.withdraw(player, price);
		if (item.getEnchantments().isEmpty()) {
			Plugin.send(beingSponsored, "%s has sponsored you %d %s(s).",
					player.getName(), item.getAmount(), item.getType().name());
		} else {
			Plugin.send(beingSponsored, "%s has sponsored you %d enchanted %s(s).",
					player.getName(), item.getAmount(), item.getType().name());
		}

		for (ItemStack drop : beingSponsored.getInventory().addItem(item)
				.values()) {
			beingSponsored.getWorld().dropItem(beingSponsored.getLocation(),
					drop);
		}
		if (item.getEnchantments().isEmpty()) {
			Plugin.send(beingSponsored, "You have sponsored %s %d %s(s) for $%.2f.",
					player.getName(), item.getAmount(), item.getType().name(),
					price);
		} else {
			Plugin.send(beingSponsored,
					"You have sponsored %s %d enchanted %s(s) for $%.2f.",
					player.getName(), item.getAmount(), item.getType().name(),
					price);
		}

	}
}
