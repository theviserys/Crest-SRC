package me.aristhena.crest.command.commands;

import me.aristhena.crest.command.*;
import me.aristhena.utils.*;
import me.aristhena.crest.option.types.*;
import me.aristhena.crest.module.*;
import me.aristhena.crest.option.*;

public class OptionCommand extends Command
{
    @Override
    public void runCommand(final String[] args) {
        if (args.length < 2) {
            ClientUtils.sendMessage(getHelpString());
            return;
        }
        final Module mod = ModuleManager.getModule(args[0]);
        if (!mod.getId().equalsIgnoreCase("Null")) {
            final Option option = OptionManager.getOption(args[1], mod.getId());
            if (option instanceof BooleanOption) {
                final BooleanOption booleanOption = (BooleanOption)option;
                booleanOption.setValue(!booleanOption.getValue());
                ClientUtils.sendMessage(String.valueOf(option.getDisplayName()) + " set to " + option.getValue());
                OptionManager.save();
            }
            else if (option instanceof NumberOption) {
                try {
                    option.setValue(Double.parseDouble(args[2]));
                    ClientUtils.sendMessage(String.valueOf(option.getDisplayName()) + " set to " + args[2]);
                }
                catch (NumberFormatException e) {
                    ClientUtils.sendMessage("Number option format error.");
                }
                OptionManager.save();
            }
            else {
                ClientUtils.sendMessage("Option not recognized.");
            }
        }
        else {
            ClientUtils.sendMessage(getHelpString());
        }
    }
    
    public static String getHelpString() {
        return "Set option - (modname) (option name) <value>";
    }
}
