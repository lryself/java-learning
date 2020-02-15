import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class 创建三个对话框 {
    public static void main(String arg[])
    {
        new show();
    }
}

class show
{
    JFrame jFrame=new JFrame("三个对话窗口");
    JButton jButton[]=new JButton[3];

    show()
    {
        jFrame.setSize(640,480);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridLayout(3,1));
        jButton[0]=new JButton("JDialog窗口");
        jButton[1]=new JButton("消息对话框");
        jButton[2]=new JButton("确认对话框");
        for(int i=0;i<3;i++)
        {
            jFrame.add(jButton[i]);
        }

        jButton[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showJDialog(jFrame);
            }
        });
        jButton[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame,"点击确定按钮关闭","消息对话框",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        jButton[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(jFrame,"将要退出","确认对话框",JOptionPane.YES_NO_CANCEL_OPTION);
            }
        });
        jFrame.setVisible(true);
    }

    void showJDialog(JFrame jFrame) {
        JDialog dialog = new JDialog(jFrame, "JDialog窗口", true);
        dialog.setSize(250, 150);
        dialog.setResizable(false);
        dialog.setLayout(new GridLayout(2,1));
        dialog.setLocationRelativeTo(jFrame);
        JLabel jLabel = new JLabel("点击确定按钮退出");
        JButton jButton = new JButton("确定");
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 关闭对话框
                dialog.dispose();
            }
        });
        dialog.add(jLabel);
        dialog.add(jButton);
        dialog.setVisible(true);
    }
}