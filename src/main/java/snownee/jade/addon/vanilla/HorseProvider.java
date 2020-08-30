package snownee.jade.addon.vanilla;

import java.text.DecimalFormat;
import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import snownee.jade.JadePlugin;

public class HorseProvider implements IEntityComponentProvider {
    public static final HorseProvider INSTANCE = new HorseProvider();
    private static final DecimalFormat dfCommas = new DecimalFormat("##.##");

    @Override
    public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (!config.get(JadePlugin.HORSE_STAT)) {
            return;
        }
        AbstractHorseEntity horse = (AbstractHorseEntity) accessor.getEntity();
        double jumpStrength = horse.getHorseJumpStrength();
        double jumpHeight = -0.1817584952 * jumpStrength * jumpStrength * jumpStrength + 3.689713992 * jumpStrength * jumpStrength + 2.128599134 * jumpStrength - 0.343930367;
        ModifiableAttributeInstance iattributeinstance = horse.getAttribute(Attributes.MOVEMENT_SPEED);
        tooltip.add(new TranslationTextComponent("jade.horseStat.jump", dfCommas.format(jumpHeight)));
        if (iattributeinstance != null) {
            tooltip.add(new TranslationTextComponent("jade.horseStat.speed", dfCommas.format(iattributeinstance.getValue())));
        }
    }
}
