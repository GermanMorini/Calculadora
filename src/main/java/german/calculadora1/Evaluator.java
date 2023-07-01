package german.calculadora1;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Evaluator {

    private HashMap<String, String> VARIABLES = new HashMap<>();
    private HashMap<String, Function<Double, Double>> UN_OPERATORS = new HashMap<>();
    private HashMap<String, BiFunction<Double, Double, Double>> BIN_OPERATORS = new HashMap<>();

    public Evaluator() {
        VARIABLES.put("pi", Math.PI+"");
        VARIABLES.put("π", Math.PI+"");
        VARIABLES.put("e", Math.E+"");

        UN_OPERATORS.put("abs", Math::abs);
        UN_OPERATORS.put("cos", Math::cos);
        UN_OPERATORS.put("sin", Math::sin);
        UN_OPERATORS.put("tan", Math::tan);
        UN_OPERATORS.put("r2d", rad -> rad*180/Math.PI);
        UN_OPERATORS.put("d2r", deg -> deg*Math.PI/180);
        UN_OPERATORS.put("sqrt", Math::sqrt);
        UN_OPERATORS.put("cbrt", Math::cbrt);

        BIN_OPERATORS.put("+", (a, b) -> a+b);
        BIN_OPERATORS.put("-", (a, b) -> a-b);
        BIN_OPERATORS.put("*", (a, b) -> a*b);
        BIN_OPERATORS.put("/", (a, b) -> a/b);
        BIN_OPERATORS.put("^", (a, b) -> Math.pow(a, b));
    }

    public double eval(String expression) {
        String exp = trimParethesis(expression);
        try {
            return Double.valueOf(exp);
        } catch (Exception e) {
            int op = getOperatorIndex(exp);
            String term1=exp.substring(0,op);
            String term2=exp.substring(op+1);

            return BIN_OPERATORS.get(exp.charAt(op)+"").apply(eval(term1), eval(term2));
        }
    }

    private int getOperatorIndex(String exp) {
        int opIndx=-1, opPriority=-1, i = 0, count = 0;

        for(char c : exp.toCharArray()) {
            if(count == 0) {
                if(c == '(') count++;
                else if(getOperatorPriority(c) > opPriority) {
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
    }

    private String trimParethesis(String exp) {
        if(exp.charAt(0) == '(' && exp.charAt(exp.length()-1) == ')') {
            String e = exp.substring(1);
            for(char c : e.toCharArray()) {
                if (c == '(') {
                    return exp;
                }
            }
            return e.substring(0, e.length()-1);
        }
        return exp;
    }

    private int getOperatorPriority(char op) {
        if(op == '+' || op == '-') return 2;
        else if(op == '*' || op == '/' || op == '%') return 1;
        else if(op == '^') return 0;
        return -1;
    }
}
