package 考试练习5;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.*;
import java.util.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class app1 {

	public static void main(String[] args) {
		new view();
	}

}

class view
{
	JFrame jFrame=new JFrame("信息统计");
	JPanel jPanelmain;
	JPanel jPanelNorth;
	JPanel jPanelSouth;
	JButton[]jButtons=new JButton[5];
	JTextField jTextFieldname=new JTextField();
	JTextField jTextFieldheigh=new JTextField();
	JRadioButton jRadioButton[]=new JRadioButton[2];
	ButtonGroup jButtonGroup=new ButtonGroup();
	JComboBox<String>jComboBox=new JComboBox<String>();
	JLabel jLabel[]=new JLabel[4];
	
	JTable jTable;
	DefaultTableModel defaultTableModel;
	JScrollPane jScrollPane;
	
	Connection connection;
	view() {
		initConnection();
		initJtable();
		
		initmain();
		initAction();
	}
	void initmain()
	{
		jPanelSouth=new JPanel(new GridLayout(1,5));
		jPanelSouth.add(jButtons[0]=new JButton("添加"));
		jPanelSouth.add(jButtons[1]=new JButton("删除"));
		jPanelSouth.add(jButtons[2]=new JButton("查找"));
		jPanelSouth.add(jButtons[3]=new JButton("刷新"));
		jPanelSouth.add(jButtons[4]=new JButton("保存到文件"));
		
		jPanelNorth=new JPanel(new GridLayout(1,9));
		jPanelNorth.add(jLabel[0]=new JLabel("姓名"));
		jPanelNorth.add(jTextFieldname);
		jPanelNorth.add(jLabel[1]=new JLabel("身高"));
		jPanelNorth.add(jTextFieldheigh);
		jPanelNorth.add(jLabel[2]=new JLabel("性别"));
		jPanelNorth.add(jRadioButton[0]=new JRadioButton("男"));
		jRadioButton[0].setSelected(true);
		jPanelNorth.add(jRadioButton[1]=new JRadioButton("女"));
		jButtonGroup.add(jRadioButton[0]);
		jButtonGroup.add(jRadioButton[1]);
		jPanelNorth.add(jLabel[3]=new JLabel("爱好"));
		jComboBox.addItem("唱歌");
		jComboBox.addItem("跳舞");
		jComboBox.addItem("篮球");
		jPanelNorth.add(jComboBox);
		
		
		jPanelmain=new JPanel(new BorderLayout());
		jFrame.add(jPanelmain);
		jPanelmain.add(jPanelNorth,BorderLayout.NORTH);
		jPanelmain.add(jPanelSouth,BorderLayout.SOUTH);
		jPanelmain.add(jScrollPane,BorderLayout.CENTER);
		
		jFrame.setSize(960,640);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
	}
	void initConnection()
	{
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			connection=DriverManager.getConnection("jdbc:Access:///F:/编程/java/考试练习5/src/考试练习5/data.accdb","","");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void initJtable()
	{
		try {
			Vector<String>columvVector=new Vector<String>();
			columvVector.add("姓名");
			columvVector.add("身高");
			columvVector.add("性别");
			columvVector.add("爱好");
			Vector rowsVector=new Vector();
			Scanner scanner=new Scanner(new File("F:\\编程\\java\\考试练习5\\src\\考试练习5\\data.txt"));
			while(scanner.hasNext())
			{
				int i=0;
				Vector<String>vector=new Vector<String>();
				vector.add(scanner.next());
				vector.add(scanner.next());
				vector.add(scanner.next());
				vector.add(scanner.next());
				rowsVector.add(vector);
			}
			defaultTableModel=new DefaultTableModel(rowsVector,columvVector);
			jTable=new JTable(defaultTableModel);
			jScrollPane=new JScrollPane(jTable);
			jTable.setSelectionMode(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void update()
	{
		while(jTable.getRowCount()>0)
		{
			defaultTableModel.removeRow(0);
		}
		try {
			PreparedStatement preparedStatement=connection.prepareStatement("select * from data1");
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Vector<String>vector=new Vector<String>();
				for(int i=1;i<=4;i++)
				{
					vector.add(resultSet.getString(i));
				}
				defaultTableModel.addRow(vector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jTable.setModel(defaultTableModel);
	}
	void update(String string)
	{
		while(jTable.getRowCount()>0)
		{
			defaultTableModel.removeRow(0);
		}
		try {
			PreparedStatement preparedStatement=connection.prepareStatement("select * from data1 where 姓名=?");
			preparedStatement.setString(1, string);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Vector<String>vector=new Vector<String>();
				for(int i=1;i<=4;i++)
				{
					vector.add(resultSet.getString(i));
				}
				defaultTableModel.addRow(vector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jTable.setModel(defaultTableModel);
	}
	void initAction()
	{
		try {
			for(int i=0;i<5;i++)
			{
				jButtons[i].addActionListener(new ActionButton());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				int selectint=jTable.getSelectedRow();
				jTextFieldname.setText((String)jTable.getValueAt(selectint, 0));
				jTextFieldheigh.setText((String)jTable.getValueAt(selectint, 1));
				if(((String)jTable.getValueAt(selectint, 2)).equals("男"))
				{
					jRadioButton[0].setSelected(true);
				}
				else {
					jRadioButton[1].setSelected(true);
				}
				jComboBox.setSelectedItem((String)jTable.getValueAt(selectint, 3));
			}
		});
	}
	class ActionButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			try {
				if(e.getSource()==jButtons[0])
				{
					PreparedStatement preparedStatement=connection.prepareStatement("insert into data1 values(?,?,?,?)");
					preparedStatement.setString(1, jTextFieldname.getText());
					preparedStatement.setString(2, jTextFieldheigh.getText());
					if(jRadioButton[0].isSelected())
						preparedStatement.setString(3, jRadioButton[0].getText());
					if(jRadioButton[1].isSelected())
						preparedStatement.setString(3, jRadioButton[1].getText());
					preparedStatement.setString(4, jComboBox.getItemAt(jComboBox.getSelectedIndex()));
					preparedStatement.executeUpdate();
					update();
				}
				if(e.getSource()==jButtons[1])
				{
					int selectint=JOptionPane.showConfirmDialog(jFrame, "确定删除吗", "警告", JOptionPane.OK_CANCEL_OPTION);
					if(selectint!=0)
						return;
					PreparedStatement preparedStatement=connection.prepareStatement("delete from data1 where 姓名=? AND 身高=? AND 性别=? AND 爱好=?");
					preparedStatement.setString(1, jTextFieldname.getText());
					preparedStatement.setString(2, jTextFieldheigh.getText());
					if(jRadioButton[0].isSelected())
						preparedStatement.setString(3, jRadioButton[0].getText());
					if(jRadioButton[1].isSelected())
						preparedStatement.setString(3, jRadioButton[1].getText());
					preparedStatement.setString(4, jComboBox.getItemAt(jComboBox.getSelectedIndex()));
					preparedStatement.executeUpdate();
					update();
				}
				if(e.getSource()==jButtons[2])
				{
					String string=JOptionPane.showInputDialog("请输入您要查询的学生姓名");
					update(string);
				}
				if(e.getSource()==jButtons[3])
				{
					update();
				}
				if(e.getSource()==jButtons[4])
				{
					JFileChooser jFileChooser=new JFileChooser();
					jFileChooser.showOpenDialog(jFrame);
					File file=jFileChooser.getSelectedFile();
					FileWriter fileWriter=new FileWriter(file);
					for(int i=0;i<jTable.getRowCount();i++)
					{
						String string="";
						for(int j=0;j<jTable.getColumnCount();j++)
						{
							string+=jTable.getValueAt(i, j)+" ";
						}
						string+="\n";
						fileWriter.write(string);
					}
					fileWriter.close();
					JOptionPane.showMessageDialog(jFrame, "保存成功");
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}