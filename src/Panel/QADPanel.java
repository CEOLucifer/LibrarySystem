package Panel;

import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import Control.LTFPair;
import Layout.VLayout;
import Manager.BridgeMgr;
import Manager.DataBaseMgr;
import Manager.MessageManager;
import Utility.Action.EventCenter;

/**
 * 查询/添加/删除书籍面板
 */
public class QADPanel extends JPanel
{
    private LTFPair p_ISBN = new LTFPair("ISBN");
    private LTFPair p_Title = new LTFPair("书名");
    private LTFPair p_Authors = new LTFPair("作者");
    private LTFPair p_Publisher = new LTFPair("发行商");
    private LTFPair p_EditionNumber = new LTFPair("版本号");
    private LTFPair p_PublicationDate = new LTFPair("发行日期");
    private LTFPair p_Type = new LTFPair("类型");

    private JPanel btnsPanel = new JPanel();
    private JButton btnQuery = new JButton("查询");
    private JButton btnAdd = new JButton("添加");
    private JButton btnDelete = new JButton("删除");

    public QADPanel()
    {
        setLayout(new VLayout());

        add(p_ISBN);
        add(p_Title);
        add(p_Authors);
        add(p_Publisher);
        add(p_EditionNumber);
        add(p_PublicationDate);
        add(p_Type);
        AddListener();

        add(btnsPanel);
        btnsPanel.setLayout(new FlowLayout());
        btnsPanel.add(btnQuery);
        btnsPanel.add(btnAdd);
        btnAdd.setEnabled(false);
        btnsPanel.add(btnDelete);

        // #region 查询按钮事件监听
        btnQuery.addActionListener((e) ->
        {
            // 获取输入
            String ISBN = p_ISBN.tf.getText();
            ISBN = ISBN.length() == 0 ? null : ISBN;
            String title = p_Title.tf.getText();
            title = title.length() == 0 ? null : title;
            String authors = p_Authors.tf.getText();
            authors = authors.length() == 0 ? null : authors;
            String publisher = p_Publisher.tf.getText();
            publisher = publisher.length() == 0 ? null : publisher;
            String editionNumber = p_EditionNumber.tf.getText();
            editionNumber = editionNumber.length() == 0 ? null : editionNumber;
            String publicationDate = p_PublicationDate.tf.getText();
            publicationDate = publicationDate.length() == 0 ? null : publicationDate;
            String type = p_Type.tf.getText();
            type = type.length() == 0 ? null : type;

            // 查询
            DataBaseMgr.Instance.Query(ISBN, title, authors, publisher,
                    editionNumber == null ? null : Integer.parseInt(editionNumber),
                    publicationDate, type);

            // 触发查询成功事件
            MessageManager.Instance.PrintMessage("查询书籍成功");
            EventCenter.Instance.EventTrigger("查询书籍成功");
        });
        // #endregion

        // #region Add按钮事件监听
        btnAdd.addActionListener((e) ->
        {
            // 获取输入
            String ISBN = p_ISBN.tf.getText();
            String title = p_Title.tf.getText();
            String authors = p_Authors.tf.getText();
            String publisher = p_Publisher.tf.getText();
            String editionNumber = p_EditionNumber.tf.getText();
            String publicationDate = p_PublicationDate.tf.getText();
            String type = p_Type.tf.getText();

            int row = DataBaseMgr.Instance.InsertIntoBooks(ISBN, title, authors, publisher,
                    Integer.parseInt(editionNumber), publicationDate, type);

            if (row > 0)
            {
                MessageManager.Instance.PrintMessage("插入到Books成功");
            } else
            {
                MessageManager.Instance.PrintMessage("插入到Books失败，存在相同ISBN的对象或发行日期格式有误");
            }
        });
        // #endregion

        // #region Delete按钮事件监听
        btnDelete.addActionListener((e) ->
        {
            // 获取输入
            String ISBN = p_ISBN.tf.getText();
            ISBN = ISBN.length() == 0 ? null : ISBN;
            String title = p_Title.tf.getText();
            title = title.length() == 0 ? null : title;
            String authors = p_Authors.tf.getText();
            authors = authors.length() == 0 ? null : authors;
            String publisher = p_Publisher.tf.getText();
            publisher = publisher.length() == 0 ? null : publisher;
            String editionNumber = p_EditionNumber.tf.getText();
            editionNumber = editionNumber.length() == 0 ? null : editionNumber;
            String publicationDate = p_PublicationDate.tf.getText();
            publicationDate = publicationDate.length() == 0 ? null : publicationDate;
            String type = p_Type.tf.getText();
            type = type.length() == 0 ? null : type;

            int row = DataBaseMgr.Instance.DeleteFromBooks(ISBN, title, authors, publisher,
                    editionNumber == null ? null : Integer.parseInt(editionNumber), publicationDate, type);
            BridgeMgr.Instance.row = row;

            if (row >= 0)
            {
                System.out.println("删除成功，删除的元组数：" + row);
                EventCenter.Instance.EventTrigger("删除Books成功");
            } else
            {
                System.out.println("删除失败");
                EventCenter.Instance.EventTrigger("删除Books失败");
            }
        });
        // #endregion
    }

