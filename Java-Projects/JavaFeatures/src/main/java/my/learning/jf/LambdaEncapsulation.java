package my.learning.jf;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LambdaEncapsulation {
    private final String field = "Class field";

    public static void main(String[] args) {
        print(new LambdaEncapsulation().textFactory());
    }

    public static void print(Supplier<String> stringSupplier) {
        System.out.println(stringSupplier.get());
    }

    public Supplier<String> textFactory() {
        String field = "Method field";

        return () -> this.field;
    }
}
