import java.io.*;
import java.util.*;

public class 编程题2 {
    public static void main(String arg[])
    {
        File OUT=new File("F:/编程/java/输出流.txt");
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入您要储存的字符串");
        String string=sc.next();
        if(OUT.exists())
        {
            try
            {
                FileOutputStream output=new FileOutputStream(OUT);
                output.write(string.getBytes());
                output.close();
                System.out.println("字符串已保存到文件F:/编程/java/输出流.txt中！");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
            System.out.println("输出流文件不存在！");
    }
}