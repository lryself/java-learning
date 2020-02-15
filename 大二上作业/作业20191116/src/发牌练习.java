import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class 发牌练习 {
    public static void main(String arg[])
    {
        app App[]=new app[4];
        for(int i=0;i<4;i++)
        {
            App[i]=new app(i);
        }
    }
}
class fapai extends Thread
{
   static int alltrick[][]=new int[4][54];
   Random random=new Random();
   JTextField t;
   int num;
   fapai(JTextField name,int n)
   {
       t=name;
       num=n;
   }

    public void run() {
        while(true)
        {
            if(iffull())
                return;
            t.setText(t.getText()+(pai()+1)+" ");
            try {
                sleep(1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    synchronized int pai()
   {
       int i;
       while(true)
       {
           i=random.nextInt(54);
           if(alltrick[num][i]==0)
           {
               alltrick[num][i]=1;
               return i;
           }
       }
   }
   boolean iffull()
    {
        for(int i=0;i<54;i++)
        {
            if(alltrick[num][i]==0)
                return false;
        }
        return true;
    }
}
class app
{
    JFrame jFrame=new JFrame("发牌程序");
    JButton jButton[]=new JButton[5];
    JTextField jTextField[]=new JTextField[4];
    fapai fapai[]=new fapai[4];
    app(int n)
    {
        JPanel jPanel1=new JPanel(new GridLayout(3,3));
        jFrame.setSize(960,240);
        for(int i=0;i<4;i++)
        {
            jButton[i]=new JButton();
            jPanel1.add(jButton[i]);
            jTextField[i]=new JTextField();
            jTextField[i].setEnabled(false);
            jPanel1.add(jTextField[i]);
            fapai[i]=new fapai(jTextField[i],n);
        }
        jButton[4]=new JButton();
        jPanel1.add(jButton[4]);
        jFrame.add(jPanel1);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jButton[2].setText("开始");
        jButton[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<4;i++)
                {
                    fapai[i].start();
                }
            }
        });
        jFrame.setVisible(true);
    }
}