package snownee.jade.addon.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import snownee.jade.JadePlugin;

public class BreedingProvider implements IEntityComponentProvider, IServerDataProvider<Entity> {
    public static final BreedingProvider INSTANCE = new BreedingProvider();

    @Override
    public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (!config.get(JadePlugin.MOB_BREEDING)) {
            return;
        }

        if (accessor.getServerData().contains("BreedingCD", Constants.NBT.TAG_INT)) {
            int time = accessor.getServerData().getInt("BreedingCD");
            if (time > 0) {
                tooltip.add(new TranslationTextComponent("jade.mobbreeding.time", time / 20));
            }
        }

        if (accessor.getServerData().contains("InLoveCD", Constants.NBT.TAG_INT)) {
            int inLoveCD = accessor.getServerData().getInt("InLoveCD");
            if (inLoveCD > 0) {
                tooltip.add(new TranslationTextComponent("jade.mobbreeding.inlove", inLoveCD / 20));
            }
        }
    }

    @Override
    public void appendServerData(CompoundNBT tag, ServerPlayerEntity player, World world, Entity entity) {
        int breedingCD = ((AnimalEntity) entity).getGrowingAge();
        if (breedingCD > 0) {
            tag.putInt("BreedingCD", breedingCD);
        }

        int inLoveCD = ((AnimalEntity) entity).inLove;
        if (inLoveCD > 0) {
            tag.putInt("InLoveCD", inLoveCD);
        }
    }
}
