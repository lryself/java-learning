package 考试练习2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.*;
import javax.swing.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class text {

	public static void main(String[] args) {
		new app1();
	}

}
class app1
{
	JFrame jFrame=new JFrame();
	JPanel jPanelmain;
	JPanel jPanelSouth;
	JPanel jPanelNorth;
	JButton jButton[]=new JButton[5];
	
	JLabel jLabel[]=new JLabel[3];
	JTextField jTextFieldid=new JTextField();
	JTextField jTextFieldnumber=new JTextField();
	JComboBox jComboBox;
	DefaultTableModel jTableModel=new DefaultTableModel();
	JTable jTable=new JTable(jTableModel);
	JScrollPane jScrollPanetable=new JScrollPane(jTable);
	
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	public app1() {
		jPanelmain=new JPanel(new BorderLayout());
		jPanelSouth=new JPanel(new GridLayout(1,5));
		jPanelNorth=new JPanel(new GridLayout(1,6));
		initButton();
		initTable();
		initNorth();
		for(int i=0;i<5;i++)
		{
			jPanelSouth.add(jButton[i]);
		}
		jPanelmain.add(jPanelSouth,BorderLayout.SOUTH);
		jPanelmain.add(jPanelNorth,BorderLayout.NORTH);
		jPanelmain.add(jScrollPanetable,BorderLayout.CENTER);
		
		jFrame.add(jPanelmain);
		jFrame.setSize(960,640);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initAction();
	}
	
	void initTable()
	{
		Vector<String> colum=new Vector<String>();
		Vector rows=new Vector();
		colum.add("账号");
		colum.add("操作");
		colum.add("金额");
		colum.add("余额");
		jTableModel.setDataVector(rows, colum);
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			connection=DriverManager.getConnection("jdbc:Access:///F:/编程/java/考试练习/src/考试练习2/money.accdb","","");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		updata();
	}
	void initButton()
	{
		jButton[0]=new JButton("添加");
		jButton[1]=new JButton("查找");
		jButton[2]=new JButton("删除");
		jButton[3]=new JButton("显示全部");
		jButton[4]=new JButton("保存到文件");
	}
	void initNorth()
	{
		jComboBox=new JComboBox();
		jComboBox.addItem("存入");
		jComboBox.addItem("取出");
		jPanelNorth.add(jLabel[0]=new JLabel("账号："));
		jPanelNorth.add(jTextFieldid);
		jPanelNorth.add(jLabel[1]=new JLabel("操作："));
		jPanelNorth.add(jComboBox);
		jPanelNorth.add(jLabel[2]=new JLabel("金额："));
		jPanelNorth.add(jTextFieldnumber);
	}

	void initAction()
	{
		for(int i=0;i<5;i++)
		{
			jButton[i].addActionListener(new ButtonAction(i));
		}
	}

	class ButtonAction implements ActionListener
	{
		int n;
		public ButtonAction(int n) {
			this.n=n;
		}
		public void actionPerformed(ActionEvent e) {
			try {
				if(n==0)
				{
					Vector<String>vector=new Vector<String>();
					vector.add(jTextFieldid.getText());
					vector.add(jComboBox.getItemAt(jComboBox.getSelectedIndex()).toString());
					vector.add(jTextFieldnumber.getText());
					jTableModel.addRow(vector);
				}
				if(n==1)
				{
					for(int i=0;i<jTable.getRowCount();i++)
					{
						for(int j=0;j<jTable.getColumnCount();j++)
						{
							if(((String)jTable.getValueAt(i, j)).equals(jTextFieldid.getText()))
							{
								 
							}
						}
					}
				}
				if(n==3)
				{
					updata();
				}
				if(n==4)
				{
					preparedStatement=connection.prepareStatement("delete from num");
					preparedStatement.executeUpdate();
					preparedStatement=connection.prepareStatement("insert into num values(?,?,?)");
					for(int i=0;i<jTable.getRowCount();i++)
					{
						for(int j=0;j<3;j++)
						{
							preparedStatement.setString(j+1, (String)jTable.getValueAt(i, j));
						}
						preparedStatement.executeUpdate();
					}
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	void updata()
	{
		try {
			while(jTable.getRowCount()!=0)
			{
				jTableModel.removeRow(0);;
			}
			
			preparedStatement=connection.prepareStatement("select * from num");
			resultSet=preparedStatement.executeQuery();
			Vector rows=new Vector();
			while(resultSet.next())
			{
				int sum=0;
				Vector<String>vector=new Vector<String>();
				for(int i=1;i<=3;i++)
				{
					vector.add(resultSet.getString(i));
				}
				for(int i=0;i<rows.size();i++)
				{
					if(((Vector)rows.get(i)).get(0).equals(vector.get(0)))
					{
						sum=Integer.valueOf((String)((Vector)rows.get(i)).get(3));
					}
				}
				if(vector.get(1).equals("存入"))
					sum+=Integer.valueOf(vector.get(2));
				if(vector.get(1).equals("取出"))
					sum-=Integer.valueOf(vector.get(2));
				vector.add(String.valueOf(sum));
				rows.add(vector);
				jTableModel.addRow(vector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}