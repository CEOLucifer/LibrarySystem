package Utility.GameCore;

import java.util.ArrayList;

import Utility.Action.Action;
import Utility.Action.IAction;

// 游戏核心模块。主要用于游戏主循环
public class GameCore
{
    public static GameCore Instance = new GameCore();
    public ArrayList<Action> list = new ArrayList<>(); // 要帧更新的所有委托

    private GameCore()
    {
    }

    // 开启死循环
    public void Start()
    {
        Long t0 = 0L;
        Long t1 = System.currentTimeMillis();
        while (true)
        {
            t0 = t1;

            // 主逻辑
            for (Action each : list)
            {
                each.Invoke();
            }

            t1 = System.currentTimeMillis();
            Time.deltaTime = (t1 - t0) / 1000.0f;
        }
    }

    // 添加要帧更新的方法
    public void AddMethodToUpdate(Action a)
    {
        list.add(a);
    }

    // 添加要帧更新的方法
    public void AddMethodToUpdate(IAction a)
    {
        list.add(new Action(a));
    }

    // 删除要帧更新的方法
    public void RemoveMethodToUpdate(Action a)
    {
        list.remove(a);
    }

    // 清除所有要帧更新的方法
    public void Clear()
    {
        list.clear();
    }
}
