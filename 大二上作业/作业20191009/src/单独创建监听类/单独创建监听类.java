package 单独创建监听类;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class 单独创建监听类 {
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
        ok.addActionListener(new kbAction(this));
        cancel=new JButton("取消");
        cancel.addActionListener(new kbAction(this));
        jPanel.add(ok);
        jPanel.add(cancel);
        jFrame.add(jPanel);
        jFrame.setSize(640,480);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}

class kbAction implements ActionListener {
    OC oc;
    kbAction(OC oc)
    {
        this.oc=oc;
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==oc.ok)
        {
            if(oc.ok.getText().equals("确定"))
                oc.ok.setText("OK");
            else
                oc.ok.setText("确定");
            oc.jFrame.setTitle("您单击了确定按钮");
        }
        if(e.getSource()==oc.cancel)
        {
            if(oc.cancel.getText().equals("取消"))
                oc.cancel.setText("Cancel");
            else
                oc.cancel.setText("取消");
            oc.jFrame.setTitle("您单击了取消按钮");
        }
    }
}