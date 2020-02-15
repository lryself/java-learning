import java.sql.*;
import java.util.Scanner;

public class 使用prepare {
    public static void main(String arg[])
    {
        try
        {
            Class.forName("com.hxtt.sql.access.AccessDriver");
            Connection connection= DriverManager.getConnection("jdbc:Access:///F:/编程/java/作业20191108/student.accdb","","");
            ResultSet resultSet;
            PreparedStatement preparedStatement=connection.prepareStatement("insert into student[(姓名,性别,专业,成绩)] values(?,?,?,?)");
            Scanner scanner=new Scanner(System.in);
            System.out.println("请输入您要创建的学生信息（姓名，性别，专业，成绩）用空格隔开");
            String name=scanner.next();
            String sex=scanner.next();
            String pro=scanner.next();
            String score=scanner.next();
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,sex);
            preparedStatement.setString(3,pro);
            preparedStatement.setString(4,score);
            preparedStatement.executeUpdate();
            preparedStatement=connection.prepareStatement("SELECT * from student");
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next())
            {
                System.out.print("姓名："+resultSet.getString("姓名"));
                System.out.print(" 性别："+resultSet.getString("性别"));
                System.out.print(" 专业："+resultSet.getString("专业"));
                System.out.println(" 成绩："+resultSet.getString("成绩"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
