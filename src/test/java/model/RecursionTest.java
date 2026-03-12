package model;

import org.junit.jupiter.api.Test;

import static model.Recursion.fibonacci;
import static org.junit.jupiter.api.Assertions.*;

class RecursionTest {

    @Test
    void factorialTest() {
        int n = 5;

        long t1 = System.nanoTime();
        long result = Recursion.factorial(n);
        long t2 = System.nanoTime();

        System.out.println("El factorial de " + n + " es " + result + "\nT(n): "+ util.Utility.format(t2-t1) + " ns" );


    }

    @Test
    void fibonacciTest(){

        int n = 10;

        long t1 = System.nanoTime();
        long result = fibonacci(n);
        long t2 = System.nanoTime();

        System.out.println("El fibonacci de: " + n + " es " + result + "\n T(n): " + util.Utility.format(t2-t1) + " ns ");
    }


}