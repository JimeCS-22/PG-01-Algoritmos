package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static model.Recursion.fibonacci;
import static org.junit.jupiter.api.Assertions.*;

class RecursionTest {

    @Test
    void factorialTest() {
        int [] list = {5,10,12,15,20};

        for (int n : list) {
            AtomicInteger counter = new AtomicInteger(0); // Contador para llamadas recursivas
            long t1 = System.nanoTime();
            long result = Recursion.factorial(n, counter);
            long t2 = System.nanoTime();

            System.out.println("El factorial de " + n + " es " + util.Utility.format(result) +
                    "\nTotal recursive calls: " + util.Utility.format(counter.get()) +
                    "\nT(n): " + util.Utility.format(t2-t1) + " ns \n"

            );
        }

    }

    @Test
    void fibonacciTest(){

        int [] list = {5,10,12,15,20, 30};

        for (int n : list) {
            AtomicInteger counter = new AtomicInteger(0); // Contador para llamadas recursivas
            long t1 = System.nanoTime();
            long result = fibonacci(n, counter);
            long t2 = System.nanoTime();

            System.out.println("El fibonacci de " + n + " es " + util.Utility.format(result) +
                            "\nTotal recursive calls: " + util.Utility.format(counter.get()) +
                             "\nT(n): " + util.Utility.format(t2-t1) + " ns \n"

            );
        }
    }

    @Test
    void fibMemoTest(){

        int [] list = {5,10,12,15,20};

        for (int n : list) {
            AtomicInteger counter = new AtomicInteger(0); // Contador para llamadas recursivas
            Map<Integer, Long> memo = new HashMap<>(); // Cache para resultados ya calculados

            long t1 = System.nanoTime();
            long result = Recursion.fibMemo(n, memo ,counter);
            long t2 = System.nanoTime();

            System.out.println("Fibonacci MemoHasMap " + n + " is " + util.Utility.format(result) +
                    "\nTotal recursive calls: " + util.Utility.format(counter.get()) +
                    "\nT(n): " + util.Utility.format(t2-t1) + " ns \n"

            );
        }
    }

    @Test
    void fibMemoArrayTest(){

        int [] list = {5,10,12,15,20};

        for (int n : list) {
            AtomicInteger counter = new AtomicInteger(0); // Contador para llamadas recursivas
            long [] memo = new long[n+1]; // Cache para resultados ya calculados
            long t1 = System.nanoTime();
            long result = Recursion.fibMemoArray(n, memo ,counter);
            long t2 = System.nanoTime();

            System.out.println("Fibonacci MemoArray " + n + " is " + util.Utility.format(result) +
                    "\nTotal recursive calls: " + util.Utility.format(counter.get()) +
                    "\nT(n): " + util.Utility.format(t2-t1) + " ns \n"

            );
        }
    }

    @Test
    void arraySum(){
        int [] a = {3, 5, 2, 7, 1};

        for (int n : a){

            AtomicInteger counter = new AtomicInteger(0); // Contador para llamadas recursivas
            long [] memo = new long[n+1]; // Cache para resultados ya calculados
            long t1 = System.nanoTime();
            int resultado = Recursion.arraySum(a, 0, counter);
            long t2 = System.nanoTime();

            // Usamos Arrays.toString(a) para ver el contenido
            System.out.println("El resultado de " + Arrays.toString(a) + " es: " + resultado +
                     "\nTotal recursive calls: " + util.Utility.format(counter.get()) +
                    "\nT(n): " + util.Utility.format(t2-t1) + " ns \n");

        }

    }

    @Test
    void sumDigit() {
        int[] a = {20, 100, 1000, 5000, 10000};

        for (int n : a) {
            long t1 = System.nanoTime();

            try {
                int result = Recursion.sumDigit(n);
                long t2 = System.nanoTime();

                System.out.println("sumDigits(" + n + ") = " + result +
                        "\nT(n): " + util.Utility.format(t2 - t1) + " ns\n");

            } catch (StackOverflowError e) {
                long t2 = System.nanoTime();
                System.out.println("sumDigits(" + n + ") => StackOverflowError" +
                        "\nT(n): " + util.Utility.format(t2 - t1) + " ns\n");
            }
        }

    }

}