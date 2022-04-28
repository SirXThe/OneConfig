package io.polyfrost.oneconfig.gui.elements.config;

import io.polyfrost.oneconfig.config.OneConfigConfig;
import io.polyfrost.oneconfig.config.data.OptionPage;
import io.polyfrost.oneconfig.config.interfaces.BasicOption;
import io.polyfrost.oneconfig.gui.OneConfigGui;
import io.polyfrost.oneconfig.gui.pages.ModConfigPage;
import io.polyfrost.oneconfig.lwjgl.RenderManager;
import io.polyfrost.oneconfig.lwjgl.font.Fonts;
import io.polyfrost.oneconfig.utils.ColorUtils;
import io.polyfrost.oneconfig.utils.InputUtils;
import org.lwjgl.nanovg.NanoVG;

import java.lang.reflect.Field;

public class ConfigPage extends BasicOption {
    public final OptionPage page;
    public final String description;
    private int backgroundColor = OneConfigConfig.GRAY_500;

    public ConfigPage(Field field, String name, String description, int size, OptionPage page) {
        super(field, name, size);
        this.description = description;
        this.page = page;
    }

    @Override
    public void draw(long vg, int x, int y) {
        int height = description.equals("") ? 64 : 96;
        NanoVG.nvgScissor(vg, x - 2, y, 1024, height);
        boolean hovered = InputUtils.isAreaHovered(x - 2, y, 1024, height);
        boolean clicked = InputUtils.isAreaClicked(x - 2, y, 1024, height);
        backgroundColor = ColorUtils.smoothColor(backgroundColor, OneConfigConfig.GRAY_500, OneConfigConfig.GRAY_400, hovered, 100);

        if (clicked) NanoVG.nvgGlobalAlpha(vg, 0.8f);

        RenderManager.drawRoundedRect(vg, x - 2, y, 1024, height, backgroundColor, 20);
        RenderManager.drawString(vg, name, x + 24, y + 32, OneConfigConfig.WHITE, 24, Fonts.INTER_MEDIUM);
        if (!description.equals(""))
            RenderManager.drawString(vg, name, x + 24, y + 70, OneConfigConfig.WHITE, 14, Fonts.INTER_MEDIUM);
        RenderManager.drawImage(vg, "/assets/oneconfig/textures/arrow.png", x + 995f, y + (description.equals("") ? 20f : 36f), 13, 22);

        if (clicked) OneConfigGui.INSTANCE.openPage(new ModConfigPage(page));
        NanoVG.nvgGlobalAlpha(vg, 1f);
        NanoVG.nvgResetScissor(vg);
    }

    @Override
    public int getHeight() {
        return description.equals("") ? 64 : 96;
    }

    @Override
    public boolean hasHalfSize() {
        return false;
    }
}
