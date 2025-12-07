package org.evenmorefish.fishstew.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.evenmorefish.fishstew.FishStewPlugin;
import org.evenmorefish.fishstew.config.MessageConfig;
import org.evenmorefish.fishstew.item.FishStewItem;

// Safe to suppress as the same API is stable in modern Paper.
@SuppressWarnings("UnstableApiUsage")
public class MainCommand {

    public static @NotNull LiteralCommandNode<CommandSourceStack> get() {
        return Commands.literal("fishstew")
            .requires(stack -> stack.getSender().hasPermission("fishstew.command"))
            .then(give())
            .then(reload())
            .build();
    }

    private static LiteralArgumentBuilder<CommandSourceStack> give() {
        return Commands.literal("give")
            .then(
                Commands.argument("item", new FishStewArgument())
                    // no player argument - only players can execute
                    .executes(ctx -> {
                        CommandSender sender = ctx.getSource().getSender();
                        if (!(sender instanceof Player player)) {
                            sender.sendMessage(Component.text("Only players can use this command.").color(NamedTextColor.RED));
                            return 1;
                        }
                        FishStewItem item = ctx.getArgument("item", FishStewItem.class);
                        item.give(player);
                        MessageConfig.getInstance().getStewReceived().send(player);
                        return 1;
                    })
                    // player argument - anyone can execute
                    .then(
                        Commands.argument("target", ArgumentTypes.player())
                            .executes(ctx -> {
                                CommandSender sender = ctx.getSource().getSender();
                                Player target = ctx.getArgument("target", Player.class);
                                FishStewItem item = ctx.getArgument("item", FishStewItem.class);
                                item.give(target);
                                MessageConfig.getInstance().getStewReceived().send(target);
                                if (!sender.equals(target)) {
                                    MessageConfig.getInstance().getStewGiven(target).send(sender);
                                }
                                return 1;
                            })
                    )
            );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> reload() {
        return Commands.literal("reload")
            .executes(ctx -> {
                FishStewPlugin.getInstance().reload();
                MessageConfig.getInstance().getReloaded().send(ctx.getSource().getSender());
                return 1;
            });
    }

}
