package 对象数组练习;
import java.util.*;

public class 对象数组练习 {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		student a[]=new student[4];
		for(int i=0;i<4;i++)
		{
			System.out.println("请输入第"+(i+1)+"个学生的姓名和学号，用空格隔开");
			a[i]=new student(input.next(),input.next());
		}
		for(int i=0;i<4;i++)
		{
			System.out.print("第"+(i+1)+"个学生的姓名和学号为：");
			a[i].print();
		}
	}

}

class student
{
	private String name,id;
	student(){}
	student(String name,String id)
	{
		this.name=name;
		this.id=id;
	}
	public void print()
	{
		System.out.println(name+" "+id);
	}
}