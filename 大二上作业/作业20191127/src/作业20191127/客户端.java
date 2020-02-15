package 作业20191127;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class 客户端 {

	public static void main(String[] args) {
		View1 view1=new View1("客户端");
	}

}
class View1
{
	final int duankou=9999;
	String ipnameString="127.0.0.1";
	
	JFrame jFrame=new JFrame();
	JPanel jPanel=new JPanel(new BorderLayout());
	JTextField jTextField=new JTextField();
	JTextArea jTextArea=new JTextArea();
	JPanel jPanel2=new JPanel(new BorderLayout());
	JButton jButton=new JButton("发送");
	JLabel jLabel=new JLabel();
	JLabel jLabel2=new JLabel();
	JPanel jPanel3=new JPanel(new GridLayout(2,1));
	
	
	View1(String name) {
		jFrame.setSize(960,360);
		jFrame.setTitle(name);
		jFrame.add(jPanel);
		jPanel.add(jPanel3,BorderLayout.NORTH);
		jPanel3.add(jLabel);
		jPanel3.add(jLabel2);
		jPanel.add(jTextArea,BorderLayout.CENTER);
		jPanel.add(jPanel2,BorderLayout.SOUTH);
		jPanel2.add(jTextField,BorderLayout.CENTER);
		jPanel2.add(jButton,BorderLayout.EAST);
		
		jTextArea.setEnabled(false);
		
		SetAction();
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
		
		Connect();
	}
	void SetAction()
	{
		jButton.addActionListener(new ActionListener() {
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
	void Connect()
	{
		try {
			ipAddress=InetAddress.getLocalHost();
			String locolip=ipAddress.getHostAddress();
			jLabel.setText("本机ip："+locolip+" 连接的服务器ip为："+ipnameString+" 端口为："+duankou);
			jLabel2.setText("尝试连接中");
			socket=new Socket(ipnameString,duankou);
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