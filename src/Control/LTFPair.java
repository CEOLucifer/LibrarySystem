package Control;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 标签(L)和文本框(TF)对控件
 */
public class LTFPair extends JPanel
{
    public JLabel lab = new JLabel();
    public JTextField tf = new JTextField();

    public LTFPair(String labText)
    {
        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        add(lab);
        lab.setText(labText);
        lab.setPreferredSize(new Dimension(80, 30));

        add(tf);
        tf.setPreferredSize(new Dimension(200, 30));
    }
}
