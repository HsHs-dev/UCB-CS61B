import java.util.function.IntUnaryOperator;

public class HoF {

    public interface IntUnaryFunction {
        int apply(int x);
    }

    public static int doTwice(IntUnaryFunction f, int x) {
        return f.apply(f.apply(x));
    }

    public static void main(String[] args) {
        // anonymous class that implements IntUnaryFunction
        IntUnaryFunction tenX = new IntUnaryFunction() {
            public int apply(int x) {
                return x * 10;
            }
        };

        System.out.println(doTwice(tenX, 2));
    }
    
}
