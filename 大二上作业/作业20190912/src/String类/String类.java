package String类;
import java.util.*;
public class String类 {
	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		System.out.println("请输入一个字符串");
		String a=input.nextLine();
		int longth=a.length();
		int charnum=0,intnum=0,othernum=0;
		for(int i=0;i<longth;i++)
		{
			if(a.toUpperCase().charAt(i)>='A'&&a.toUpperCase().charAt(i)<='Z')
				charnum++;
			else if(a.toUpperCase().charAt(i)>='0'&&a.toUpperCase().charAt(i)<='9')
				intnum++;
			else othernum++;
		}
		System.out.println("这个字符串中有"+charnum+"个字母，"+intnum+"个整数，"+othernum+"个其他字符");
	}
}