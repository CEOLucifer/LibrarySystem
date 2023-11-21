package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;

import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * 功能面板
 */
public class FunctionPanel extends JPanel
{
    private JComboBox<String> cb = new JComboBox<>();
    private QADPanel qadPanel = new QADPanel();
    private BRPanel brPanel = new BRPanel();
    private BorrowQueryPanel borrowQueryPanel = new BorrowQueryPanel();

    public FunctionPanel()
    {
        setPreferredSize(new Dimension(300, 0));
        setBackground(Color.orange);
        setLayout(new BorderLayout());

        // #region 控件相关
        add(cb, BorderLayout.NORTH);
        cb.addItem("查询/添加/删除书籍");
        cb.addItem("借/还书");
        cb.addItem("借阅记录查询");
        cb.addItemListener((e) ->
        {
            if (e.getStateChange() == ItemEvent.SELECTED) // 过滤。否则每次选择都会执行两次
            {
                String selection = (String) e.getItem();
                switch (selection)
                {
                    case "查询/添加/删除书籍":
                        remove(getComponent(1));
                        add(qadPanel, BorderLayout.CENTER);
                        break;
                    case "借/还书":
                        remove(getComponent(1));
                        add(brPanel, BorderLayout.CENTER);
                        break;
                    case "借阅记录查询":
                        remove(getComponent(1));
                        add(borrowQueryPanel, BorderLayout.CENTER);
                        break;
                }
                validate();
                repaint();
            }
        });

        add(qadPanel, BorderLayout.CENTER);
        // #endregion
    }
}
