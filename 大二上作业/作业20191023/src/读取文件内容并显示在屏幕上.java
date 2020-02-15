import java.io.*;

public class 读取文件内容并显示在屏幕上 {
    public static void main(String arg[])
    {
        File IN=new File("F:/编程/java/输入流.txt");
        if(IN.exists())
        {
            try
            {
                FileInputStream input=new FileInputStream(IN);
                byte by[]=new byte[1024*1024];
                while(true)
                {
                    int len=input.read(by);
                    System.out.print(new String(by,0,len));
                    if(len<1024*1024)
                        break;
                }
                input.close();
                System.out.println("\n文件中的数据显示完成！");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("输入流文件不存在！");
    }
}
