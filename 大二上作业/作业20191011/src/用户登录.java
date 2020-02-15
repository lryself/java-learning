import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import  javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;

public class 用户登录 {
    public static void main(String args[])
    {
        new 登录();
    }
}

class 登录
{
    JFrame jFrame=new JFrame("登录界面");
    JPanel jPanel=new JPanel(new GridLayout(4,1));
    JButton button1=new JButton("登录");
    JLabel tip=new JLabel("请输入您的用户名（第一行）和密码（第二行）");
    TextArea id=new TextArea();
    JPasswordField passwordField=new JPasswordField();
    int i=0;
    登录()
    {
        passwordField.setEchoChar('*');
        tip.setEnabled(false);
        jFrame.add(jPanel);
        jPanel.add(tip);
        jPanel.add(id);
        jPanel.add(passwordField);
        jPanel.add(button1);
        jFrame.setVisible(true);
        jFrame.setSize(640,480);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        button1.addActionListener(new ButtonAction());
    }
    class ButtonAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            String name,password;
            name=id.getText();
            password=new String(passwordField.getPassword());
            passwordField.setText("");
            if(name.equals("lry")&&password.equals("123"))
            {
                jFrame.setTitle("登录成功");
            }
            else
            {
                jFrame.setTitle("登录失败");
                tip.setText("用户名或密码错误！\n请重新输入您的用户名（第一行）和密码（第二行）");
                if(3==++i)
                {
                    JOptionPane.showMessageDialog(null,"错误次数过多,请稍后再试");
                    System.exit(0);
                }
            }
        }
    }
}