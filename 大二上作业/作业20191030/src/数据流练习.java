import java.util.*;
import java.io.*;

public class 数据流练习 {
    public static void main(String arg[]) throws IOException
    {
        Scanner sc=new Scanner(System.in);
        File file=new File("F:/编程/java/输入流.txt");
        DataOutputStream bf=new DataOutputStream(new FileOutputStream(file));
        String str[]=new String[4];
        char yn='y';
        while(yn=='y')
        {
            System.out.println("请输入您要保存的学生信息（姓名，院系，年龄，成绩）");
            for(int i=0;i<4;i++)
                str[i]=sc.next();
            System.out.println("请问你是否继续继续输入（y/n）");
            yn=sc.next().charAt(0);
            for(int i=0;i<4;i++)
            {
                bf.writeUTF(str[i]);
            }
            bf.writeUTF("\n");
        }
        bf.close();
        DataInputStream br=new DataInputStream(new FileInputStream(file));
        int len;
        String string;
        while((string=br.readUTF())!=null){
            System.out.println(string);
        }
        br.close();
    }
}