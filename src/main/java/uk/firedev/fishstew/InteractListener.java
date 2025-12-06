package uk.firedev.fishstew;

import com.oheers.fish.competition.Competition;
import com.oheers.fish.competition.configs.CompetitionFile;
import org.bukkit.NamespacedKey;
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
