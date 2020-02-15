package 图形管理;
import java.util.*;
public class GraghManagerment {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入您要创建的第一个矩形长和宽（用空格隔开）");
		Rectangle a=new Rectangle(sc.nextInt(),sc.nextInt());
		System.out.println("请输入您要创建的第二个矩形长和宽（用空格隔开）");
		Rectangle b=new Rectangle(sc.nextInt(),sc.nextInt());
		System.out.println("请输入您要创建的正方形边长");
		Square c=new Square(sc.nextInt());
		System.out.println("第一个矩形周长为："+a.perimeter()+" 面积为："+a.area());
		System.out.println("第二一个矩形周长为："+b.perimeter()+" 面积为："+b.area());
		System.out.println("正方形周长为："+c.perimeter()+" 面积为："+c.area());
		System.out.println("矩形个数为："+Rectangle.num()+" 正方形个数为："+Square.num());
	}
}
class Rectangle
{
	int longth,wideth;
	static int number;
	Rectangle(){}
	Rectangle(int a,int b)
	{
		longth=a;
		wideth=b;
		number++;
	}
	int perimeter()
	{
		return longth*2+wideth*2;
	}
	int area()
	{
		return longth*wideth;
	}
	static int num()
	{
		return number;
	}
}
class Square extends Rectangle
{
	static int number;
	Square(){}
	Square(int a)
	{
		longth=a;
		number++;
	}
	int perimeter()
	{
		return longth*4;
	}
	int area()
	{
		return longth*longth;
	}
	static int num()
	{
		return number;
	}
}