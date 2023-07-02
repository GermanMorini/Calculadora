package german.calculadora1;

import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Evaluator {

    private HashMap<String, String> VARIABLES = new HashMap<>();
    private HashMap<String, Function<Double, Double>> UN_OPERATORS = new HashMap<>();
    private HashMap<String, BiFunction<Double, Double, Double>> BIN_OPERATORS = new HashMap<>();
    private HashMap<String, Integer> PRECEDENCE = new HashMap<>();

    public Evaluator() {
        /*
        Para definir una función se debe:
            1. Mapearla en operadores binarios o unarios según corresponda
            2. Establecer su orden de precedencia en "PRECEDENCE"
        */
        VARIABLES.put("pi", Math.PI+"");
        VARIABLES.put("π", Math.PI+"");
        VARIABLES.put("e", Math.E+"");

        // FUNCIONES BUILT-IN
        UN_OPERATORS.put("abs", Math::abs);
        UN_OPERATORS.put("cos", Math::cos);
        UN_OPERATORS.put("sin", Math::sin);
        UN_OPERATORS.put("tan", Math::tan);
        UN_OPERATORS.put("r2d", rad -> rad*180/Math.PI);
        UN_OPERATORS.put("d2r", deg -> deg*Math.PI/180);
        UN_OPERATORS.put("º", deg -> deg*Math.PI/180);
        UN_OPERATORS.put("√", Math::sqrt);
        UN_OPERATORS.put("sqrt", Math::sqrt);
        UN_OPERATORS.put("∛", Math::cbrt);
        UN_OPERATORS.put("cbrt", Math::cbrt);

        BIN_OPERATORS.put("=", Evaluator::equity);
        BIN_OPERATORS.put("+", (a, b) -> a+b);
        BIN_OPERATORS.put("-", (a, b) -> a-b);
        BIN_OPERATORS.put("*", (a, b) -> a*b);
        BIN_OPERATORS.put("/", (a, b) -> a/b);
        BIN_OPERATORS.put("^", (a, b) -> Math.pow(a, b));

        // ESTABLECEMOS EL ORDEN DE PRECEDENCIA
        PRECEDENCE.put("=",-2);
        PRECEDENCE.put("+",0);
        PRECEDENCE.put("-",0);
        PRECEDENCE.put("*",1);
        PRECEDENCE.put("/",2);
        PRECEDENCE.put("^",3);
        PRECEDENCE.put("r2d",4);
        PRECEDENCE.put("d2r",4);
        PRECEDENCE.put("º",4);
        PRECEDENCE.put("abs",4);
        PRECEDENCE.put("cos",4);
        PRECEDENCE.put("sin",4);
        PRECEDENCE.put("tan",4);
        PRECEDENCE.put("√",4);
        PRECEDENCE.put("sqrt",4);
        PRECEDENCE.put("∛",4);
        PRECEDENCE.put("cbrt",4);
    }

    private static Double equity(Double a, Double b) {
        if (a.equals(b)) {
            return 1d;
        } else {
            return 0d;
        }
    }

    public double eval(String expression) {
        String exp = trimParethesis(expression);

        try {
            return Double.valueOf(exp);
        } catch (Exception e) {
            Point2D opIndex = getOperatorIndexes(exp);
            String operator = exp.substring((int)opIndex.getX(), (int)opIndex.getY());

            if(BIN_OPERATORS.containsKey(operator)) {
                String term1=exp.substring(0, (int)opIndex.getX());
                String term2=exp.substring((int)opIndex.getY());

                return BIN_OPERATORS.get(operator).apply(eval(term1), eval(term2));
            } else {
                String param = exp.substring((int)opIndex.getY());

                return UN_OPERATORS.get(operator).apply(eval(param));
            }
        }
    }

    /*// Obtiene el índice del operador con mayor precedencia
    private int getOperatorIndex(String exp) {
        // "count" cuenta los paréntesis. Simpre debe ser 0, sino, hay un paréntesis abierto
        // "opIndx" indica el índice del operador con mayo precedencia
        // "opPriority" almacena el valor de ese operador
        int opIndx=-1, opPriority=1000, i = 0, count = 0;

        for(char c : exp.toCharArray()) {
            if(count == 0) {
                if(c == '(') count++;
                else if(getOperatorPriority(c) < opPriority) {
                    opIndx = i;
                    opPriority = getOperatorPriority(c);
                }
            } else {
                if(c == '(') count++;
                else if(c == ')') count--;
            }
            i++;
        }

        return opIndx;
    }*/

    private String trimParethesis(String exp) {
        /*if(exp.charAt(0) == '(' && exp.charAt(exp.length()-1) == ')') {
            String e = exp.substring(1);
            for(char c : e.toCharArray()) {
                if (c == '(') {
                    return exp;
                }
            }
            return e.substring(0, e.length()-1);
        }*/
        if(exp.charAt(0) == '(' && exp.charAt(exp.length()-1) == ')') {
            return exp.substring(1, exp.length()-1);
        }
        return exp;
    }

    private Point2D getOperatorIndexes(String exp) {
        String tmp;
        Point2D opIndex = new Point2D(-1, -1);
        int opPriority=100000, counter=0;

        for(int begin=0; begin < exp.length(); begin++) {
            if(exp.charAt(begin) == '(') {counter++; continue;}

            if(counter == 0) {
                for(int end=begin+1; end<exp.length() && end<begin+4; end++) {
                    tmp = exp.substring(begin, end);

                    if (isOperator(tmp) && (getOperatorPriority(tmp) < opPriority)) {
                            opIndex = new Point2D(begin, end);
                            opPriority = getOperatorPriority(tmp);
                    }
                }
            } else {
                if(exp.charAt(begin) == '(') counter++;
                if(exp.charAt(begin) == ')') counter--;
            }
        }
        return opIndex;
    }

    private boolean isOperator(String op) {
        return BIN_OPERATORS.containsKey(op) || UN_OPERATORS.containsKey(op);
    }

    private int getOperatorPriority(String op) {
        return PRECEDENCE.get(op);
    }

    /*private int getOperatorPriority(char op) {
        if(op == '+' || op == '-') return 0;
        else if(op == '*' || op == '%') return 1;
        else if(op == '/') return 2;
        else if(op == '^') return 3;
        return 1000;
    }*/
}
