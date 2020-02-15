import java.sql.*;

public class Java语言访问数据库 {
    public static void main(String arg[])
    {
        try
        {
            Class.forName("com.hxtt.sql.access.AccessDriver");
            Connection connection= DriverManager.getConnection("jdbc:Access:///F:/编程/java/作业20191107/student.accdb","","");
            Statement sql=connection.createStatement();
            ResultSet resultSet;
            sql.executeUpdate("insert into student[(姓名,院系,性别,出生日期,平均成绩)] values (6,'李瑞阳','控计院','男','2000.1.16',90);");
            sql.executeUpdate("insert into student[(姓名,院系,性别,出生日期,平均成绩)] values (7,'王宇飞','控计院','男','2001.6.20',50);");
            resultSet=sql.executeQuery("select * from student where 性别='男';");
            while(resultSet.next())
                System.out.println("姓名：" + resultSet.getString("姓名") + " 院系" + resultSet.getString("院系") + " 出生日期" + resultSet.getString("出生日期") + " 平均成绩" + resultSet.getString("平均成绩"));
            System.out.println();
            sql.executeUpdate("update student[(姓名,院系,性别,出生日期,平均成绩)] set 平均成绩 = 平均成绩*0.7+30 where 院系='控计院';");
            sql.executeUpdate("delete from student where 平均成绩<60;");
            resultSet=sql.executeQuery("select  * from student");
            while(resultSet.next())
                System.out.println("姓名：" + resultSet.getString("姓名") + " 院系" + resultSet.getString("院系") + " 出生日期" + resultSet.getString("出生日期") + " 平均成绩" + resultSet.getString("平均成绩"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
