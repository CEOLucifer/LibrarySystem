package Panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import Manager.BridgeMgr;
import Utility.Action.EventCenter;

import Manager.MessageManager;

/**
 * 消息框
 */
public class MessagePanel extends JPanel
{
    private JTextArea ta = new JTextArea();
    private JScrollPane sp = new JScrollPane(ta);

    public MessagePanel()
    {
        setPreferredSize(new Dimension(0, 200));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("系统消息"));

        MessageManager.Instance.setMessagePanel(this);

        // 组件相关
        add(sp);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ta.setEditable(false);

        // #region 事件监听
        EventCenter.Instance.AddListener("删除Books成功", () ->
        {
            PrintMessage("删除成功，删除的元组数：" + BridgeMgr.Instance.row);
        });
        EventCenter.Instance.AddListener("借阅记录查询完成", () ->
        {
            PrintMessage("借阅记录查询完成");
        });
        // #endregion
    }

    // 输出当前时间
    public void PrintCurrentTime()
    {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ta.append(dateTime.format(formatter) + "\n");
    }

    // 输出消息
    public void PrintMessage(String content)
    {
        PrintCurrentTime();
        ta.append(content + "\n");
    }
}
