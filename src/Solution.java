import java.util.ArrayList;
import java.util.Stack;

//source:
//https://wxx5433.gitbooks.io/interview-preparation/content/part_ii_leetcode_lintcode/stack/convert_expression_to_reverse_polish_notation.html

public class Solution {
    /*
     * @param expression: A string array
     * @return: The Reverse Polish notation of this expression
     */
    public ArrayList<String> convertToRPN(String[] expression) {
        ArrayList<String> result = new ArrayList<>();
        if (expression == null || expression.length == 0) {
            return result;
        }
        Stack<String> opStack = new Stack<>();
        for (String token : expression) {
            //Character.isLetterOrDigit(char)
//            if (isNumber(token)) {
            if (Character.isLetterOrDigit(token.charAt(0))) {
                result.add(token);
            } else if (token.equals("(")) {
                opStack.push(token);
            } else if (token.equals(")")) {
                while (!opStack.peek().equals("(")) {
                    result.add(opStack.pop());
                }
                opStack.pop();
            } else {
                while (!opStack.isEmpty() && getPriority(opStack.peek()) >= getPriority(token)) {
                    result.add(opStack.pop());
                }
                opStack.push(token);
            }
        }
        while (!opStack.isEmpty()) {
            result.add(opStack.pop());
        }
        return result;
    }

    private boolean isNumber(String token) {
        return Character.isDigit(token.charAt(0));
    }

    private int getPriority(String op) {
        switch (op) {
            case "(":
                return -1;
            case "&&":
            case "||":
                return 0;
            case "+":
            case "-":
                return 1;
            default:
                return 2;
        }
    }

//    private int getPriority(String op) {
//        if (op.equals("(")) {
//            return 0;
//        } else if (op.equals("+") || op.equals("-")) {
//            return 1;
//        } else {
//            return 2;
//        }
//    }
}