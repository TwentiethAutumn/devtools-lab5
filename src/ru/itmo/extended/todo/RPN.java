package ru.itmo.extended.todo;

import java.util.Stack;

public class RPN {
    public static int calculate(String expression){
        return evaluateRPN(convertToRPN(expression));
    }

    private static int evaluateRPN(String expression) {
        Stack<Integer> stack = new Stack<>();

        for (String token : expression.split(" ")) {
            if (isOperator(token)) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(evaluate(a, b, token));
            } else {
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") ||
                token.equals("*") || token.equals("/");
    }

    private static int evaluate(int a, int b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private static String convertToRPN(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                result.append(c).append(" ");
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (stack.peek() != '(') {
                    result.append(stack.pop()).append(" ");
                }
                stack.pop();
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && hasHigherPrecedence(stack.peek(), c)) {
                    result.append(stack.pop()).append(" ");
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
        }

        return result.toString().trim();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static boolean hasHigherPrecedence(char op1, char op2) {
        return (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-');
    }
}
