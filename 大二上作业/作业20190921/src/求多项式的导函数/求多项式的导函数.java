package 求多项式的导函数;
import java.util.*;

public class 求多项式的导函数 {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		System.out.println("请输入一个多项式：");
		String x=input.next();
		int n=x.length(),i,j;
		for(i=0,j=0;i<n;i=j)
		{
			if(x.charAt(j)=='+'||x.charAt(j)=='-')
			{
				System.out.print(x.charAt(j));
				i++;
			}
			j=i;
			while(j<n)
			{
				if(x.charAt(j)=='+'||x.charAt(j)=='-')
				{
					discermint(x.substring(i, j)).print();
					break;
				}
				j++;
			}
			if(j>=n)
			{
				discermint(x.substring(i, j)).print();
				break;
			}
			i=j;
		}
	}
	static monomial discermint(String x)
	{
		monomial m=new monomial();
		int i=0,j=0,n=x.length();
		while(i<n)
		{
			if(Character.isDigit(x.charAt(i)))
			{
				j=i;
				while(j<n&&Character.isDigit(x.charAt(j)))
				{
					j++;
				}
				m.coe=Integer.parseInt(x.substring(i, j));
				i=j;
			}
			else if(Character.isLetter(x.charAt(i)))
			{
				for(j=i;Character.isLetter(x.charAt(j))&&j<n;j++);
				m.x=new String(x.substring(i, j));
				i=j;
			}
			else if(x.charAt(i)=='^')
			{
				i++;
				j=i;
				while(Character.isDigit(x.charAt(j)))
				{
					j++;
					if(j>=n)
						break;
				}
				m.index=Integer.parseInt(x.substring(i, j));
				i=j;
			}
			else
				i++;
		}
		return m;
	}
}

class monomial{
	int coe;
	int index;
	String x;
	monomial(){}
	void print()
	{
		coe=coe*index;
		index=index-1;
		System.out.print(coe+"*"+x+"^"+index);
	}
}