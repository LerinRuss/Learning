package my.learning.jf.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DuplicatesAway {
    public static void main(String[] args) {
        /*
            [0, 1, 1, 2, 2] - [0, 1, 2]
            [] - []
            null - null
            [0,0,0,0,0] - [0]
            [0, 1, 2] - [0, 1, 2]
        */

        System.out.println("[0, 1, 1, 2, 2] - [0, 1, 2], res is " + filter(Arrays.asList(0, 1, 1, 2, 2)));
        System.out.println("[] - [], res is " + filter(List.of()));
        System.out.println("null - null, res is " + filter(null));
        System.out.println("[0,0,0,0,0] - [0], res is " + filter(Arrays.asList(0,0,0,0,0)));
        System.out.println("[0, 1, 2] - [0, 1, 2], res is " + filter(Arrays.asList(0, 1, 2)));
    }

    private static List<Integer> filter(List<Integer> nums) {
        if (nums == null || nums.isEmpty()) return nums;

        var res = new ArrayList<Integer>(nums.size());
        res.add(nums.get(0));

        for (Integer num : nums) {
            if (!res.get(res.size() - 1).equals(num)) {
                res.add(num);
            }
        }

        return res;
    }
}
