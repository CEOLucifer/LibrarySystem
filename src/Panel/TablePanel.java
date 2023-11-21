package Panel;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Manager.DataBaseMgr;
import Utility.Action.EventCenter;

/**
 * 表格框
 */
public class TablePanel extends JPanel
{
    private JTable tab;
    private JScrollPane spTab;

    public TablePanel()
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("表格"));

        tab = new JTable();
        tab.setAutoCreateRowSorter(true);
        spTab = new JScrollPane(tab);
        add(spTab, BorderLayout.CENTER);

        // #region 事件监听
        EventCenter.Instance.AddListener("查询书籍成功", () ->
        {
            // 生成表格模式
            DefaultTableModel defaultTableModel = new DefaultTableModel()
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            Vector<String> head = new Vector<>();
            Vector<Vector<String>> data = new Vector<>();
            ResultSet resultSet = DataBaseMgr.Instance.getResultSet();
            try
            {
                // 获取表头
                ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); ++i)
                {
                    head.addElement(metaData.getColumnName(i));
                }

                // 获取数据
                while (resultSet.next())
                {
                    Vector<String> row = new Vector<>();
                    for (int i = 1; i <= 7; ++i)
                    {
                        row.addElement(resultSet.getString(i));
                    }
                    data.addElement(row);
                }

                // 模式关联数据
                defaultTableModel.setDataVector(data, head);

                // 表格关联该模式
                tab.setModel(defaultTableModel);

                // 刷新？

                // 释放资源
                DataBaseMgr.Instance.CloseResultSet();

            } catch (SQLException e)
            {
                e.printStackTrace();
            }

        });
        EventCenter.Instance.AddListener("借阅记录查询完成", () ->
        {
            // 生成表格模式
            DefaultTableModel defaultTableModel = new DefaultTableModel()
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            Vector<String> head = new Vector<>();
            Vector<Vector<String>> data = new Vector<>();
            ResultSet resultSet = DataBaseMgr.Instance.getResultSet();
            try
            {
                // 获取表头
                ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); ++i)
                {
                    head.addElement(metaData.getColumnName(i));
                }

                // 获取数据
                while (resultSet.next())
                {
                    Vector<String> row = new Vector<>();
                    for (int i = 1; i <= metaData.getColumnCount(); ++i)
                    {
                        row.addElement(resultSet.getString(i));
                    }
                    data.addElement(row);
                }

                // 模式关联数据
                defaultTableModel.setDataVector(data, head);

                // 表格关联该模式
                tab.setModel(defaultTableModel);

                // 刷新？

                // 释放资源
                DataBaseMgr.Instance.CloseResultSet();

            } catch (SQLException e)
            {
                e.printStackTrace();
            }

        });
        // #endregion
    }
}
