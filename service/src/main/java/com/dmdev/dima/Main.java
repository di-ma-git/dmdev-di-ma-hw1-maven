package com.dmdev.dima;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        calculateSum(5, 6);
        calculateMulti(3, 4);


    }
    public static int calculateSum(int a, int b) {
        return a + b;
    }
    public static int calculateMulti(int a, int b) {
        return a * b;
    }
}