    /**
     * 判断是否有文本框为空
     * 
     * @return
     */
    private boolean hasEmpty()
    {
        return p_ISBN.tf.getText().length() == 0 || p_Title.tf.getText().length() == 0 ||
                p_Authors.tf.getText().length() == 0 || p_Publisher.tf.getText().length() == 0 ||
                p_EditionNumber.tf.getText().length() == 0 || p_PublicationDate.tf.getText().length() == 0 ||
                p_Type.tf.getText().length() == 0;
    }

    /**
     * 添加文本框事件监听。写的复杂了，后面再看看怎么改
     */
    private void AddListener()
    {
        p_ISBN.tf.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == 8)
                {
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                } else
                {
                    String old = p_ISBN.tf.getText();
                    String ne = old + c;
                    p_ISBN.tf.setText(ne);
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                    p_ISBN.tf.setText(old);
                }
            }
        });
        p_Title.tf.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == 8)
                {
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                } else
                {
                    String old = p_Title.tf.getText();
                    String ne = old + c;
                    p_Title.tf.setText(ne);
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                    p_Title.tf.setText(old);
                }
            }
        });
        p_Authors.tf.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == 8)
                {
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                } else
                {
                    String old = p_Authors.tf.getText();
                    String ne = old + c;
                    p_Authors.tf.setText(ne);
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                    p_Authors.tf.setText(old);
                }
            }
        });
        p_Publisher.tf.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == 8)
                {
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                } else
                {
                    String old = p_Publisher.tf.getText();
                    String ne = old + c;
                    p_Publisher.tf.setText(ne);
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                    p_Publisher.tf.setText(old);
                }
            }
        });
        p_EditionNumber.tf.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == 8)
                {
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                } else
                {
                    String old = p_EditionNumber.tf.getText();
                    String ne = old + c;
                    p_EditionNumber.tf.setText(ne);
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                    p_EditionNumber.tf.setText(old);
                }
            }
        });
        p_PublicationDate.tf.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == 8)
                {
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                } else
                {
                    String old = p_PublicationDate.tf.getText();
                    String ne = old + c;
                    p_PublicationDate.tf.setText(ne);
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                    p_PublicationDate.tf.setText(old);
                }
            }
        });
        p_Type.tf.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (c == 8)
                {
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                } else
                {
                    String old = p_Type.tf.getText();
                    String ne = old + c;
                    p_Type.tf.setText(ne);
                    if (hasEmpty())
                    {
                        btnAdd.setEnabled(false);
                    } else
                    {
                        btnAdd.setEnabled(true);
                    }
                    p_Type.tf.setText(old);
                }
            }
        });
    }
}
