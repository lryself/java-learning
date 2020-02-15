import java.util.*;
import java.io.*;

public class 缓冲流练习 {
    public static void main(String arg[]) throws IOException
    {
        Scanner sc=new Scanner(System.in);
        File file=new File("F:/编程/java/输入流.txt");
        BufferedWriter bf=new BufferedWriter(new FileWriter(file));
        String collage,name,age,score;
        char yn='y';
        while(yn=='y')
        {
            System.out.println("请输入您要保存的学生信息（姓名，院系，年龄，成绩）");
            collage=sc.next();
            name=sc.next();
            age=sc.next();
            score=sc.next();
            System.out.println("请问你是否继续继续输入（y/n）");
            yn=sc.next().charAt(0);
            bf.write(collage+' ');
            bf.write(name+' ');
            bf.write(age+' ');
            bf.write(score+' ');
            bf.newLine();
        }
        bf.close();
        BufferedReader br=new BufferedReader(new FileReader(file));
        int len;
        while((name=br.readLine())!=null){
            System.out.println(name);
        }
        br.close();
    }
}