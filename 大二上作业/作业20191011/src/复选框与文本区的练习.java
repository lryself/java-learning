import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class 复选框与文本区的练习 {
    public static void main(String arg[])
    {
        new show();
    }
}
class show
{
    JFrame jFrame=new JFrame("复选框与文本区的练习");
    JPanel jPanel=new JPanel(new GridLayout(4,1));
    JTextArea jTextArea=new JTextArea();
    JCheckBox jCheckBox1=new JCheckBox("读书");
    JCheckBox jCheckBox2=new JCheckBox("旅游");
    JCheckBox jCheckBox3=new JCheckBox("运动");
    show()
    {
        jFrame.add(jPanel);
        jFrame.setSize(640,480);
        jPanel.add(jTextArea);
        jPanel.add(jCheckBox1);
        jPanel.add(jCheckBox2);
        jPanel.add(jCheckBox3);
        jCheckBox1.addChangeListener(new CheckAction());
        jCheckBox2.addChangeListener(new CheckAction());
        jCheckBox3.addChangeListener(new CheckAction());
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    class CheckAction implements ChangeListener
    {
        public void stateChanged(ChangeEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            String text=jTextArea.getText();
            if(checkBox.isSelected())
            {
                int a=text.indexOf(checkBox.getText());
                if(a==-1)
                {
                    jTextArea.setText(jTextArea.getText()+checkBox.getText()+"\n");
                }
            }
            else
            {
                int a=text.indexOf(checkBox.getText());
                if(a==-1)
                    return;
                int b=a+checkBox.getText().length()+1;
                if(b==text.length())
                {
                    jTextArea.setText(text.substring(0,a));
                    return;
                }
                jTextArea.setText(text.substring(0,a)+text.substring((b)));
            }
        }
    }
}