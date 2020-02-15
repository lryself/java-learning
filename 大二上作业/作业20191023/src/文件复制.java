import java.io.*;

public class 文件复制 {
    public static void main(String arg[])
    {
        File IN=new File("F:/编程/java/输入流.txt");
        File OUT=new File("F:/编程/java/输出流.txt");
        if(IN.exists())
        {
            if(OUT.exists())
            {
                try
                {
                    FileInputStream input=new FileInputStream(IN);
                    FileOutputStream output=new FileOutputStream(OUT);
                    byte by[]=new byte[1024*1024];
                    while(true)
                    {
                        int len=input.read(by);
                        output.write(by,0,len);
                        if(len<1024*1024)
                            break;
                    }
                    input.close();
                    output.close();
                    System.out.println("复制完成！");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("输出流文件不存在！");
        }
        else
            System.out.println("输入流文件不存在！");
    }
}
