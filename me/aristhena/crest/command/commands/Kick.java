package me.aristhena.crest.command.commands;

import me.aristhena.crest.command.*;
import net.minecraft.network.play.client.*;
import me.aristhena.utils.*;
import net.minecraft.network.*;

@Com(names = { "kick", "k" })
public class Kick extends Command
{
    @Override
    public void runCommand(final String[] args) {
        ClientUtils.packet(new C03PacketPlayer.C05PacketPlayerLook(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, false));
    }
    
    @Override
    public String getHelp() {
        return "Kick - kick <k> - Kicks you from most servers.";
    }
}
