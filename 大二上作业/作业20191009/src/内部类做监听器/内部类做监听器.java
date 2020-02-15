package 内部类做监听器;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class 内部类做监听器 {
    public static void main(String arg[])
    {
        OC oc=new OC();
    }
}

class OC{
    JButton ok,cancel;
    JFrame jFrame;
    JPanel jPanel;
    OC()
    {
        jFrame=new JFrame("等待操作");
        jPanel=new JPanel(new GridLayout(2,1));
        ok=new JButton("确定");
        ok.addActionListener(new kbAction(

        ));
        cancel=new JButton("取消");
        cancel.addActionListener(new kbAction());
        jPanel.add(ok);
        jPanel.add(cancel);
        jFrame.add(jPanel);
        jFrame.setSize(640,480);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    class kbAction implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource()==ok)
            {
                if(ok.getText().equals("确定"))
                    ok.setText("OK");
                else
                    ok.setText("确定");
                jFrame.setTitle("您单击了确定按钮");
            }
            if(e.getSource()==cancel)
            {
                if(cancel.getText().equals("取消"))
                    cancel.setText("Cancel");
                else
                    cancel.setText("取消");
                jFrame.setTitle("您单击了取消按钮");
            }
        }
    }
}