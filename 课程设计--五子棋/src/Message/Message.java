package Message;
import java.io.*;
public class Message implements Serializable {
    public int kind;
    public String content;
    public int x,y;
    public void setMessage(int i,String s)
    {
        kind=i;
        content=s;
    }
    public void setMessage(int i,int a,int b)
    {
        kind=i;
        x=a;y=b;
    }
    public Message(){}
    public Message(int i,String s)
    {
        setMessage(i,s);
    }
    public Message(int i,int a,int b)
    {
        setMessage(i,a,b);
    }
}
