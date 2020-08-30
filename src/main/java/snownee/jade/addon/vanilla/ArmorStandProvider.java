package snownee.jade.addon.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import snownee.jade.JadePlugin;
import snownee.jade.Renderables;

public class ArmorStandProvider implements IEntityComponentProvider {

    public static final ArmorStandProvider INSTANCE = new ArmorStandProvider();

    @Override
    public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (!config.get(JadePlugin.ARMOR_STAND)) return;

        ArmorStandEntity entity = (ArmorStandEntity) accessor.getEntity();
        entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            .ifPresent(itemHandler -> {
                for (int i = itemHandler.getSlots() - 1; i >= 0; i--) {
                    ItemStack stack = itemHandler.getStackInSlot(i);
                    if (!stack.isEmpty())
                        tooltip.add(Renderables.of(Renderables.item(stack), Renderables.offsetText(stack.getDisplayName(), 0, 4)));
                }
            });
    }

}
