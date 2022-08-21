import java.util.LinkedList;

public class MathEl {
    public static String BinaryOperation(double first, char operand, double second) {
        Double result;
        switch (operand) {
            case '+':
                result = first + second;
                break;
            case '-':
                result = first - second;
                break;
            case '*':
                result = first * second;
                break;
            case '/':
                result = first / second;
                break;
            default:
                result = 0.0;
        }
        return result.toString();
    }

    public static String FunctionRunning(String func, LinkedList<Double> args) {
        Double result;
        switch (func) {
            case "sqrt":
                result = Math.sqrt(args.get(0));
                break;
            case "cbrt":
                result = Math.cbrt(args.get(0));
                break;
            case "abs":
                result = Math.abs(args.get(0));
                break;
            case "pow":
                result = Math.pow(args.get(0), args.get(1));
                break;
            case "round":
                result = Double.parseDouble(((Long) (Math.round(args.get(0)))).toString());
                break;
            case "ceil":
                result = Math.ceil(args.get(0));
                break;
            case "floor":
                result = Floor(args.get(0));
            case "exp":
                result = Math.exp(args.get(0));
                break;
            case "expf":
                result = Math.expm1(args.get(0));
            case "log":
                result = Math.log(args.get(0));
            case "logfp":
                result = Math.log1p(args.get(0));
            case "logten":
                result = Math.log10(args.get(0));
            case "sin":
                result = Math.sin(args.get(0));
            case "sinh":
                result = Math.sinh(args.get(0));
            case "asin":
                result = Math.asin(args.get(0));
            case "cos":
                result = Math.cos(args.get(0));
            case "cosh":
                result = Math.cosh(args.get(0));
                break;
            case "acos":
                result = Math.acos(args.get(0));
                break;
            case "tg":
                result = Math.tan(args.get(0));
                break;
            case "tgh":
                result = Math.tanh(args.get(0));
            case "atg":
                result = Math.acos(args.get(0));
                break;
            case "to_degrees":
                result = Math.toDegrees(args.get(0));
                break;
            case "to_radians":
                result = Math.toRadians(args.get(0));
                break;
            case "scalb":
                result = Math.scalb(args.get(0), Integer.parseInt(((Long) Math.round(args.get(1))).toString()));
                break;
            case "sign":
                result = Math.signum(args.get(0));
                break;
            case "fact":
                result = Factorial(Integer.parseInt(((Long) Math.round(args.get(0))).toString()));
                break;
            case "max":
                result = FindMax(args);
                break;
            case "min":
                result = FindMin(args);
            default:
                result = 0.0;
                break;
        }
        return Double.toString(result);
    }

    private static double Factorial(int a) {
        Integer p = 1;
        for (int i = 1; i <= a; i++) {
            p *= i;
        }
        return Double.parseDouble(p.toString());
    }

    private static Double FindMax(LinkedList<Double> args) {
        double max = Double.NEGATIVE_INFINITY;
        for (Double el : args) {
            if (el > max) {
                max = el;
            }
        }
        return max;
    }

    private static Double FindMin(LinkedList<Double> args) {
        double min = Double.POSITIVE_INFINITY;
        for (Double el : args) {
            if (el < min) {
                min = el;
            }
        }
        return min;
    }

    private static Double Floor(Double a) {
        String s = a.toString(), r = "";
        System.out.println(s);
        Double res;
        if (((s.charAt(s.length() - 1)) == '0') && ((s.charAt(s.length() - 2)) == '.')) {
            return a;
        } else {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '.') {
                    break;
                } else {
                    r += s.charAt(i);
                }
            }
            System.out.println(r);
            res = Double.parseDouble(r);
            if (res < 0.0) {
                res--;
            }
        }
        return res;
    }

    public static String DecodingLiteral(String lit) {
        Double res = 0.0;
        switch (lit) {
            case "PI":
                res = Math.PI;
                break;
            case "E":
                res = Math.E;
                break;
            default:
                res = 0.0;
                break;

        }
        return (res).toString();
    }
}
