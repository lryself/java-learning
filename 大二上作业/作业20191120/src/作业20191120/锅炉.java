package 作业20191120;
import java.util.*;
import java.math.*;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class 锅炉 {

	public static void main(String[] args) {
		new view();
	}

}
class view extends JFrame
{
	Container container;
	JScrollPane jScrollPane;
	JTable jTable;
	DefaultTableModel defaultTableModel;
	Temp temp=new Temp();
	Stress stress=new Stress(this);
	
	view()
	{
		String columString[]= {"锅炉名称","温度","锅炉压力"};
		String data[][]=new String[5][3];
		for(int i=0;i<5;i++)
		{
			data[i][0]="锅炉"+String.valueOf(i+1);
			data[i][1]="0";
			data[i][2]="0";
		}
		defaultTableModel=new DefaultTableModel(data, columString);
		jTable=new JTable(defaultTableModel);
		jScrollPane=new JScrollPane(jTable);
		container=jScrollPane;
		add(container);
		setSize(960,540);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		temp.start();
		stress.start();
	}

}
class Temp extends Thread
{
	Connection connection;
	PreparedStatement preparedStatement;
	Random random=new Random();
	Temp() {}
	public void run()
	{
		while(true)
		{
			try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
		    connection=DriverManager.getConnection("jdbc:Access:///F:/编程/java/作业20191120/src/作业20191120/锅炉数据.accdb","","");
	        for(int i=0;i<5;i++)
	        {
		        preparedStatement=connection.prepareStatement("update 表1 set tempe"+" = ? where name = ?");
		        preparedStatement.setString(1, String.valueOf(random.nextInt(1000)));
		        preparedStatement.setString(2, String.valueOf("锅炉"+(i+1)));
		        preparedStatement.executeUpdate();
		        preparedStatement=connection.prepareStatement("update 表1 set stess"+" = ? where name = ?");
		        preparedStatement.setString(1, String.valueOf(random.nextInt(1000)));
		        preparedStatement.setString(2, String.valueOf("锅炉"+(i+1)));
		        preparedStatement.executeUpdate();
	        }
	        sleep(20000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
class Stress extends Thread
{
	Connection connection;
	PreparedStatement preparedStatement;
	view v;
	public Stress(view v) {
		try {
			this.v=v;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run()
	{
		ResultSet result;
		while(true)
		{
			try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
		    connection= DriverManager.getConnection("jdbc:Access:///F:/编程/java/作业20191120/src/作业20191120/锅炉数据.accdb","","");
			preparedStatement=connection.prepareStatement("select * from 表1");
			result=preparedStatement.executeQuery();
			for(int i=0;i<5;i++)
			{
				result.next();
				v.defaultTableModel.setValueAt(result.getString("tempe"),i,1);
				v.defaultTableModel.setValueAt(result.getString("stess"),i,2);
			}
			sleep(30000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}