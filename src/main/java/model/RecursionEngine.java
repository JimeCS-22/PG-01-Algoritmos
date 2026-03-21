package model;

import java.util.*;

import static model.Recursion.fibonacci;

/**
 * Motor de recursividad: implementa factorial y Fibonacci
 * con captura del árbol de llamadas y memorización.
 */
public class RecursionEngine {

    //Nodo para un árbol de llamadas recursivas
    public static class CallNode {
        public final String label; //fact(3)
        public final int n;
        public final List<CallNode> children = new ArrayList<>();
        public long result = -1;
        public boolean fromMemo = false; //para fib con memorización
        public int depth;

        public CallNode(String label, int n, int depth) {
            this.label = label;
            this.n = n;
            this.depth = depth;
        }
    }

    //Datos de cada llamada recursiva - paso recursivo
    public static class Step {
        public final String description;
        public final String expression;
        public final long partialResult;
        public final boolean isMemo;
        public final int callCount; //contador de llamadas recursivas

        public Step(String description, String expression, long result, boolean memo, int calls) {
            this.description = description;
            this.expression = expression;
            this.partialResult = result;
            this.isMemo = memo;
            this.callCount = calls;
        }
    }

    //atributos del estado interno
    private final Map<Integer, Long> memo = new HashMap<>();
    private int callCount;
    private final List<Step> steps = new ArrayList<>();
    private CallNode treeRoot; //raíz del árbol de llamadas

    public void reset(){
        memo.clear();
        callCount = 0;
        steps.clear();
        treeRoot = null;
    }

    // FACTORIAL RECURSIVO
    /**
     * Calcula el factorial de un número entero no negativo utilizando recursión.
     * <p>
     * Este método inicializa el estado necesario para el cálculo, incluyendo
     * la estructura de seguimiento de llamadas (árbol de recursión) y el registro
     * de pasos. Luego delega el cálculo al método recursivo interno.
     * </p>
     *
     * <p>
     * Además, almacena el resultado final como un paso en la lista de ejecución,
     * incluyendo la representación textual del factorial calculado.
     * </p>*/
    public long computeFactorial(int n){
        reset();
        treeRoot = new CallNode("fact("+n+")", n, 0);
        long result = factorial(n, treeRoot, 0);
        steps.add(new Step("Resultado final", n+"! = "+result,
                result, false, callCount));
        return result;
    }

    /**
     * Método recursivo auxiliar que calcula el factorial de un número entero
     * y construye simultáneamente un árbol de llamadas junto con un registro
     * detallado de cada paso de ejecución.
     *
     * <p>
     * En cada llamada, se incrementa el contador de llamadas y se registra
     * un paso que representa la invocación actual. Luego:
     * </p>
     *
     * <ul>
     *   <li>Si {@code n <= 1}, se alcanza el caso base y se retorna 1.</li>
     *   <li>En caso contrario, se realiza una llamada recursiva con {@code n - 1},
     *       creando un nodo hijo en el árbol de llamadas.</li>
     *   <li>Al regresar de la llamada recursiva, se calcula el resultado como
     *       {@code n * factorial(n - 1)} y se registra el paso de retorno.</li>
     * </ul>
     */
    private long factorial(int n, CallNode parent, int depth) {
        callCount++;
        String label = "fact("+n+")";
        steps.add(new Step("Llamada No.: "+callCount+": "+label
                , buildFactExp(n), -1, false, callCount));

        if(n<=1){
            parent.result = 1;
            steps.add(new Step("Caso base: "+label+" = 1"
                    , "fact(1) = 1", 1, false, callCount));
            return 1;
        }

        CallNode child = new  CallNode("fact("+(n-1)+ ")", n-1, depth + 1);
        parent.children.add(child);
        long sub = factorial(n-1, child, depth + 1);
        long  result = (long) n * sub;
        parent.result = result;

        steps.add(new Step("Retorno: "+label+" = "+n+" * "+sub+" = "+result
                ,label+" = "+result
                , result, false, callCount));
        return result;
    }

    private String buildFactExp(int n) {
        if(n<=1) return "1";
        StringBuilder sb = new StringBuilder();
        for(int i = n; i >= 1; i--){
            sb.append(i);
            if(i>1) sb.append(" * ");
        }
        return sb.toString();
    }

