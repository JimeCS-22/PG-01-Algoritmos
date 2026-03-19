package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Motor de recursividad: implementar factorial y fibonacci en javaFX
 */
public class RecursionEngine {

    //nodo para un árbol de llamadas recursivas
    public static class CallNode{
        public final String label; //fact(3)
        public final int n;
        public final List<CallNode> children = new ArrayList<>();
        public long result = -1;
        public boolean fromemo = false; //para fib con memorización
        public int depth;

        public CallNode(String label, int n, long result, boolean fromemo, int depth) {
            this.label = label;
            this.n = n;
            this.depth = depth;
        }
    }

    //Datos de cada llamada recursiva - Paso Recursivo
    public static class Step{
        public final String description;
        public final String expression;
        public final long partialResult;
        public final boolean isMemo;
        public final int callCount; // Contador de llamadas recursivas

        public Step(String description, String expression, long result, boolean memo, int call) {
            this.description = description;
            this.expression = expression;
            this.partialResult = result;
            this.isMemo = memo;
            this.callCount = call;
        }
    }

    //Atributos del estado interno
    private final Map<Integer, Long> memo = new HashMap<>();
    private int callCount;
    private final List<Step> steps = new ArrayList<>();
    private CallNode treeRoot; //Raiz del arbol de llamadas

    public void reset(){
        memo.clear();
        callCount = 0;
        steps.clear();
        treeRoot = null;
    }
}
