package com.sekwah.sekcphysics.commands;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollGenerator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandRagdolls extends CommandBase {

    private List<String> subcommands = Arrays.asList(new String[]{"reload", "remove"});
    
    @Override
    public String getName() {
        return "ragdoll";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/ragdoll";
    }

    @Override
    public List<String> getAliases()
    {
        return Arrays.asList(new String[]{"ragdolls"});
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("reload")) {
                SekCPhysics.ragdolls.reset();
                new RagdollGenerator().loadRagdolls();
                sender.sendMessage(new TextComponentString("Reloaded ragdolls"));
            }
            else if(args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("clear")) {
                SekCPhysics.ragdolls.ragdolls.clear();
                sender.sendMessage(new TextComponentString("Removed ragdolls"));
            }
            else if(args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(new TextComponentString("Subcommands: clear, help, reload, remove"));
            }
            else {
                sender.sendMessage(new TextComponentString("Invalid subcommand"));
            }
        }
        else {
            sender.sendMessage(new TextComponentString("Please specify subcommand: remove, reload"));
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 0)
        {
            return getListOfStringsMatchingLastWord(args, subcommands);
        }
        else
        {
            return Collections.<String>emptyList();
        }
    }
    
}