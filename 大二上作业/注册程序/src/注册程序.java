import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class 注册程序 {
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
    JTextArea result=new JTextArea("学号： 姓名： 密码： 性别：男 专业：计算机科学与技术 出生年月：1949年1月1日 爱好：\n以注册的学生：\n");
    app()
    {
        inibirthday();
        messageButton();

        jFrame.add(jPanel);
        jFrame.setSize(960,840);
        jPanel.add(BorderLayout.SOUTH,southpanel);
        jPanel.add(BorderLayout.NORTH,northpanel);

        resultButton.add(sign);
        resultButton.add(reset);
        southpanel.add(BorderLayout.NORTH,resultButton);
        southpanel.add(BorderLayout.SOUTH,result);

        gender.add(man);
        gender.add(woman);
        result.setEnabled(false);

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
                result.setText("学号： 姓名： 密码： 性别：男 专业：计算机科学与技术 出生年月：1949年1月1日 爱好：\n以注册的学生：\n");
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
        man.addActionListener(new menAction());
        woman.addActionListener(new menAction());
        for(int i=0;i<3;i++)
        {
            hobbies[i].addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent e) {
                    JCheckBox checkBox = (JCheckBox) e.getSource();
                    String text=result.getText();
                    if(checkBox.isSelected())
                    {
                        int a=text.indexOf(checkBox.getText());
                        if(a==-1)
                        {
                            result.setText(print(result.getText(),checkBox.getText()+" ","爱好："));
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
                            result.setText(text.substring(0,a));
                            return;
                        }
                        result.setText(text.substring(0,a)+text.substring((b)));
                    }
                }
            });
        }
        year.addActionListener(new dateAction());
        month.addActionListener(new dateAction());
        day.addActionListener(new dateAction());
        jList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                JList a=(JList)e.getSource();
                String majorstr=" 专业：";
                int begin=result.getText().indexOf(majorstr)+majorstr.length();
                int end=result.getText().indexOf(" 出生年月");
                result.setText(result.getText().substring(0,begin)+(String)a.getSelectedValue()+result.getText().substring(end)+" ");
            }
        });
        name.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {}
            public void focusLost(FocusEvent e) {
                JTextField a=(JTextField) e.getSource();
                String majorstr="姓名：";
                int begin=result.getText().indexOf(majorstr)+majorstr.length();
                int end=result.getText().indexOf(" 密码");
                result.setText(result.getText().substring(0,begin)+(String)a.getText()+result.getText().substring(end)+" ");
            }
        });
        id.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {}
            public void focusLost(FocusEvent e) {
                JTextField a=(JTextField) e.getSource();
                String majorstr="学号：";
                int begin=result.getText().indexOf(majorstr)+majorstr.length();
                int end=result.getText().indexOf(" 姓名");
                result.setText(result.getText().substring(0,begin)+(String)a.getText()+result.getText().substring(end)+" ");
            }
        });
        passwordField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {}
            public void focusLost(FocusEvent e) {
                JPasswordField a=(JPasswordField) e.getSource();
                String majorstr="密码：";
                int begin=result.getText().indexOf(majorstr)+majorstr.length();
                int end=result.getText().indexOf(" 性别：");
                result.setText(result.getText().substring(0,begin)+String.valueOf(passwordField.getPassword())+result.getText().substring(end)+" ");
            }
        });
    }
    class menAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            JRadioButton a=(JRadioButton) e.getSource();
            String majorstr=" 性别：";
            int begin=result.getText().indexOf(majorstr)+majorstr.length();
            int end=result.getText().indexOf(" 专业：");
            result.setText(result.getText().substring(0,begin)+a.getText()+result.getText().substring(end)+" ");
        }
    }
    class dateAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            String majorstr="出生年月：";
            int begin=result.getText().indexOf(majorstr)+majorstr.length();
            int end=result.getText().indexOf(" 爱好：");
            result.setText(result.getText().substring(0,begin)+year.getSelectedItem()+"年"+month.getSelectedItem()+"月"+day.getSelectedItem()+"日"+result.getText().substring(end)+" ");
        }
    }
    String print(String txt,String instr,String kind)
    {
        int begin;
        begin=txt.indexOf(kind)+kind.length()-1;
        String before,after;
        before=txt.substring(0,begin+1);
        if((begin+1)<=txt.length())
            after=txt.substring(begin+1);
        else
            after="";
        return before+instr+after;
    }
}