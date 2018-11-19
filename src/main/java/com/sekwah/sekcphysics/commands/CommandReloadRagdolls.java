package com.sekwah.sekcphysics.commands;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollGenerator;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandReloadRagdolls extends CommandBase {
    
    @Override
    public String getName() {
        return "reload_ragdolls";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/reload_ragdolls";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        SekCPhysics.ragdolls.reset();
        new RagdollGenerator().loadRagdolls();
    }
    
}