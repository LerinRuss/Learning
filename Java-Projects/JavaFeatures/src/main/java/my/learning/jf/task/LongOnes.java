package my.learning.jf.task;

import java.util.Arrays;

public class LongOnes {
    public static void main(String[] args) {
        // 0000000 - 0
        // 1111111 - 7
        // 1100110 - 2
        // 1000001 - 1
        // 1110011 - 3
        // 1100111 - 3
        // null or empty - 0

        System.out.println("0000000 = " + count("0000000"));
        System.out.println("1111111 = " + count("1111111"));
        System.out.println("1100110 = " + count("1100110"));
        System.out.println("1000001 = " + count("1000001"));
        System.out.println("1110011 = " + count("1110011"));
        System.out.println("1100111 = " + count("1100111"));
        System.out.println("Null = " + count(null));
        System.out.println("Blank  = " + count("   "));
        System.out.println("Empty = " + count(""));
    }

    private static int count(String zeroOnes) {
        if (zeroOnes == null || zeroOnes.isBlank()) {
            return 0;
        }

        int counter = 0;
        int res = 0;

        for (char c : zeroOnes.toCharArray()) {
            if (c == '1') counter++;
            else counter = 0;

            res = Math.max(counter, res);
        }

        return res;
    }
}
