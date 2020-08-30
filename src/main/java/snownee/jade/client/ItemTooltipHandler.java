package snownee.jade.client;

import mezz.jei.util.StackHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.TagRegistry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.text.MessageFormat;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused")
public class ItemTooltipHandler {

    private static final ItemTooltipHandler INSTANCE = new ItemTooltipHandler();

    public static ItemTooltipHandler getInstance() {
        return INSTANCE;
    }

    private ItemTooltipHandler() {
    }

    private static void addFormatted(ItemTooltipEvent event, String format, Object... objects) {
        event.getToolTip().add(new StringTextComponent(MessageFormat.format(format, objects)).mergeStyle(TextFormatting.DARK_AQUA));
    }

    private static void addCategory(ItemTooltipEvent event, String category) {
        event.getToolTip().add(new StringTextComponent(category + ":").mergeStyle(TextFormatting.DARK_AQUA));
    }

    private static void addParameter(ItemTooltipEvent event, String key, Object value) {
        event.getToolTip().add(new StringTextComponent(" - " + key + ": " + value).mergeStyle(TextFormatting.DARK_AQUA));
    }

    @SubscribeEvent
    public void addRegistryInfo(ItemTooltipEvent event) {
        if (Screen.hasShiftDown()) {
            final Item item = event.getItemStack().getItem();
            addCategory(event, "Registry");
            addParameter(event, "id", item.getRegistryName());
        } else {
            event.getToolTip().add(
                new StringTextComponent("Hold ").mergeStyle(TextFormatting.GRAY)
                .append(new StringTextComponent("Shift").mergeStyle(TextFormatting.DARK_AQUA))
                .append(new StringTextComponent(" for Registry Info").mergeStyle(TextFormatting.GRAY))
            );
        }
    }

    @SubscribeEvent
    public void addNbtInfo(ItemTooltipEvent event) {
        if (Screen.hasShiftDown()) {
            addCategory(event, "NBT");
            if (event.getItemStack().hasTag()) {
                final CompoundNBT tag = event.getItemStack().getTag();
                for (String key : tag.keySet()) {
                    addParameter(event, key, tag.get(key));
                }
            } else {
                addFormatted(event, " - No NBT Tags");
            }
        } else {
            event.getToolTip().add(
                new StringTextComponent("Hold ").mergeStyle(TextFormatting.GRAY)
                    .append(new StringTextComponent("Shift").mergeStyle(TextFormatting.DARK_AQUA))
                    .append(new StringTextComponent(" for NBT Info").mergeStyle(TextFormatting.GRAY))
            );
        }
    }

    @SubscribeEvent
    public void addOreDictInfo(ItemTooltipEvent event) {
        //addCategory(event, "Tags");
        //final int[] ids = OreDictionary.getOreIDs(event.getItemStack());
        //for (int id : ids) {
        //    addFormatted(event, " - {1} ({0})", id, OreDictionary.getOreName(id));
        //}
        //if (ids.length == 0) {
        //    addFormatted(event, " - No OreDict Entries");
        //}
    }

}
