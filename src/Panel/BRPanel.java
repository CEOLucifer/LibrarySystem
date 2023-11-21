package Panel;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import Control.LTFPair;
import Layout.VLayout;
import Manager.DataBaseMgr;
import Manager.MessageManager;

/**
 * 借/还书面板
 */
public class BRPanel extends JPanel
{
    private LTFPair p_ID = new LTFPair("用户ID");
    private LTFPair p_ISBN = new LTFPair("ISBN");

    private JPanel btnsPanel = new JPanel();
    private JButton btnBorrow = new JButton("借书");
    private JButton btnReturn = new JButton("还书");

    public BRPanel()
    {
        setLayout(new VLayout());

        // #region 控件相关
        add(p_ID);
        add(p_ISBN);
        AddListener();

        btnsPanel.setLayout(new FlowLayout());
        btnsPanel.add(btnBorrow);
        btnsPanel.add(btnReturn);
        add(btnsPanel);
        btnBorrow.setEnabled(false);
        btnReturn.setEnabled(false);

        btnBorrow.addActionListener((e) ->
        {
            // 获取输入
            String ISBN = p_ISBN.tf.getText();
            String ID = p_ID.tf.getText();

            // 查询有没有该用户
            if (!DataBaseMgr.Instance.CheckReader(ID))
            {
                MessageManager.Instance.PrintMessage("没有该用户的记录！");
                return;
            }

            // 查询有没有该书
            if (!DataBaseMgr.Instance.CheckBook(ISBN))
            {
                MessageManager.Instance.PrintMessage("没有该书的记录！");
                return;
            }

            // 查询有没有借出记录
            if (DataBaseMgr.Instance.CheckBorrowed(ISBN))
            {
                // 获取归还日期
                ResultSet resultset = DataBaseMgr.Instance.getResultSet();
                try
                {
                    resultset.next(); // 直接到第一条
                    String return_date = resultset.getString(5);
                    MessageManager.Instance.PrintMessage("所要借的书已被其他人借出，归还日期：" + return_date);
                } catch (SQLException e1)
                {
                    e1.printStackTrace();
                } finally
                {
                    DataBaseMgr.Instance.CloseResultSet();
                }
                return;
            }

            // 查询有没有达到借阅上限
            if (DataBaseMgr.Instance.CheckLimit(ID)) // 达到借阅上限
            {
                MessageManager.Instance.PrintMessage("该读者已达到借阅上限，无法借阅更多书籍");
                return;
            }

            /* 经过重重检查，终于可以插入本次借阅记录 */
            // 获取当前日期
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = dateTime.format(formatter);
            System.out.println(date);

            // 随机生成归还时间
            Random r = new Random();
            String return_date = r.nextInt(dateTime.getYear(), dateTime.getYear() + 3) + "-"
                    + r.nextInt(dateTime.getMonthValue(), 13) + "-" + r.nextInt(1, 29);

            // 执行
            int row = DataBaseMgr.Instance.InsertIntoRecord(ISBN, ID, date, return_date);

            if (row > 0)
            {
                MessageManager.Instance.PrintMessage("借阅成功，归还时间：" + return_date);
            } else
            {
                MessageManager.Instance.PrintMessage("借阅失败！");
            }

        });

        btnReturn.addActionListener((e) ->
        {
            // 获取输入
            String ISBN = p_ISBN.tf.getText();
            String ID = p_ID.tf.getText();

            // 执行
            int row = DataBaseMgr.Instance.DeleteFromRecord(ISBN, ID);

            if (row > 0)
            {
                MessageManager.Instance.PrintMessage("归还成功，并从Record删除该记录");
            } else
            {
                MessageManager.Instance.PrintMessage("没有该借阅记录！");
            }

        });

        // #endregion
    }

    private void AddListener()
    {
        p_ID.tf.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == 8)
                {
                    String old = p_ID.tf.getText();
                    if (old.length() == 0 || p_ISBN.tf.getText().length() == 0)
                    {
                        btnBorrow.setEnabled(false);
                        btnReturn.setEnabled(false);
                    } else
                    {
                        btnBorrow.setEnabled(true);
                        btnReturn.setEnabled(true);
                    }
                } else
                {
                    if (p_ISBN.tf.getText().length() == 0)
                    {
                        btnBorrow.setEnabled(false);
                        btnReturn.setEnabled(false);
                    } else
                    {
                        btnBorrow.setEnabled(true);
                        btnReturn.setEnabled(true);
                    }
                }
            }
        });

        p_ISBN.tf.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == 8)
                {
                    String old = p_ISBN.tf.getText();
                    if (old.length() == 0 || p_ID.tf.getText().length() == 0)
                    {
                        btnBorrow.setEnabled(false);
                        btnReturn.setEnabled(false);
                    } else
                    {
                        btnBorrow.setEnabled(true);
                        btnReturn.setEnabled(true);
                    }
                } else
                {
                    if (p_ID.tf.getText().length() == 0)
                    {
                        btnBorrow.setEnabled(false);
                        btnReturn.setEnabled(false);
                    } else
                    {
                        btnBorrow.setEnabled(true);
                        btnReturn.setEnabled(true);
                    }
                }
            }
        });
    }
}
