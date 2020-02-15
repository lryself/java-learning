package 一位数组练习;
import java.util.*;

public class 一维数组练习 {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		System.out.println("请输入你要输入的数据数量：");
		int n=input.nextInt();
		int a[]=new int[n];
		for(int i=0;i<n;i++)
			a[i]=input.nextInt();
		Arrays.sort(a);
		for(int x:a)
		{
			System.out.print(x+" ");
		}
	}
	
}
