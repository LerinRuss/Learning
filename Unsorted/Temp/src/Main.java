import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> res = Arrays.asList(1, 2, 3);

        res.subList(1, 1).forEach(System.out::println);
    }
}
