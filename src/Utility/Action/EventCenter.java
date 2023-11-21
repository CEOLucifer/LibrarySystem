package Utility.Action;

import java.util.HashMap;

// 事件中心模块
public class EventCenter
{
    public static EventCenter Instance = new EventCenter();
    private HashMap<String, Action> actionMap = new HashMap<>();

    private EventCenter()
    {
    }

    // 添加事件监听函数
    public void AddListener(String eventName, IAction a)
    {
        // 如果有该键
        if (actionMap.containsKey(eventName))
        {
            actionMap.get(eventName).Add(a);
        } else // 没有该键
        {
            actionMap.put(eventName, new Action(a));
        }
    }

    // 移除事件监听函数
    public void RemoveListener(String eventName, IAction a)
    {
        if (actionMap.containsKey(eventName))
        {
            actionMap.get(eventName).Remove(a);
        }
    }

    // 触发事件
    public void EventTrigger(String eventName)
    {
        if (actionMap.containsKey(eventName))
        {
            actionMap.get(eventName).Invoke();
        }
    }

    // 清除所有事件监听函数
    public void Clear()
    {
        actionMap.clear();
    }

}
