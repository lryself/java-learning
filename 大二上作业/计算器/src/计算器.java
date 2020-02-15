import java.awt.*;
import javax.swing.*;

public class 计算器{
    public static void main(String args[]) throws  Exception{
        new 界面();
    }
}

class 界面
{
    JFrame frame;
    界面()
    {
        JButton number1,number2,number3,number4,number5,number6,number7,number8,number9,number0;
        JButton plus_sign,minus_sign,times_sign,division_sign,sqrt_sign,per_cent,reciprocal,equal_sign,negative_sign,decimal_point;
        JPanel jpanel=new JPanel(new GridLayout(4,5));
        number1=new JButton("1");
        number2=new JButton("2");
        number3=new JButton("3");
        number4=new JButton("4");
        number5=new JButton("5");
        number6=new JButton("6");
        number7=new JButton("7");
        number8=new JButton("8");
        number9=new JButton("9");
        number0=new JButton("0");
        plus_sign=new JButton("+");
        minus_sign=new JButton("-");
        times_sign=new JButton("*");
        division_sign=new JButton("/");
        sqrt_sign=new JButton("sqrt");
        per_cent=new JButton("%");
        reciprocal=new JButton("1/x");
        equal_sign=new JButton("=");
        negative_sign=new JButton("+/-");
        decimal_point=new JButton(".");
        jpanel.add(number7);
        jpanel.add(number8);
        jpanel.add(number9);
        jpanel.add(division_sign);
        jpanel.add(sqrt_sign);
        jpanel.add(number4);
        jpanel.add(number5);
        jpanel.add(number6);
        jpanel.add(times_sign);
        jpanel.add(per_cent);
        jpanel.add(number1);
        jpanel.add(number2);
        jpanel.add(number3);
        jpanel.add(minus_sign);
        jpanel.add(reciprocal);
        jpanel.add(number0);
        jpanel.add(negative_sign);
        jpanel.add(decimal_point);
        jpanel.add(plus_sign);
        jpanel.add(equal_sign);
        jpanel.setSize(640,400);
        jpanel.setVisible(true);

        JPanel panel=new JPanel();
        panel.setBackground(Color.CYAN);
        TextArea text=new TextArea();
        text.setSize(400,50);
        Button CE,AC;
        CE=new Button("CE");
        AC=new Button("AC");
        panel.add(text);
        panel.add(AC);
        panel.add(CE);
        panel.setVisible(true);

        frame=new JFrame("计算器");
        frame.setSize(640,480);
        frame.setResizable(false);
        Container container=frame.getContentPane();
        container.setLayout(new BorderLayout(0,1));
        TextArea text1=new TextArea("0");
        text1.setEditable(false);
        frame.add(BorderLayout.SOUTH,jpanel);
        frame.add(BorderLayout.CENTER,panel);
        frame.add(BorderLayout.NORTH,text1);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}