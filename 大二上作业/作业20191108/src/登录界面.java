import javax.swing.*;
import java.sql.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.plaf.basic.BasicOptionPaneUI;

public class 登录界面
{
    public static void main(String arg[])
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
            if(pass())
            {
                jFrame.setTitle("登录成功");
            }
            else
            {
                jFrame.setTitle("登录失败");
                tip.setText("用户名或密码错误！\n请重新输入您的用户名（第一行）和密码（第二行）");
            }
        }
    }
    boolean pass()
    {
        try
        {
            Class.forName("com.hxtt.sql.access.AccessDriver");
            Connection connection= DriverManager.getConnection("jdbc:Access:///F:/编程/java/作业20191108/user.accdb","","");
            ResultSet resultSet;
            PreparedStatement preparedStatement=connection.prepareStatement("select * from 表1");
            String name=id.getText();
            resultSet=preparedStatement.executeQuery();
            String word=resultSet.getString("password");
            while(resultSet.next())
            {
                if(word.equals(new String(passwordField.getPassword()))&&name.equals(resultSet.getString("name")))
                    return true;
            }
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            return false;
        }
    }
}