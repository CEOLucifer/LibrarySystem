package Utility.Action;

import java.util.ArrayList;

// 委托
public class Action
{
    private ArrayList<IAction> list = new ArrayList<>();

    public Action()
    {
    }

    public Action(IAction a)
    {
        list.add(a);
    }

    // 添加单个方法
    public void Add(IAction a)
    {
        list.add(a);
    }

    // 添加了另一个委托变量
    public void Add(Action a)
    {
        for (IAction each : a.list)
        {
            list.add(each);
        }
    }

    // 移除单个方法
    public void Remove(IAction a)
    {
        list.remove(a);
    }

    // 执行所有方法
    public void Invoke()
    {
        for (IAction each : list)
        {
            each.ActionPerform();
        }
    }

    // 清除所有方法
    public void Clear()
    {
        list.clear();
    }
}
