package Frame;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Panel.FunctionPanel;
import Panel.MessagePanel;
import Panel.TablePanel;

public class MainFrame extends JFrame
{
    // 默认宽高
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private TablePanel tablePanel = new TablePanel();
    private MessagePanel messagePanel = new MessagePanel();
    private FunctionPanel functionPanel = new FunctionPanel();

    private JPanel PanelTM = new JPanel(); // 用来放tablePanel和messagePanel的面板

    public MainFrame()
    {
        setTitle("图书管理系统");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Image image = new ImageIcon("src\\ArtRes\\icon.PNG").getImage();
        setIconImage(image);

        PanelTM.setLayout(new BorderLayout());
        PanelTM.add(tablePanel, BorderLayout.CENTER);
        PanelTM.add(messagePanel, BorderLayout.SOUTH);
        add(PanelTM, BorderLayout.CENTER);

        add(functionPanel, BorderLayout.EAST);

        setVisible(true);
    }
}
