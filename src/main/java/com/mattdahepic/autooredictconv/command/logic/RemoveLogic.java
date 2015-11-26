package com.mattdahepic.autooredictconv.command.logic;

import com.mattdahepic.autooredictconv.config.ConversionsConfig;
import com.mattdahepic.mdecore.command.ICommandLogic;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveLogic implements ICommandLogic {
    public static final String USAGE = "/odc remove <ore name>";
    public static RemoveLogic instance = new RemoveLogic();

    public String getCommandName () {
        return "remove";
    }
    public int getPermissionLevel () {
        return 2;
    }
    public String getCommandSyntax () {
        return USAGE;
    }
    public void handleCommand (ICommandSender sender, String[] args) {
        ItemStack item = ((EntityPlayer) sender).getHeldItem();
        if (item != null) {
            int[] oreIDs = OreDictionary.getOreIDs(item);
            List<String> oreDictNames = new ArrayList<String>();
            for (int id : oreIDs) oreDictNames.add(OreDictionary.getOreName(id));
            for (String name : oreDictNames) {
                if (ConversionsConfig.conversions.containsKey(name)) {
                    if (ConversionsConfig.remove(name)) {
                        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Successfully removed conversion for "+EnumChatFormatting.AQUA+name+EnumChatFormatting.GREEN+"!"));
                        continue;
                    }
                }
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Did not remove conversion for "+EnumChatFormatting.AQUA+name+EnumChatFormatting.RED+"!"));
            }
        } else if (args.length == 2) {
            String name = args[1];
            if (ConversionsConfig.conversions.containsKey(name)) {
                if (ConversionsConfig.remove(name)) {
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Successfully removed conversion for "+EnumChatFormatting.AQUA+name+EnumChatFormatting.GREEN+"!"));
                }
            }
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Did not remove conversion for "+EnumChatFormatting.AQUA+name+EnumChatFormatting.RED+"!"));
        } else {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"You\'re not holding an item and didn't specify a name to remove!"));
        }
    }
    public List<String> addTabCompletionOptions (ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 2 ? Arrays.asList((String[]) ConversionsConfig.conversions.keySet().toArray()) : null;
    }
}
