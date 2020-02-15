import java.util.*;
import java.io.*;

public class 对象流练习 {
    public static void main(String arg[]) throws IOException
    {
        Scanner sc=new Scanner(System.in);
        File file=new File("F:/编程/java/输入流.txt");
        ObjectOutputStream bf=new ObjectOutputStream(new FileOutputStream(file));
        student stu=new student();
        char yn='y';
        while(yn=='y')
        {
            System.out.println("请输入您要保存的学生信息（姓名，院系，年龄，成绩）");
            stu.name=sc.next();
            stu.collage=sc.next();
            stu.age=sc.nextInt();
            stu.score=sc.nextInt();
            System.out.println("请问你是否继续继续输入（y/n）");
            yn=sc.next().charAt(0);
            bf.writeObject(stu);
        }
        bf.close();
        ObjectInputStream br=new ObjectInputStream(new FileInputStream(file));
        int len;
        try
        {
            while((stu=(student)br.readObject())!=null){
                System.out.println(stu.name+' '+stu.collage+' '+stu.age+' '+stu.score+"\n");
            }
        }catch (Exception e)
        {
        }
        finally {
            br.close();
        }
    }
}
class student implements Serializable
{
    int age,score;
    String name,collage;
    student(){}
}