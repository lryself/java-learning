package com.homework;

public class Main {

    public static void main(String[] args) {
        int i;
        System.out.println("101-199的所有素数为：");
        for(i = 101; i<=199; i++)
        {
            if(check(i)) {
                System.out.print(" "+i);
            }
        }
    }
    public static boolean check(int a){
        int i;
        for(i = 2; i<a; i++)
        {
            if(a%i==0)
                return false;
        }
        return true;
    }
}