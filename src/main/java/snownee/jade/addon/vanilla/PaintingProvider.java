package snownee.jade.addon.vanilla;

import java.util.List;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import snownee.jade.JadePlugin;

public class PaintingProvider implements IEntityComponentProvider {
    public static final PaintingProvider INSTANCE = new PaintingProvider();

    @Override
    public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (!config.get(JadePlugin.PAINTING)) {
            return;
        }
        PaintingEntity painting = (PaintingEntity) accessor.getEntity();
        ResourceLocation resourceLocation = painting.art.getRegistryName();
        if (resourceLocation != null) {
            String name = resourceLocation.getPath().replace('_', ' ');
            tooltip.add(new StringTextComponent(name));
        }
    }

    @Override
    public void appendTail(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (config.get(JadePlugin.HIDE_MOD_NAME) || !config.get(JadePlugin.PAINTING)) {
            return;
        }
        PaintingEntity painting = (PaintingEntity) accessor.getEntity();
        ResourceLocation resourceLocation = painting.art.getRegistryName();
        if (resourceLocation != null) {
            String modid = resourceLocation.getNamespace();
            if (modid.equals("minecraft")) return;
            tooltip.clear();
            tooltip.add(new StringTextComponent(String.format(Waila.CONFIG.get().getFormatting().getModName(), ModIdentification.getModInfo(modid).getName())));
        }
    }
}
