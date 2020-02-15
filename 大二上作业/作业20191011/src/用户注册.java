import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class 用户注册 {
    public static void main(String arg[])
    {
        new app();
    }
}
class app
{
    JFrame jFrame=new JFrame("用户注册");
    JPanel jPanel=new JPanel(new BorderLayout());
    JPanel northpanel=new JPanel(new GridLayout(7,2));
    JPanel southpanel=new JPanel(new BorderLayout());
    JTextField id=new JTextField(),name=new JTextField();
    JPasswordField passwordField=new JPasswordField();
    JRadioButton man=new JRadioButton("男");
    JRadioButton woman=new JRadioButton("女");
    ButtonGroup gender=new ButtonGroup();
    JTextArea result=new JTextArea();
    JButton sign=new JButton("注册");
    JButton reset=new JButton("重置");
    JPanel resultButton=new JPanel(new GridLayout(1,2));
    String[] major={"计算机科学与技术","信息安全","软件工程","物联网工程"};
    JList jList=new JList(major);
    JComboBox year;
    JComboBox month;
    JComboBox day;
    JLabel birth[]=new JLabel[3];
    JPanel birthday=new JPanel(new GridLayout(1,6));
    JCheckBox hobbies[]=new JCheckBox[3];
    JLabel jLabel[]=new JLabel[7];
    app()
    {
        inibirthday();
        messageButton();

        jFrame.add(jPanel);
        jFrame.setSize(960,640);
        jPanel.add(BorderLayout.SOUTH,southpanel);
        jPanel.add(BorderLayout.NORTH,northpanel);

        resultButton.add(sign);
        resultButton.add(reset);
        southpanel.add(BorderLayout.NORTH,resultButton);
        southpanel.add(BorderLayout.SOUTH,result);

        gender.add(man);
        gender.add(woman);

        passwordField.setEchoChar('*');

        iniaction();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    void messageButton()
    {
        int i=0;
        northpanel.add(jLabel[i++]=new JLabel("学号："));
        northpanel.add(id);
        northpanel.add(jLabel[i++]=new JLabel("姓名："));
        northpanel.add(name);
        northpanel.add(jLabel[i++]=new JLabel("密码："));
        northpanel.add(passwordField);

        northpanel.add(jLabel[i++]=new JLabel("性别："));
        JPanel genderButton=new JPanel(new GridLayout(1,3));
        northpanel.add(genderButton);
        genderButton.add(man);
        genderButton.add(woman);
        man.setSelected(true);

        northpanel.add(jLabel[i++]=new JLabel("专业："));
        northpanel.add(jList);
        jList.setSelectedIndex(0);

        northpanel.add(jLabel[i++]=new JLabel("出生年月："));
        birthday.add(year);
        birthday.add(birth[0]=new JLabel("年"));
        birthday.add(month);
        birthday.add(birth[1]=new JLabel("月"));
        birthday.add(day);
        birthday.add(birth[2]=new JLabel("日"));
        northpanel.add(birthday);

        northpanel.add(jLabel[i++]=new JLabel("爱好："));
        JPanel hobbiesButton=new JPanel(new GridLayout(1,3));
        hobbiesButton.add(hobbies[0]=new JCheckBox("读书"));
        hobbiesButton.add(hobbies[1]=new JCheckBox("运动"));
        hobbiesButton.add(hobbies[2]=new JCheckBox("旅游"));
        northpanel.add(hobbiesButton);
    }
    void inibirthday()
    {
        String years[]=new String[100];
        for(int i=0,j=1949;i<100;i++)
        {
            years[i]=String.valueOf(j++);
        }
        String months[]=new String[100];
        for(int i=0,j=1;i<12;i++)
        {
            months[i]=String.valueOf(j++);
        }
        String days[]=new String[100];
        for(int i=0,j=1;i<31;i++)
        {
            days[i]=String.valueOf(j++);
        }

        year=new JComboBox(years);
        month=new JComboBox(months);
        day=new JComboBox(days);
    }
    void iniaction()
    {
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                id.setText("");
                name.setText("");
                passwordField.setText("");
                man.setSelected(true);
                jList.setSelectedIndex(0);
                year.setSelectedIndex(0);
                month.setSelectedIndex(0);
                day.setSelectedIndex(0);
                for(int i=0;i<3;i++)
                {
                    hobbies[i].setSelected(false);
                }
                result.setText("");
            }
        });
        sign.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text=" 性别：";
                if(man.isSelected())
                    text=text+man.getText();
                if(woman.isSelected())
                    text=text+woman.getText();
                text=text+" 专业："+(String)(jList.getSelectedValue())+" 出生年月："+year.getSelectedItem()+"年"+month.getSelectedItem()+"月"+day.getSelectedItem()+"日"+" 爱好：";
                for(int i=0;i<3;i++)
                {
                    if(hobbies[i].isSelected())
                        text+=hobbies[i].getText();
                }
                result.setText(result.getText()+"学号："+id.getText()+" 姓名："+name.getText()+" 密码："+String.valueOf(passwordField.getPassword())+text+"\n");
            }
        });
    }
}