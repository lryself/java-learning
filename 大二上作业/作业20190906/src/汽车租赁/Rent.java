package 汽车租赁;
import java.util.*;
public class Rent {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		int all=0;
		int n=0,m=0;
		truck a;lim b;coach c;
		String yn;
		String[][] list=new String[100][3];
		while(true)
		{
			System.out.println("请输入您要选择的车型:\n1.轿车\n2.客车\n3.卡车");
			switch(input.nextInt())
			{
			case 1:
				System.out.println("请输入您要租赁的车型，本店提供：本田CRV，福特Escape，本田SUV");
				list[n][0]=input.next();
				list[n][1]="车牌号：京"+(char)(65+(int)(Math.random()*26))+(int)(Math.random()*10)+(int)(Math.random()*10)+(int)(Math.random()*10)+(int)(Math.random()*10)+(int)(Math.random()*10);
				b=new lim(list[n][0]);
				System.out.println("请输入您要租赁的天数");
				m=input.nextInt();
				list[n][2]="时长:"+m+"天";
				all+=b.sum(m);
				break;
			case 2:
				System.out.println("请输入您要租赁的客车座位数:");
				list[n][0]="客车";
				m=input.nextInt();
				list[n][1]=m+"座";
				c=new coach(m);
				System.out.println("请输入您要租赁的天数;");
				m=input.nextInt();
				list[n][2]=m+"天";
				all+=c.sum(m);
				break;
			case 3:
				System.out.println("请输入您要租赁的卡车吨位:");
				m=input.nextInt();
				list[n][0]="卡车";
				list[n][1]=m+"吨";
				a=new truck(m);
				System.out.println("请输入您要租赁的天数;");
				m=input.nextInt();
				list[n][2]=m+"天";
				all+=a.sum(m);
				break;
			}
			n++;
			System.out.println("是否继续输入？（yes/no）");
			yn=input.next();
			if(yn.equals("no"))
			{
				if(all>10000)
					all=(int) (all*0.9);
				else if(all>1000)
					all=(int)(all*0.95);
				System.out.println("您租赁的车辆如下：");
				for(int i=0;i<n;i++)
				{
					System.out.println(list[i][0]+" "+list[i][1]+" "+list[i][2]);
				}
				System.out.println("总金额为："+all);
				break;
			}
		}
	}

}
class car
{
	int cost;
	car(){}
	int sum(int day)
	{
		return cost*day;
	}
}

class truck extends car
{
	int weight;
	truck(int a){
		weight=a;
		cost=weight*50;
	}
}
class lim extends car
{
	lim(String a)
	{
		switch(a)
		{
		case "本田CRV":
			cost=600;
			break;
		case "福特Escape":
			cost=500;
			break;
		case "本田SUV":
			cost=400;
			break;
		}
	}
	
}
class coach extends car
{
	coach(int a)
	{
		if(a<=16)
			cost=800;
		else
			cost=1000;
	}
}