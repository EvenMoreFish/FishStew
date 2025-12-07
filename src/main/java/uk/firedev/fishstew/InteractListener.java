package uk.firedev.fishstew;

import com.oheers.fish.EvenMoreFish;
import com.oheers.fish.FishUtils;
import com.oheers.fish.competition.Competition;
import com.oheers.fish.competition.configs.CompetitionFile;
import com.oheers.fish.messages.ConfigMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import uk.firedev.fishstew.item.FishStewItem;
import uk.firedev.fishstew.item.FishStewRegistry;
import uk.firedev.fishstew.utils.Keys;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!EquipmentSlot.HAND.equals(event.getHand())) {
            return;
        }
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }
        String id = item.getPersistentDataContainer().get(Keys.STEW_ID, PersistentDataType.STRING);
        if (id == null) {
            return;
        }
        FishStewItem fishStew = FishStewRegistry.getInstance().get(id);
        if (fishStew == null) {
            player.sendPlainMessage("The fish stew you tried to use is no longer configured.");
            return;
        }
        if (Competition.isActive()) {
            player.sendPlainMessage("There is an active competition! Please wait until it is over.");
            return;
        }
        CompetitionFile file = fishStew.getCompFile();
        Competition competition = new Competition(file);

        // Optionally respect the competition player requirement
        if (fishStew.shouldRespectMinimumPlayers() && !competition.isPlayerRequirementMet()) {
            player.sendPlainMessage("There are not enough players online to start the competition.");
            return;
        }

        competition.setAdminStarted(true);
        competition.begin();
        
        int newAmount = item.getAmount() - 1;
        if (newAmount > 0) {
            player.getInventory().setItemInMainHand(item.asQuantity(newAmount));
        } else {
            player.getInventory().setItemInMainHand(null);
        }
    }

}
