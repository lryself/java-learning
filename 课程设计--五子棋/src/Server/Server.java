package Server;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.net.ServerSocket;
import java.util.Optional;
import java.util.Stack;
import Message.*;

public class Server extends Application implements Runnable{
    BorderPane mainPane=new BorderPane();
    ScrollPane centerPane=new ScrollPane();
    TextArea textArea=new TextArea();
    Stack<User>users=new Stack<>();
    int portnum=8189;
    public void run(){
        textArea.appendText("服务器开始工作……\n");
        try
        {
            textArea.appendText("本机ip为："+InetAddress.getLocalHost());
            textArea.appendText("\n监听端口为："+portnum+"\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            ServerSocket serverSocket=new ServerSocket(portnum);
            while(true)
            {
                Socket socket=serverSocket.accept();
                users.push(new User(socket,this));
                users.peek().start();
                textArea.appendText("新的玩家已加入，");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    void initmainPane()
    {
        centerPane.setContent(textArea);
        centerPane.setFitToHeight(true);
        centerPane.setFitToWidth(true);
        mainPane.setCenter(centerPane);
    }
    public void start(Stage stage) throws Exception {
        initmainPane();
        TextInputDialog dialog = new TextInputDialog("8189");
        dialog.setTitle("设置服务器端口");
        dialog.setHeaderText("请输入你服务器的端口：");
        dialog.setContentText("8000~10000");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            portnum=Integer.valueOf(result.get());
        }
        else return;
        new Thread(this).start();
        Scene scene=new Scene(mainPane,300,300);
        stage.setScene(scene);
        stage.setTitle("五子棋游戏服务器");
        stage.setResizable(false);
        stage.getIcons().add(new Image("image/icon.png"));
        stage.show();
    }
}
class User extends Thread
{
    String username;
    String roomname;
    private Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    Server server;
    User enemy=null;
    Message acceptMessage=new Message();
    User(Socket socket,Server server)
    {
        this.server=server;
        try
        {
            this.socket=socket;
            inputStream=new ObjectInputStream(socket.getInputStream());
            outputStream=new ObjectOutputStream(socket.getOutputStream());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    void searchenemy()
    {
        for(int i=0;i<(server.users.size()-1);i++)
        {
            if(server.users.get(i).roomname.equals(roomname))
            {
                enemy=server.users.get(i);
                server.users.get(i).enemy=this;
                server.textArea.appendText(username+"已找到对手\n");
                acceptMessage.setMessage(0,"连接成功");
                try
                {
                    outputStream.writeObject(acceptMessage);
                    send(acceptMessage);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    void handing(Message message)
    {
        try
        {
            server.textArea.appendText(username+":"+message.kind+" "+message.content+"\n");
            if(message.kind==0)//设置用户姓名及对手姓名并连接至成功
            {
                String temp[]=message.content.split("/");
                username=temp[0];
                roomname=temp[1];
                server.textArea.appendText("昵称是:"+username+" 对战房间名是："+roomname+"\n");
                searchenemy();
            }
            if(message.kind==1)//开始，认输，悔棋
            {
                if(message.content.equals("开始"))
                {
                    acceptMessage=message;
                    if(acceptMessage.content.equals(enemy.acceptMessage.content)&&acceptMessage.kind==enemy.acceptMessage.kind)
                    {
                        acceptMessage.setMessage(1,"白色");
                        outputStream.writeObject(acceptMessage);
                        acceptMessage.setMessage(1,"黑色");
                        send(acceptMessage);
                    }
                }
                if(message.content.equals("认输"))
                {
                    acceptMessage.setMessage(1,"对手认输");
                    send(acceptMessage);
                }
                if(message.content.equals("请求悔棋"))
                {
                    acceptMessage.setMessage(1,"请求悔棋");
                    send(acceptMessage);
                }
                if(message.content.equals("同意悔棋"))
                {
                    acceptMessage.setMessage(1,"同意悔棋");
                    outputStream.writeObject(new Message(1,"同意悔棋"));
                    send(acceptMessage);
                }
            }
            if(message.kind==2)//发送消息
            {
                acceptMessage.setMessage(2,username+"（对手）"+":"+message.content+"\n");
                send(acceptMessage);
            }
            if(message.kind==3)//棋子落点
            {
                server.textArea.appendText(username+":"+message.kind+" "+message.x+" "+message.y+"\n");
                send(message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    void send(Message message)
    {
        try
        {
            Message message2=new Message(message.kind,message.content);
            message2.setMessage(message.kind,message.x,message.y);
            enemy.outputStream.writeObject(message2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void run()
    {
        while(true)
        {
            try
            {
                handing((Message)inputStream.readObject());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                server.textArea.appendText(username+" 玩家退出\n");
                acceptMessage.setMessage(1,"对手认输");
                send(acceptMessage);
                server.users.remove(this);
                break;
            }
        }
    }
    void setName(String string1,String string2)
    {
        username=string1;
        roomname=string2;
    }
}