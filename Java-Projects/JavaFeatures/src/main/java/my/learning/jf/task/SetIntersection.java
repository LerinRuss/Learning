package my.learning.jf.task;

import java.util.HashSet;

public class SetIntersection {
    public static void main(String[] args) {
        /*
            j = qwerd s = qwerd - 5
            j = 123qwerd456 s = qwerd - 5
            j = 123 s = 456 - 0
            j = 1q2w3e s = qwe - 3
            j = qwe s = rqwet - 3
            one of them or both is empty - 0
            one of them or both is null - 0
         */
        System.out.println("j = qwerd s = qwerd - 5, res is " + count("qwerd", "qwerd"));
        System.out.println("j = 123qwerd456 s = qwerd - 5, res is " + count("123qwerd456", "qwerd"));
        System.out.println("j = 123 s = 456 - 0, res is " + count("123", "456"));
        System.out.println("j = 1q2w3e s = qwe - 3, res is " + count("1q2w3e", "qwe"));
        System.out.println("j = qwe s = rqwet - 3, res is " + count("qwe", "rqwet"));
        System.out.println("one of them or both is empty - 0, res is " + count("213", ""));
        System.out.println("one of them or both is empty - 0, res is " + count("", "213"));
        System.out.println("one of them or both is empty - 0, res is " + count("", ""));
        System.out.println("one of them or both is null - 0, res is " + count(null, "123"));
        System.out.println("one of them or both is null - 0, res is " + count("123", null));
        System.out.println("one of them or both is null - 0, res is " + count(null, null));
    }

    private static int count(String source, String potentialInclusions) {
        if ((source == null || source.isEmpty()) || (potentialInclusions == null || potentialInclusions.isEmpty())) {
            return 0;
        }

        var sourceSet = new HashSet<>(source.length());
        for (char c : source.toCharArray()) {
            sourceSet.add(c);
        }

        int count = 0;

        for (char c : potentialInclusions.toCharArray()) {
            if (sourceSet.contains(c)) count++;
        }

        return count;
    }

}
