package Layout;

import java.awt.Component;
import java.awt.Container;

import Utility.GUI.LayoutAdapter;

/**
 * 垂直布局管理器
 */
public class VLayout extends LayoutAdapter
{
    @Override
    public void layoutContainer(Container parent)
    {
        int width = parent.getWidth();
        int height = parent.getHeight();

        Component[] components = parent.getComponents();
        int unit = (int) ((float) height / components.length);
        for (int i = 0; i < components.length; ++i)
        {
            components[i].setBounds(0, i * unit, width, unit);
        }
    }
}
