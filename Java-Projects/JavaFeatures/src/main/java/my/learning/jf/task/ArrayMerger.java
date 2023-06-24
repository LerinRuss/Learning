package my.learning.jf.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayMerger {
    public static void main(String[] args) {
        /*
            [] or null - [] or null
            [[0, 1, 2], [3, 4, 5], [6, 7, 8]] - [0, 1, 2, 3, 4, 5, 6, 7, 8]
            [[0, 0, 5], [3, 4], []] - [0, 0, 3, 4, 5]
            [[0, 0, 1, 1, 10], [2], [4, 9, 9], [5, 5, 6]] - [0, 0, 1, 1, 2, 4, 5, 5, 6, 9, 9, 10]
         */

        System.out.println("[] or null - [] or null, res is " + merge(List.of()));
        System.out.println("[] or null - [] or null, res is " + merge(null));
        System.out.println("[[0, 1, 2], [3, 4, 5], [6, 7, 8]] - [0, 1, 2, 3, 4, 5, 6, 7, 8], res is " + merge(
                Arrays.asList(
                        Arrays.asList(0, 1, 2),
                        Arrays.asList(3, 4, 5),
                        Arrays.asList(6, 7, 8))));
        System.out.println("[[0, 0, 5], [3, 4], []] - [0, 0, 3, 4, 5], res is " + merge(
                Arrays.asList(
                        Arrays.asList(0, 0, 5),
                        Arrays.asList(3, 4),
                        Collections.emptyList())));
        System.out.println("[[0, 0, 1, 1, 10], [2], [4, 9, 9], [5, 5, 6]] - [0, 0, 1, 1, 2, 4, 5, 5, 6, 9, 9, 10], res is " + merge(
                Arrays.asList(
                        Arrays.asList(0, 0, 1, 1, 10),
                        List.of(2),
                        Arrays.asList(4, 9, 9),
                        Arrays.asList(5, 5, 6))));
    }

    private static List<Integer> merge(List<List<Integer>> sequences) {
        if (sequences == null || sequences.isEmpty()) return Collections.emptyList();

        int commonNumber = sequences.stream().mapToInt(List::size).sum();
        var pointers = new int[sequences.size()];
        var res = new ArrayList<Integer>(commonNumber);

        while (res.size() < commonNumber) {
            int min = Integer.MAX_VALUE, pointer = 0;

            for (int i = 0; i < pointers.length; i++) {
                if (pointers[i] >= sequences.get(i).size()) continue;

                int seqValue = sequences.get(i).get(pointers[i]);
                if (seqValue < min) {
                    min = seqValue;
                    pointer = i;
                }
            }

            res.add(min);
            pointers[pointer]++;
        }

        return res;
    }
}
