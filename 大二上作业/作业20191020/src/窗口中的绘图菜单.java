import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class 窗口中的绘图菜单 {
    public static void main(String arg[])
    {
        new app2();
    }
}
class app2
{
    JFrame jFrame=new JFrame("窗口中的绘图菜单");

    JButton jButtons[]=new JButton[3];
    int place[][]=new int[2][2];
    int clicknum=0;
    int choose=1;
    Graphics g;
    ToolPane toolPane;
    DrawPane drawPane;
    app2()
    {
        jButtons[0]=new JButton("矩形");
        jButtons[1]=new JButton("圆");
        jButtons[2]=new JButton("直线");
        toolPane=new ToolPane();
        drawPane=new DrawPane();
        jFrame.add(toolPane);
        jFrame.add(drawPane);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(640,480);
        jFrame.setLayout(new GridLayout(1,2));
        jFrame.setForeground(Color.BLUE);
        jFrame.setVisible(true);
        g=drawPane.getGraphics();
        drawPane.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(clicknum==0)
                {
                    place[0][0]=e.getX();
                    place[0][1]=e.getY();
                }
                if(clicknum==1)
                {
                    place[1][0]=e.getX();
                    place[1][1]=e.getY();
                }
                clicknum++;
                if(clicknum==2)
                {
                    if(choose==1)
                    {
                        g.drawRect(place[0][0],place[0][1],(int)Math.abs((place[1][0]-place[0][0])),(int)Math.abs((place[1][1]-place[0][1])));
                    }
                    if(choose==2)
                    {
                        int r=(int)(Math.sqrt((place[1][0]-place[0][0])*(place[1][0]-place[0][0])+(place[1][1]-place[0][1])*(place[1][1]-place[0][1])));
                        g.drawOval(place[0][0],place[0][1],r,r);

                    }
                    if(choose==3)
                    {
                        g.drawLine(place[0][0],place[0][1],place[1][0],place[1][1]);
                    }
                    clicknum=0;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
    class DrawPane extends JPanel
    {
        DrawPane()
        {
            this.setBackground(Color.WHITE);
            this.setVisible(true);
        }
    }
    class ToolPane extends JPanel
    {
        JToolBar toolBar=new JToolBar("绘图工具");
        ToolPane()
        {
            toolBar.setFloatable(false);
            this.add(toolBar);
            for(int i=0;i<3;i++)
            {
                toolBar.add(jButtons[i]);
            }
            for(int i=0;i<2;i++)
            {
                for(int j=0;j<2;j++)
                {
                    place[i][j]=-1;
                }
            }
            jButtons[0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    choose=1;
                }
            });
            jButtons[1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    choose=2;
                }
            });
            jButtons[2].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    choose=3;
                }
            });
        }
    }
}