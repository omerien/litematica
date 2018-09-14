package fi.dy.masa.litematica.gui.widgets;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.List;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.gui.GuiAreaSelectionManager;
import fi.dy.masa.litematica.gui.Icons;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetFileBrowserBase;

public class WidgetAreaSelectionBrowser extends WidgetFileBrowserBase
{
    protected static final FileFilter JSON_FILTER = new FileFilterJson();
    private final GuiAreaSelectionManager guiAreaSelectionManager;

    public WidgetAreaSelectionBrowser(int x, int y, int width, int height,
            GuiAreaSelectionManager parent, ISelectionListener<DirectoryEntry> selectionListener)
    {
        super(x, y, width, height, DataManager.getInstance(), parent.getBrowserContext(),
                parent.getDefaultDirectory(), selectionListener, Icons.DUMMY);

        this.browserEntryHeight = 22;
        this.guiAreaSelectionManager = parent;
    }

    public GuiAreaSelectionManager getSelectionManagerGui()
    {
        return this.guiAreaSelectionManager;
    }

    @Override
    protected void drawAdditionalContents(int mouseX, int mouseY)
    {
    }

    @Override
    protected File getRootDirectory()
    {
        return DataManager.ROOT_AREA_SELECTIONS_DIRECTORY;
    }

    @Override
    protected void addFileEntriesToList(File dir, List<DirectoryEntry> list)
    {
        for (File file : dir.listFiles(JSON_FILTER))
        {
            list.add(new DirectoryEntry(DirectoryEntryType.fromFile(file), dir, file.getName()));
        }

        Collections.sort(list);
    }

    @Override
    protected WidgetAreaSelectionEntry createListEntryWidget(int x, int y, boolean isOdd, DirectoryEntry entry)
    {
        return new WidgetAreaSelectionEntry(x, y, this.browserEntryWidth, this.getBrowserEntryHeightFor(entry), this.zLevel, isOdd,
                entry, this.guiAreaSelectionManager.getSelectionManager(), this.mc, this, this.iconProvider);
    }

    public static class FileFilterJson implements FileFilter
    {
        @Override
        public boolean accept(File pathName)
        {
            return pathName.getName().endsWith(".json");
        }
    }
}