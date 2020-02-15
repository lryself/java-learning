package 向量和泛型练习;
import java.util.*;

public class 向量和泛型练习 {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		Vector<student> a=new Vector<student>();
		for(int i=0;i<4;i++)
		{
			System.out.println("请输入第"+(i+1)+"个学生的姓名和学号，用空格隔开");
			a.add(new student(input.next(),input.next()));
		}
		for(int i=0;i<4;i++)
		{
			System.out.print("第"+(i+1)+"个学生的姓名和学号为：");
			a.get(i).print();
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