package 作业20191127;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.math.*;

public class 聊天应用 {

	public static void main(String[] args) {
		View view=new View("聊天应用");
	}
}

class View
{
	final int duankou=9999;
	
	JFrame jFrame=new JFrame();
	JPanel jPanel=new JPanel(new BorderLayout());
	JTextField jTextField=new JTextField();
	JTextArea jTextArea=new JTextArea();
	JPanel jPanel2=new JPanel(new BorderLayout());
	JButton jButton=new JButton("发送");
	JLabel jLabel=new JLabel();
	JLabel jLabel2=new JLabel();
	JPanel jPanel3=new JPanel(new GridLayout(3,1));
	JPanel jPanel4=new JPanel(new GridLayout(1,7));
	JLabel jLabel3[]=new JLabel[2];
	JRadioButton jRadioButton[]=new JRadioButton[2];
	ButtonGroup buttonGroup=new ButtonGroup();
	JTextField jTextField2=new JTextField("127.0.0.1");
	JTextField jTextField3=new JTextField(String.valueOf(duankou));
	JButton jButton2=new JButton("连接");
	String flag;
	Gopro gopro=new Gopro(this);
	
	View(String name) {
		jFrame.setSize(960,360);
		jFrame.setTitle(name);
		jFrame.add(jPanel);
		jPanel.add(jPanel3,BorderLayout.NORTH);
		jPanel3.add(jLabel);
		jPanel3.add(jLabel2);
		jPanel3.add(jPanel4);
		jPanel.add(jTextArea,BorderLayout.CENTER);
		jPanel.add(jPanel2,BorderLayout.SOUTH);
		jPanel2.add(jTextField,BorderLayout.CENTER);
		jPanel2.add(jButton,BorderLayout.EAST);
		
		jTextArea.setEnabled(false);
		jLabel3[0]=new JLabel("类型：");
		jLabel3[1]=new JLabel("ip和端口");
		jRadioButton[0]=new JRadioButton("客户端");
		jRadioButton[1]=new JRadioButton("服务器端");
		jPanel4.add(jLabel3[0]);
		jPanel4.add(jRadioButton[0]);
		jPanel4.add(jRadioButton[1]);
		buttonGroup.add(jRadioButton[0]);
		buttonGroup.add(jRadioButton[1]);
		jPanel4.add(jLabel3[1]);
		jPanel4.add(jTextField2);
		jPanel4.add(jTextField3);
		jTextField2.setEnabled(false);
		jTextField3.setEnabled(false);
		jButton2.setEnabled(false);
		jPanel4.add(jButton2);
		
		SetAction();
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
	}
	void SetAction()
	{
		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					writer.write(jTextField.getText());
					jTextArea.append("本机："+jTextField.getText()+"\n");
					jTextField.setText("");
				}
				catch (Exception q) {
					q.printStackTrace();
				}
			}
		});
		jRadioButton[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextField2.setEnabled(false);
				jTextField3.setEnabled(false);
				jButton2.setText("开始");
				flag="clien";
				jRadioButton[0].setEnabled(false);
				jRadioButton[1].setEnabled(false);
				
			}
		});
		jRadioButton[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ipAddress=InetAddress.getLocalHost();
					String locolip=ipAddress.getHostAddress();
					jLabel.setText("本机ip："+locolip);
				} catch (Exception e2) {
					// TODO: handle exception
				}
				jLabel2.setText("请输入您要连接的ip和端口");
				jTextField2.setEnabled(true);
				jTextField3.setEnabled(true);
				jButton2.setEnabled(true);
				jRadioButton[0].setEnabled(false);
				jRadioButton[1].setEnabled(false);
				flag="tcp";
			}
		});
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextField2.setEnabled(false);
				jTextField3.setEnabled(false);
				jButton2.setEnabled(false);
				gopro.start();
			}
		});
		jTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					writer.println(jTextField.getText());
					jTextArea.append("本机："+jTextField.getText()+"\n");
					jTextField.setText("");
				}
				catch (Exception q) {
					q.printStackTrace();
				}
			}
		});
	}
	
	InetAddress ipAddress;
	BufferedReader reader;
	PrintWriter writer;
	ServerSocket serverSocket;
	Socket socket;
	
	void SetNetwork() {
		try {
			ipAddress=InetAddress.getLocalHost();
			String locolip=ipAddress.getHostAddress();
			jLabel.setText(jLabel.getText()+"本机ip："+locolip+" 端口为："+duankou);
			
			serverSocket=new ServerSocket(duankou);
			jLabel2.setText("等待客户端的连接");
			socket=serverSocket.accept();
			jLabel2.setText("连接中");
			ipAddress=socket.getInetAddress();
			jLabel2.setText("已经连接到客户端，ip为："+ipAddress.getHostAddress());
			writer=new PrintWriter(socket.getOutputStream(),true);
			reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true)
			{
				if(socket!=null)
				{
					jTextArea.append("客户端："+reader.readLine()+"\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void Connect()
	{
		try {
				jLabel2.setText("尝试连接中");
				socket=new Socket(jTextField2.getText(),Integer.valueOf(jTextField3.getText()));
				ipAddress=socket.getInetAddress();
				jLabel2.setText("已经连接到服务器");
				writer=new PrintWriter(socket.getOutputStream(),true);
				reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while(true)
				{
					if(socket!=null)
					{
						jTextArea.append("服务器："+reader.readLine()+"\n");
					}
				}
		} catch (Exception e) {}
	}
}

class Gopro extends Thread
{
	View view;
	Gopro(View v) {
		view=v;
	}
	
	public void run()
	{
		if(view.flag.equals("clien"))
		{
			view.SetNetwork();
		}
		if(view.flag.equals("tcl"));
		{
			view.Connect();
		}
	}
}
