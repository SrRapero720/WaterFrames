package me.srrapero720.waterframes.common.screen;

import me.srrapero720.waterframes.common.block.entity.DisplayTile;
import me.srrapero720.waterframes.common.screen.widgets.styles.WidgetStyles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.gui.style.display.StyleDisplay;
import team.creative.creativecore.common.gui.sync.GuiSyncLocal;

public abstract class DisplayScreen<T extends DisplayTile<?>> extends GuiLayer {
    protected final GuiSyncLocal<EndTag> playAction;
    protected final GuiSyncLocal<EndTag> pauseAction;
    protected final GuiSyncLocal<EndTag> stopAction;
    protected final GuiSyncLocal<EndTag> fastForward;
    protected final GuiSyncLocal<EndTag> fastBackwards;
    protected final GuiSyncLocal<CompoundTag> syncAction;

    protected final T tileBlock;
    protected final float scale = 1F / 16;
    protected DisplayScreen(String name, T block, int width, int height) {
        super(name, width, height);
        this.align = Align.STRETCH;
        this.flow = GuiFlow.STACK_Y;
        this.tileBlock = block;

        this.playAction = getSyncHolder().register("play", x -> tileBlock.play());
        this.pauseAction = getSyncHolder().register("pause", x -> tileBlock.pause());
        this.stopAction = getSyncHolder().register("stop", x -> tileBlock.stop());
        this.fastForward = getSyncHolder().register("forward", x -> tileBlock.fastForward());
        this.fastBackwards = getSyncHolder().register("backwards", x -> tileBlock.fastBackwards());
        this.syncAction = getSyncHolder().register("sync", nbt -> syncData(tileBlock, getPlayer(), nbt));
    }

    @Override
    public void create() {
        onCreate();
    }

    protected abstract void onCreate();
    protected abstract void syncData(T tileBlock, Player player, CompoundTag tag);

    @Override
    @OnlyIn(Dist.CLIENT)
    public StyleDisplay getBackground(GuiStyle style, StyleDisplay display) { return WidgetStyles.BACKGROUND_COLOR; }

    @Override
    @OnlyIn(Dist.CLIENT)
    public StyleDisplay getBorder(GuiStyle style, StyleDisplay display) { return WidgetStyles.BACKGROUND_BORDER; }
}