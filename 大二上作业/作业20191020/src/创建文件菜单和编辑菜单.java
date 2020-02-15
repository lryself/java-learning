import javax.swing.*;
import java.awt.*;

public class 创建文件菜单和编辑菜单 {
    public static void main(String arg[])
    {
        new app1();
    }
}

class app1
{
    JFrame jFrame=new JFrame("创建文件菜单和编辑菜单");
    JMenuBar jMenuBar=new JMenuBar();
    JMenu file=new JMenu("文件");
    JMenu report=new JMenu("编辑");
    app1()
    {
        jFrame.setSize(640,480);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setJMenuBar(jMenuBar);

        jMenuBar.add(file);
        jMenuBar.add(report);

        JMenuItem jMenuItem[]=new JMenuItem[4];
        jMenuItem[0]=new JMenuItem("打开文件");
        jMenuItem[1]=new JMenuItem("保存文件");
        jMenuItem[2]=new JMenuItem("剪切");
        jMenuItem[3]=new JMenuItem("复制");
        file.add(jMenuItem[0]);
        file.add(jMenuItem[1]);
        report.add(jMenuItem[2]);
        report.add(jMenuItem[3]);

        jFrame.setVisible(true);
    }
}