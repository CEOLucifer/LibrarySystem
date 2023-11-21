package Panel;

import javax.swing.JButton;
import javax.swing.JPanel;

import Control.LTFPair;
import Layout.VLayout;
import Manager.DataBaseMgr;
import Manager.MessageManager;
import Utility.Action.EventCenter;

/**
 * 借阅记录查询面板
 */
public class BorrowQueryPanel extends JPanel
{
    private LTFPair p_ID = new LTFPair("用户ID");

    private JPanel btnsPanel = new JPanel();
    private JButton btnQuery = new JButton("查询");

    public BorrowQueryPanel()
    {
        setLayout(new VLayout());

        // #region 控件相关
        add(p_ID);

        btnsPanel.add(btnQuery);
        add(btnsPanel);

        btnQuery.addActionListener((e) ->
        {
            // 获取输入
            String readerID = p_ID.tf.getText();

            if (!DataBaseMgr.Instance.CheckReader(readerID))
            {
                MessageManager.Instance.PrintMessage("没有该用户的记录！");
                return;
            }

            // 执行
            DataBaseMgr.Instance.QueryBorrowRecord(readerID);

            EventCenter.Instance.EventTrigger("借阅记录查询完成");
        });
        // #endregion
    }
}
