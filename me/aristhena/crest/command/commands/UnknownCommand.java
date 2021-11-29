package me.aristhena.crest.command.commands;

import me.aristhena.crest.command.*;
import me.aristhena.utils.*;

@Com(names = { "" })
public class UnknownCommand extends Command
{
    @Override
    public void runCommand(final String[] args) {
        ClientUtils.sendMessage("Unknown Command. Type \"help\" for a list of commands.");
    }
}
