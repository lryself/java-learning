package com.company;
import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int i,min,max;
        System.out.println("请输入两个整数，用空格隔开");
        int n = sc.nextInt();
        int m = sc.nextInt();
        if(m>n)
            i=n;
        else
            i=m;
        for(; i>=2; i--)
        {
            if(m%i==0)
                if(n%i==0)
                    break;
        }
        min=i;
        max=m*n/min;
        System.out.println(m+"和"+n+"的最大公约数为"+min+";"+"最小公倍数为"+max+";");
    }
}