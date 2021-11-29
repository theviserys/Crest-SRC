package me.aristhena.crest.command.commands;

import me.aristhena.crest.command.*;
import net.minecraft.network.play.client.*;
import me.aristhena.utils.*;
import net.minecraft.network.*;

@Com(names = { "ncp", "testncp" })
public class Ncp extends Command
{
    @Override
    public void runCommand(final String[] args) {
        if (args.length > 1) {
            ClientUtils.packet(new C01PacketChatMessage("/testncp input"));
            ClientUtils.packet(new C01PacketChatMessage("/testncp input " + args[1]));
        }
        else {
            ClientUtils.sendMessage(this.getHelp());
        }
    }
    
    @Override
    public String getHelp() {
        return "Ncp - ncp <testncp> (name) - Sets a player as a testncp target.";
    }
}
