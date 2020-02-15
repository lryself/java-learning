package MainClass;
import Client.*;
import Server.*;
import javafx.application.Application;
import javax.swing.*;

public class mainclass {
    public static void main(String args[])
    {
        try
        {
            Object[] options = {"客户端","服务器","取消"};
            int response=JOptionPane.showOptionDialog(null, "请选择您要打开的程序", "打开程序",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if(response==0)
            {
                Application.launch(Client.class);
            }
            else if(response==1)
            {
                Application.launch(Server.class);
            }
            else{}
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"请检查环境变量和虚拟机设置");
        }
    }
}