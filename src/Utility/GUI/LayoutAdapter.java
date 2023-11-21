package Utility.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

/*
 * 自定义布局管理器适配器。适配是一种设计技巧，当一个接口有太多方法，但是只需要书写其中几个重要的方法的逻辑时，
 * 我们可以用适配器来屏蔽其他不需要的方法
 */
public abstract class LayoutAdapter implements LayoutManager2
{

    @Override
    public void addLayoutComponent(Component comp, Object constraints)
    {

    }

    @Override
    public void removeLayoutComponent(Component comp)
    {

    }

    @Override
    public void layoutContainer(Container parent)
    {

    }

    // 把不需要的方法都默认实现，遮蔽掉
    @Override
    public float getLayoutAlignmentX(Container target)
    {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target)
    {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target)
    {

    }

    @Override
    public Dimension maximumLayoutSize(Container target)
    {
        return null;
    }

    @Override
    public void addLayoutComponent(String name, Component comp)
    {
    }

    @Override
    public Dimension minimumLayoutSize(Container parent)
    {
        return null;
    }

    @Override
    public Dimension preferredLayoutSize(Container parent)
    {
        return null;
    }

}
