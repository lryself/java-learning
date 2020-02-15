package 学生类;
import java.io.*;
import java.util.*;

public class student {
	private int year,math,English,computer;
	private String name,id;
	public void initstu(String a,String b,int c)//a为学号,b为姓名,c为出生年月
	{
		id=a;
		name=b;
		year=c;
	}
	public void stuscore(int a,int b,int c)//a为数学,b为英语,c为计算机
	{
		math=a;
		English=b;
		computer=c;
	}
	int sum()
	{
		return math+English+computer;
	}
	int age()
	{
		return (2019-year);
	}
	public void print()
	{
		System.out.print(name+"的学号是"+id+",今年"+age()+"岁 总成绩是"+sum());
	}
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		student s1=new student() ;
		System.out.println("请输入该学生的学号，姓名和出生年份，用空格隔开。");
		s1.initstu(sc.next(), sc.next(), sc.nextInt());
		System.out.println("请输入该学生的数学成绩，英语成绩和计算机成绩，用空格隔开。");
		s1.stuscore(sc.nextInt(), sc.nextInt(), sc.nextInt());
		s1.print();
	}

}
