import java.util.LinkedList;

public class Parser {
    public static String FormatOf(String str) {
        LinkedList<Character> arr = CastToArr(str);
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == ' ') {
                arr.remove(i);
                i--;
            }
        }
        return CastToStr(arr);
    }

    public static String FindLiterals(String expression) {
        String lit = "";
        int index1, index2;
        for (int i = 0; i < expression.length(); i++) {
            if (IsLiterLetter(expression.charAt(i))) {
                lit += expression.charAt(i);
                index1 = i;
                i++;
                if (i == expression.length()) {
                    expression = InsertInstead(expression, i - 1, i - 1, MathEl.DecodingLiteral(lit));
                    lit = "";
                    i = 0;
                    break;
                }
                for (int H = i; H < expression.length(); H++) {
                    if (IsLiterLetter(expression.charAt(H))) {
                        lit += expression.charAt(H);
                        if (H == (expression.length() - 1)) {
                            expression = InsertInstead(expression, index1, H, MathEl.DecodingLiteral(lit));
                            i = 0;
                            lit = "";
                            break;
                        }
                    } else {
                        index2 = H - 1;
                        expression = InsertInstead(expression, index1, index2, MathEl.DecodingLiteral(lit));
                        lit = "";
                        i = 0;
                        break;
                    }}}}
        return expression;
    }

    public static String SolveExpression(String expression) {
        String part = expression;
        String resultExpression = expression;
        LinkedList<Integer> isB;

        while (IsExpression(resultExpression)) {
            isB = IteratorsOfBracketsFinder(resultExpression);
            part = resultExpression;
            if (isB.get(0) == -1) {
                part = SolveWithoutBrackets(resultExpression);
                resultExpression = part;
            } else {
                part = SolveWithBrackets(part, isB.get(0), isB.get(1));
                resultExpression = InsertInstead(resultExpression, isB.get(0), isB.get(1), part);
            }

        }
        return resultExpression;
    }

    private static LinkedList<Integer> IteratorsOfBracketsFinder(String module) {

        int iteratorLeftBracket = -1, iteratorRightBracket = 0;
        for (int i = 0; i < module.length(); i++) {
            if (module.charAt(i) == ')') {
                iteratorRightBracket = i;
                break;
            }
        }
        for (int i = iteratorRightBracket; i >= 0; i--) {
            if (module.charAt(i) == '(') {
                iteratorLeftBracket = i;
                break;
            }
        }
        LinkedList<Integer> res = new LinkedList<Integer>();
        res.add(iteratorLeftBracket);
        res.add(iteratorRightBracket);
        return res;
    }

    private static String SolveWithBrackets(String part, int lI, int rI) {
        String xpart = "";
        for (int h = (lI + 1); h < rI; h++) {
            xpart = xpart + part.charAt(h);
        }
        return SolveWithoutBrackets(xpart);
    }

    private static String SolveWithoutBrackets(String part) {
        part = ProcessingFunction(part);
        part = ProcessingBinaryOperation(part);
        return part;
    }

    private static String ProcessingBinaryOperation(String part) {
        if (IsSimpleParams(part)) {
            return part;
        }
        for (int i = 0; i < part.length(); i++) {
            if (part.charAt(i) == '^') {
                part = BroadcastForBinaryOperation(part, i);
                i = 0;
            }
        }
        for (int i = 0; i < part.length(); i++) {
            if (part.charAt(i) == '*' || part.charAt(i) == '/' || part.charAt(i) == '%') {
                part = BroadcastForBinaryOperation(part, i);
                i = 0;
            }
        }
        for (int i = 0; i < part.length(); i++) {
            if (part.charAt(i) == '+' || part.charAt(i) == '-') {
                if (IsMinusUnary(part, i)) {
                    continue;
                }
                part = BroadcastForBinaryOperation(part, i);
                i = 0;}}
        return part;
    }
    private static String BroadcastForBinaryOperation(String part, int iterator) {
        LinkedList<Character> arr = CastToArr(part);
        int i = iterator - 1, iter = iterator;
        String leftStr = "", rightStr = "";
        while (IsNumEl(arr.get(i))) {
            leftStr = arr.get(i) + leftStr;
            arr.remove(i);
            i--;
            iter--;
            if (iter == 0) {
                break;}}
        if (iter > 0 && IsMinusUnary(CastToStr(arr), i)) {
            leftStr = arr.get(i) + leftStr;
            arr.remove(i);
            iter--;}
        i = (iter + 1);
        if (iter < (arr.size() - 1) && IsMinusUnary(CastToStr(arr), i)) {
            rightStr = rightStr + arr.get(i);
            arr.remove(i);}
        while (IsNumEl(arr.get(i))) {
            rightStr = rightStr + arr.get(i);
            arr.remove(i);
            if (iter == (arr.size() - 1)) {
                break;}}
        double leftNum = Double.parseDouble(leftStr);
        double rightNum = Double.parseDouble(rightStr);
        String result = MathEl.BinaryOperation(leftNum, arr.get(iter), rightNum);
        arr.remove(iter);
        arr = InsertTo(arr, iter, result);
        return CastToStr(arr);
    }
    private static String ProcessingFunction(String part) {
        String funcAndArgs = "";
        int index1, index2 = 0;

        for (int i = 0; i < part.length(); i++) {
            if (IsFunctionLetter(part.charAt(i))) {
                funcAndArgs = funcAndArgs + part.charAt(i);
                i++;
                index1 = i;
                for (int j = index1; j < part.length(); j++) {
                    if (IsNumEl(part.charAt(j - 1)) && !IsPr(part.charAt(j))) {
                        break;
                    }
                    funcAndArgs = funcAndArgs + part.charAt(j);
                    index2 = j;
                }
                part = InsertInstead(part, index1 - 1, index2-1, ParseFunc(CastToArr(funcAndArgs)));
                i = 0;
            }
        }
        return part;

    }

    private static String ParseFunc(LinkedList<Character> faa) {
        String function = "";
        LinkedList<String> argsStr = new LinkedList<String>();

        while (IsFunctionLetter(faa.get(0))) {
            function = function + faa.get(0);
            faa.remove(0);
        }
        String argument = "";
        for (int i = 0; i < faa.size(); i++) {
            if (faa.get(i) == ',') {
                argsStr.add(argument);
                argument = "";
            } else {
                argument = argument + faa.get(i);
            }
        }
        argsStr.add(argument);
        LinkedList<Double> args = new LinkedList<Double>();
        for (int j = 0; j < argsStr.size(); j++) {
            args.add(Double.parseDouble(argsStr.get(j)));
        }
        return MathEl.FunctionRunning(function, args);
    }

    private static boolean IsPr(Character c) {
        return (IsNumEl(c) || IsFunctionLetter(c) || c == ','|| IsLiterLetter(c));
    }

    public static boolean IsExpression(String expression) {
        if (IsNumEl(expression.charAt(0)) || expression.charAt(0) == '-') {

            for (int i = 1; i < expression.length(); i++) {
                if (!IsNumEl(expression.charAt(i))) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    public static boolean IsNumEl(Character c) {
        if ((c >= '0' && c <= '9') || c == '.') {
            return true;
        } else {
            return false;
        }
    }

    private static boolean IsMinusUnary(String expr, int iter) {
        if (expr.charAt(iter) == '-') {
            if (iter == 0) {
                return true;
            }
            if (IsNumEl(expr.charAt(iter - 1))) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean IsFunctionLetter(Character c) {
        return ((c >= 'a' && c <= 'z') || c == '_');
    }

    public static boolean IsLiterLetter(Character c) {
        return ((c >= 'A' && c <= 'Z'));
    }

    private static boolean IsSimpleParams(String part) {
        if (!IsExpression(part)) {
            return true;
        }
        for (int i = 0; i < part.length(); i++) {
            if (!(part.charAt(i) == ',' || IsNumEl(part.charAt(i))) || IsMinusUnary(part, i)) {
                return false;
            }
        }
        return true;
    }

    public static LinkedList<Character> InsertTo(LinkedList<Character> arr, int index, String word) {
        for (int i = 0; i < word.length(); i++) {
            arr.add((i + index), word.charAt(i));
        }
        return arr;
    }

    public static String InsertInstead(String str, int i1, int i2, String word) {
        LinkedList<Character> arr = CastToArr(str);
        for (int i = i2; i >= i1; i--) {
            arr.remove(i);
        }
        return CastToStr(InsertTo(arr, i1, word));
    }

    public static LinkedList<Character> CastToArr(String str) {
        LinkedList<Character> arr = new LinkedList<Character>();
        for (int i = 0; i < str.length(); i++) {
            arr.add(str.charAt(i));
        }
        return arr;
    }

    public static String CastToStr(LinkedList<Character> arr) {
        String str = "";
        for (int i = 0; i < arr.size(); i++) {
            str = str + arr.get(i);
        }
        return str;
    }
}
