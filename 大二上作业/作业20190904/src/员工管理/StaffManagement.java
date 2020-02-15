package 员工管理;
import java.util.*;

public class StaffManagement {
    public static void main(String[] args)
    {
    	Scanner sc=new Scanner(System.in);
    	System.out.println("请输入您要创建的员工的信息（姓名和员工号），用空格隔开");
    	Emploee a=new Emploee(sc.next(),sc.next());
    	System.out.println("请输入您要创建的部门主管信息（姓名，员工号和主管部门），用空格隔开");
    	Manager b=new Manager(sc.next(),sc.next(),sc.next());
    	a.print();
    	b.print();
    }
}

class Emploee
{
    String id,name;
    Emploee(){}
    Emploee(String b,String a)
    {
        id=a;
        name=b;
    }
    void print()
    {
        System.out.println(name+"的员工号是："+id);
    }
}

class Manager extends Emploee
{
	String industry;
	Manager(){}
	Manager(String a,String b,String c)
	{
		super(a,b);
		industry=c;
	}
	void print()
	{
		System.out.println("主管"+industry+"部门的"+name+"的员工号是："+id);
	}
}