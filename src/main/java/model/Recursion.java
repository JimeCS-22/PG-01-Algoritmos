package model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Recursion {

    public static long factorial(int n){

        if (n==0) return 1;
        return n * factorial(n-1);
    }



    public static long fibonacci(int n, AtomicInteger counter){

        counter.incrementAndGet();//La idea es contar cada llamada recursiva

        if (n<= 1) return n;

        return fibonacci(n-1, counter) + fibonacci(n-2, counter);

    }

    /*Fibonacci con HaspMap */
    public static long fibMemo(int n, Map<Integer, Long> memo){
        if (n<=1) return n;

        if (memo.containsKey(n)) return memo.get(n);//Cache

        long result = fibMemo(n-1, memo) + fibMemo(n-2, memo);
        memo.put(n, result);//Guardar en cache
        return result;
    }

    /*Fibonacci con memoriazacion usando arreglos*/
    public static long fibMemoArray(int n, long[] memo){
        if (n<=1) return n;

        if (memo[n] != -1) return memo[n]; //Si tiene un result almacendo


        memo[n] = fibMemoArray(n-1, memo) + fibMemoArray(n-2, memo);
        return memo[n];
    }

    /* Ejercicio de muñecas*/
    public static void matryoshka(int n){

        if (n<=1){

            System.out.println("Abriendo la muñeca más pequeña");
            return;
        }

        System.out.println("Abriendo la muñeca número: "+ n);
        matryoshka(n-1);
    }






}
