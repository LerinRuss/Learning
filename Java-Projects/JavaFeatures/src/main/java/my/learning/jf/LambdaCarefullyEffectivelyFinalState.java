package my.learning.jf;

public class LambdaCarefullyEffectivelyFinalState {
    public static void main(String[] args) {
        int[] integers = new int[1];
        System.out.printf("Before: %d\n", integers[0]);

        Runnable runnable = () -> integers[0]++;
        runnable.run();

        System.out.printf("After: %d\n", integers[0]);
    }
}
