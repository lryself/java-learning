package 考试练习;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.sql.DriverManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.util.*;

public class 学生信息管理 {

	public static void main(String[] args) {
		new app1();
	}

}
class app1
{
	JFrame jFrame;
	JPanel jPanel;
	Container container;
	JScrollPane jScrollPane;
	JTable jTable;
	DefaultTableModel defaultTableModel;
	
	JPanel jPanel2;
	JLabel jLabel[]=new JLabel[3];
	JTextField jTextField[]=new JTextField[3];
	JRadioButton jRadioButton[]=new JRadioButton[2];
	ButtonGroup buttonGroup=new ButtonGroup();
	JButton jButton[]=new JButton[5];
	
	Connection connection;
	Statement statement;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	int selectint;
	
	app1() {
		init();
		updata();
	}
	void init()
	{
		initjPanel2();
		initJtable();
		jFrame=new JFrame("学生信息管理");
		jPanel=new JPanel(new BorderLayout());
		jFrame.add(jPanel);
		jScrollPane=new JScrollPane(jTable);
		jPanel.add(jPanel2,BorderLayout.SOUTH);
		jPanel.add(jScrollPane,BorderLayout.CENTER);

		initAction();
		
		jFrame.setSize(960,480);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	void initjPanel2()
	{
		jPanel2=new JPanel(new GridLayout(2,1));
		for(int i=0;i<3;i++)
		{
			jTextField[i]=new JTextField();
		}
		jLabel[0]=new JLabel("学号");
		jLabel[1]=new JLabel("姓名");
		jLabel[2]=new JLabel("出生日期");
		jButton[0]=new JButton("增加");
		jButton[1]=new JButton("修改");
		jButton[2]=new JButton("删除");
		jButton[3]=new JButton("查询");
		jButton[4]=new JButton("提示全部");
		JPanel jPanel3=new JPanel(new GridLayout(1,8));
		JPanel jPanel4=new JPanel(new GridLayout(1,5));
		jPanel3.add(jLabel[0]);
		jPanel3.add(jTextField[0]);
		jPanel3.add(jLabel[1]);
		jPanel3.add(jTextField[1]);
		jPanel3.add(jRadioButton[0]=new JRadioButton("男"));
		jPanel3.add(jRadioButton[1]=new JRadioButton("女"));
		buttonGroup.add(jRadioButton[0]);
		buttonGroup.add(jRadioButton[1]);
		jPanel3.add(jLabel[2]);
		jPanel3.add(jTextField[2]);
		for(int i=0;i<5;i++)
		{
			jPanel4.add(jButton[i]);
		}
		jPanel2.add(jPanel3);
		jPanel2.add(jPanel4);
	}

	void initJtable() {
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			String string="jdbc:Access:///F:/编程/java/考试练习/src/考试练习/student.accdb";
			connection=DriverManager.getConnection(string,"","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vector <String>columVector=new Vector<String>();
		Vector rowsVector=new Vector();
		Vector rowVector=new Vector();
		columVector.add("学号");
		columVector.add("姓名");
		columVector.add("性别");
		columVector.add("出生日期");
		rowsVector.add(rowVector);
		defaultTableModel=new DefaultTableModel(rowsVector,columVector);
		jTable=new JTable(defaultTableModel);
	}

	void initAction()
	{
		for(int i=0;i<5;i++)
			jButton[i].addActionListener(new ButtonAction(i));
		jTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				selectint=jTable.getSelectedRow();
				jTextField[0].setText((String) jTable.getValueAt(selectint, 0));
				jTextField[1].setText((String) jTable.getValueAt(selectint, 1));
				if(((String)jTable.getValueAt(selectint,2)).equals("男"))
				{
					jRadioButton[0].setSelected(true);
				}
				if(((String)jTable.getValueAt(selectint,2)).equals("女")){
					jRadioButton[1].setSelected(true);
				}
				jTextField[2].setText((String) jTable.getValueAt(selectint, 3));
			}
		});
	}
	
	void updata()
	{
		while(defaultTableModel.getRowCount()!=0)
		{
			defaultTableModel.removeRow(0);
		}
		try {
			preparedStatement=connection.prepareStatement("select * from stu");
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Vector<String>rowVector=new Vector<String>();
				for(int i=1;i<=4;i++)
				{
					rowVector.add(resultSet.getString(i));
				}
				defaultTableModel.addRow(rowVector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void updata(String str,String strname)
	{
		while(defaultTableModel.getRowCount()!=0)
		{
			defaultTableModel.removeRow(0);
		}
		try {
			preparedStatement=connection.prepareStatement("select * from stu where "+strname+"=?");
			preparedStatement.setString(1, str);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Vector<String>rowVector=new Vector<String>();
				for(int i=1;i<=4;i++)
				{
					rowVector.add(resultSet.getString(i));
				}
				defaultTableModel.addRow(rowVector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	void clean()
	{
		for(int i=0;i<3;i++)
		{
			jTextField[i].setText("");
		}
		for(int i=0;i<2;i++)
		{
			jRadioButton[i].setSelected(false);
		}
	}

	class ButtonAction implements ActionListener
	{
		int n;
		ButtonAction(int n) {
			this.n=n;
		}
		public void actionPerformed(ActionEvent e) {
			if(n==4)
			{
				updata();
			}
			if(n==3)
			{
				try {
					String tempString="";
					int flag=0;
					int i,j;
					for(i=0;i<3;i++)
					{
						if(!jTextField[i].getText().equals(""))
						{
							tempString=jTextField[i].getText();
							flag=0;
							break;
						}
					}
					for(j=0;j<2;j++)
					{
						if(jRadioButton[j].isSelected())
						{
							tempString=jRadioButton[j].getText();
							flag=1;
							break;
						}
					}
					if(tempString.equals(""))
						return;
					if(flag==0)
						updata(jLabel[i].getText(), tempString);
					if(flag==1)
						updata(tempString, "性别");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(n==2)
			{
				try {
					preparedStatement=connection.prepareStatement("delete from stu where 学号 =?");
					preparedStatement.setString(1, jTextField[0].getText());
					preparedStatement.executeUpdate();
					updata();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
			if(n==1)
			{
				try {
					preparedStatement=connection.prepareStatement("delete from stu where 学号 =?");
					preparedStatement.setString(1, (String)jTable.getValueAt(selectint, 0));
					preparedStatement.executeUpdate();
					
					preparedStatement=connection.prepareStatement("insert into stu values(?,?,?,?)");
					preparedStatement.setString(1, jTextField[0].getText());
					preparedStatement.setString(2, jTextField[1].getText());
					if(jRadioButton[0].isSelected())
						preparedStatement.setString(3, jRadioButton[0].getText());
					else {
						preparedStatement.setString(3, jRadioButton[1].getText());
					}
					preparedStatement.setString(4, jTextField[2].getText());
					preparedStatement.executeUpdate();
					updata();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(n==0)
			{
				try {
					preparedStatement=connection.prepareStatement("insert into stu values(?,?,?,?)");
					preparedStatement.setString(1, jTextField[0].getText());
					preparedStatement.setString(2, jTextField[1].getText());
					if(jRadioButton[0].isSelected())
						preparedStatement.setString(3, jRadioButton[0].getText());
					else if(jRadioButton[1].isSelected())
						preparedStatement.setString(3, jRadioButton[1].getText());
					else
						preparedStatement.setString(3, "");
					preparedStatement.setString(4, jTextField[2].getText());
					preparedStatement.executeUpdate();
					updata();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			clean();
		}
	}
}