package 二维数组练习;
import java.util.*;

public class 二维数组练习 {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		int score[][]=new int [4][5];
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.println("请输入第"+(i+1)+"个学生的第"+(j+1)+"门课的成绩");
				score[i][j]=input.nextInt();
				score[i][3]+=score[i][j];
			}
			score[i][4]=score[i][3]/3;
		}
		for(int i=0;i<4;i++)
		{
			System.out.println("第"+(i+1)+"个学生的总分为"+score[i][3]+"平均分为"+score[i][4]);
		}
	}

}
