package StringBuffer类;
import java.util.*;
public class StringBuffer类 {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		System.out.println("请输入字符串S");
		String S=input.nextLine();
		System.out.println("请输入字符串S1");
		String S1=input.nextLine();
		System.out.println("请输入字符串S2");
		String S2=input.nextLine();
		int n=S1.length();
		while(S.contains(S1))
		{
			S=S.substring(0, S.indexOf(S1))+S2+S.substring(S.indexOf(S1)+n);
		}
		System.out.println("更新后的字符串为："+S);
	}
}
