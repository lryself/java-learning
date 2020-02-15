package 输入星期;
import java.util.*;
public class 输入星期 {

	public static void main(String[] args) {
		System.out.println("请输入一个整数(1~7)");
		Scanner input=new Scanner(System.in);
		try
		{
			int week=input.nextInt();
			String weekeen;
			switch(week)
			{
			case 1:weekeen="Monday";break;
			case 2:weekeen="Tuesday";break;
			case 3:weekeen="Wednesday";break;
			case 4:weekeen="Thursday";break;
			case 5:weekeen="Friday";break;
			case 6:weekeen="Saturday";break;
			case 7:weekeen="Sunday";break;
			default:throw new ArgsException();
			}
			System.out.println("该数对应的星期是"+weekeen);
		}
		catch(InputMismatchException ex)
		{
			System.out.println("输入数据类型有误");
		}
		catch(ArgsException ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			System.out.println("程序结束");
		}
	}

}
class ArgsException extends Exception
{
	ArgsException()
	{
		super("输入数据范围有误，请输入1-7之间的整数");
	}
}