    // GETTERS PARA ACCEDER A LOS OBJETOS
    public List<Step> getSteps(){return steps;}
    public CallNode getTreeRoot(){return treeRoot;}
    public int getCallCount(){return callCount;}
    public Map<Integer, Long> getMemo(){return Collections.unmodifiableMap(memo);}

    public long computeFibonacci(int n){

        reset();
        treeRoot = new CallNode("fib("+n+")", n, 0);
        long result = fibonacci(n, treeRoot, 0);

        steps.add(new Step("Resultado final", "fib( "+n+" ) = "+ result,
                result, false, callCount));

        return result;
    }

    /**
     * Método recursivo auxiliar que calcula el n-ésimo número de Fibonacci
     * utilizando recursión con memoización, mientras construye un árbol de
     * llamadas y registra cada paso de la ejecución.
     *
     * El cálculo sigue la definición clásica:
     *
     *   <li>{@code fib(0) = 0}</li>
     *   <li>{@code fib(1) = 1}</li>
     *   <li>{@code fib(n) = fib(n-1) + fib(n-2)}</li>
     *
     * Este método incluye optimización mediante memoización, almacenando
     * resultados previamente calculados en una estructura {@code memo} para
     * evitar recomputaciones innecesarias.
     * */
    private long fibonacci(int n, CallNode parent, int depth){

        callCount++;
        String label = "fib(" + n + ")";
        steps.add(new Step("Llamada No.: "+callCount+": "+label
                , buildFibExp(n), -1, false, callCount));
        //Caso base
        if(n<= 1){
            parent.result = n;

            steps.add(new Step("Caso base: " + label + " = " + n,
                    label + " = " + n, n, false, callCount));

            return n;
        }

        //Memo
        if (memo.containsKey(n)) {
            parent.result = memo.get(n);
            parent.fromMemo = true;

            steps.add(new Step("Resultado de cache: " + label + " = " + memo.get(n),
                    label + " = " + memo.get(n), memo.get(n), true, callCount));

            return parent.result;
        }

        //Llamadas recursivas
        CallNode left = new CallNode("fib(" + (n - 1) + ")", n - 1, depth + 1);
        CallNode right = new CallNode("fib(" + (n - 2) + ")", n - 2, depth + 1);

        parent.children.add(left);
        parent.children.add(right);

        long a = fibonacci(n - 1, left, depth + 1);
        long b = fibonacci(n - 2, right, depth + 1);

        long result = a + b;
        parent.result = result;

        // guardar en memo
        memo.put(n, result);

        steps.add(new Step("Retorno: " + label + " = " + a + " + " + b + " = " + result,
                label + " = " + result, result, false, callCount));

        return result;
    }

    private String buildFibExp(int n) {
        if (n <= 1) return String.valueOf(n);
        return "fib(" + (n - 1) + ") + fib(" + (n - 2) + ")";
    }

    public long computeFibonacciNoMemo(int n){
        reset();
        treeRoot = new CallNode("fib("+n+")", n, 0);

        long result = fibonacciNoMemo(n, treeRoot, 0);

        steps.add(new Step("Resultado final", "fib(" + n + ") = " + result,
                result, false, callCount));

        return result;
    }

    private long fibonacciNoMemo(int n, CallNode parent, int depth){

        callCount++;
        String label = "fib(" + n + ")";

        steps.add(new Step("Llamada No.: " + callCount + ": " + label,
                buildFibExp(n), -1, false, callCount));

        // Caso base
        if(n <= 1){
            parent.result = n;

            steps.add(new Step("Caso base: " + label + " = " + n,
                    label + " = " + n, n, false, callCount));

            return n;
        }


        CallNode left = new CallNode("fib(" + (n - 1) + ")", n - 1, depth + 1);
        CallNode right = new CallNode("fib(" + (n - 2) + ")", n - 2, depth + 1);

        parent.children.add(left);
        parent.children.add(right);

        long a = fibonacciNoMemo(n - 1, left, depth + 1);
        long b = fibonacciNoMemo(n - 2, right, depth + 1);

        long result = a + b;
        parent.result = result;

        steps.add(new Step("Retorno: " + label + " = " + a + " + " + b + " = " + result,
                label + " = " + result, result, false, callCount));

        return result;
    }
}




