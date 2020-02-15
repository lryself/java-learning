public class runable {
    public static void main(String arg[])
    {
        Jishu jishu=new Jishu();
        Thread t1=new Thread(jishu);
        t1.start();
    }
}
class Jishu implements Runnable
{
    static int number;
    public void run()
    {
        while(number>=0&&number<=100)
        {
            if(number%2!=0)
            {
                System.out.println("ÆæÊı£º"+number+" ");
            }
            if(isprime(number))
            {
                System.out.println("ËØÊı£º"+number);
            }
            number++;
        }
    }
    private boolean isprime(int a)
    {
        if(a<2)
            return false;
        for(int i=2;i<a;i++)
        {
            if(a%i==0)
                return false;
        }
        return true;
    }
}