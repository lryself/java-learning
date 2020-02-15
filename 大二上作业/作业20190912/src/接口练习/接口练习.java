package 接口练习;
import java.util.*;
public class 接口练习 {

	public static void main(String[] args) {
		String c;
		Scanner input=new Scanner(System.in);
		System.out.println("请输入您要计算的图形(圆形，矩形，正方形)：");
		c=input.next();
		if(c.equals("圆形"))
		{
			System.out.println("请输入圆形的半径（默认PI为3）：");
			Circle d=new Circle(input.nextInt());
			System.out.println("该圆的面积为"+d.getArea()+" 周长为"+d.getPerimeter());
		}
		else if(c.equals("矩形"))
		{
			System.out.println("请输入矩形的长和宽：");
			Rectangle d=new Rectangle(input.nextInt(),input.nextInt());
			System.out.println("该矩形的面积为"+d.getArea()+" 周长为"+d.getPerimeter());
		}
		else if(c.equals("正方形"))
		{
			System.out.println("请输入正方形的边长：");
			Square d=new Square(input.nextInt());
			System.out.println("该正方形的面积为"+d.getArea()+" 周长为"+d.getPerimeter());
		}
		else
		{
			System.out.println("输入有误，再见");
		}
	}
}

interface shape{
	public abstract int getArea();
	public abstract int getPerimeter();
}

class Circle implements shape{
	final public int PI=3;
	int longth;
	public Circle() {}
	public Circle(int longth) {
		this.longth=longth;
	}
	public int getPerimeter()
	{
		return longth*2*PI;
	}
	public int getArea()
	{
		return longth*longth*PI; 
	}
}
class Rectangle implements shape{
	private int weighth,longth;
	public Rectangle() {}
	public Rectangle(int longth,int weighth)
	{
		this.longth=longth;
		this.weighth=weighth;
	}
	public int getArea()
	{
		return longth*weighth;
	}
	public int getPerimeter()
	{
		return longth*2+weighth*2;
	}
}
class Square extends Rectangle{
	public Square() {}
	public Square(int longth)
	{
		super(longth,longth);
	}
}