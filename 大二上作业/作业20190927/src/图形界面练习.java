import java.awt.*;
import javax.swing.*;
public class 图形界面练习{
    public static void main(String args[]) throws  Exception{
        new Operation();
    }
}

class Operation{
    JFrame frame=new JFrame("网格布局演示");
    JButton number1,number2,number3,number4,number5,number6,number7,number8,number9,number0;
    JButton plus_sign,minus_sign,times_sign,division_sign,sqrt_sign,per_cent,reciprocal,equal_sign,negative_sign,decimal_point;
    Operation()
    {
        Container container=frame.getContentPane();
        container.setLayout(new GridLayout(4,5));
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
        frame.add(number7);
        frame.add(number8);
        frame.add(number9);
        frame.add(division_sign);
        frame.add(sqrt_sign);
        frame.add(number4);
        frame.add(number5);
        frame.add(number6);
        frame.add(times_sign);
        frame.add(per_cent);
        frame.add(number1);
        frame.add(number2);
        frame.add(number3);
        frame.add(minus_sign);
        frame.add(reciprocal);
        frame.add(number0);
        frame.add(negative_sign);
        frame.add(decimal_point);
        frame.add(plus_sign);
        frame.add(equal_sign);
        frame.setSize(640,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}