import java.util.Scanner;

public class Command {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.next();
            str = Parser.FormatOf(str);
            StartRhenium(str);
        }
    }

    public static void StartRhenium(String command) {
        if (command.length() >= 2) {
            if (Parser.IsLiterLetter(command.charAt(0)) && Parser.IsFunctionLetter(command.charAt(1))) {
                DefineFunction(command);
            } else {
                if (IsVariableExpression(command)) {
                    SolveVariableExpression(command);
                } else {
                    SolveExpression(command);
                }
            }
        } else {
            SolveExpression(command);
        }
    }
    private static void SolveExpression(String expression) {
        expression = Parser.FindLiterals(expression);
        System.out.println(Parser.SolveExpression(expression));
    }

    private static void SolveVariableExpression(String expression) {
    }

    private static boolean IsVariableExpression(String expression) {
        return false;
    }

    private static void DefineFunction(String expression) {
    }
}