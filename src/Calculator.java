import java.util.Stack;

import static java.lang.Double.parseDouble;

public class Calculator {
    public static void main (String a[]) {
        String expression = "2457*84/41+56-(21+56)";
        System.out.println("------------------------------------");
        System.out.println("Выражение:");
        System.out.println(expression);
        System.out.println(new Calculator().decide(expression));
        System.out.println("------------------------------------");
    }

    public double decide (String expression) {
        String prepared = preparingExpression(expression);
        String rpn = expressionToRun(prepared);
        return rpntoAnswer(rpn);
    }

    private String preparingExpression(String expression){
        String preparedExpression = new String();
        for(int sign=0; sign<expression.length(); sign++) {
            char symbol = expression.charAt(sign);
            if (symbol == '-') {
                if (sign == 0) {
                    preparedExpression += '0';
                }
                    else if (expression.charAt(sign-1) == '(')
                        preparedExpression += '0';
            }
            preparedExpression+=symbol;
        }
        return preparedExpression;
    }

    public String expressionToRun(String expression) {
        String current = "";
        Stack<Character> stack = new Stack<>();

        //проходимся по выражению посимвольно
        for (int i = 0; i < expression.length(); i++) {
           int priority = getPriority(expression.charAt(i)); //текущий элемент символа

            if (priority == 0)
                current += expression.charAt(i);

            if (priority == 1)
                stack.push(expression.charAt(i));

            if (priority > 1) {
                current += " ";
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) {
                    current += stack.pop();
                    }
                    else break;
                }
                stack.push(expression.charAt(i));
            }

            if (priority == -1) {
                current += " ";
                while (getPriority(stack.peek()) != 1) {
                    current += stack.pop();
                }
                stack.pop();
            }
        }

        while (!stack.empty())
            current += stack.pop();
            System.out.println("------------------------------------");
            System.out.println("Перевод в Обратную Польскую Нотацию:");
            System.out.println(current);
            System.out.println("------------------------------------");
            System.out.println("Ответ:");
            return current;
    }


    public double rpntoAnswer(String rpn) {
        String operand;
        Stack<Double> stack = new Stack<>();

        for (int i=0; i<rpn.length();i++) {
            if (rpn.charAt(i) == ' ')
                continue;

            if (getPriority(rpn.charAt(i)) == 0) {
                operand = new String();

                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length())
                        break;
                }
                stack.push(Double.parseDouble(operand));
            }

            if (getPriority(rpn.charAt(i)) > 1) {
                double a=stack.pop(), b=stack.pop();
                if (rpn.charAt(i) == '+') stack.push(b+a);
                if (rpn.charAt(i) == '-') stack.push(b-a);
                if (rpn.charAt(i) == '*') stack.push(b*a);
                if (rpn.charAt(i) == '/') stack.push(b/a);
            }
        }
        return stack.pop();
    }

    private int getPriority(char sign) {
        if (sign == '*' || sign == '/') return 3;
        else if (sign == '+' || sign == '-') return 2;
        else if (sign == '(') return 1;
        else if (sign == ')') return -1;
        else return 0;
    }
}