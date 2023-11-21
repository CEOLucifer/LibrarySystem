package Manager;

/**
 * 数据传递类。用于事件中心的数据传递
 */
public class BridgeMgr
{
    public static BridgeMgr Instance = new BridgeMgr();
    public int row;

    private BridgeMgr()
    {
    }
}
