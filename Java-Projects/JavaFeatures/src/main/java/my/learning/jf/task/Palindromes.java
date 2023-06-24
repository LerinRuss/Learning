package my.learning.jf.task;

import java.util.*;

public class Palindromes {
    public static void main(String[] args) {
        /*
            "" or null - 0
            "        " - 8
            abba - 4
            absba - 5
            123 - 0
            12abba34 - 4
            12absba34 - 5
            12absba34jkkj56 - 5


            O(n1)|n2  +    O(n2)|n3 +         O(n3^2)|n4       + O(n4)
            find matches   map to intervals   collect pyramid    find max
            O(n)|n    +    O(n/2)             O(n^2/2) + O(1)
         */
        System.out.println("\"\" or null = 0, now is " + check(""));
        System.out.println("\"\" or null = 0, now is " + check(null));
        System.out.println("\"        \" = 8, now is " + check("        "));
        System.out.println("abba = 4, now is " + check("abba"));
        System.out.println("absba = 5, now is " + check("absba"));
        System.out.println("123 = 0, now is " + check("123"));
        System.out.println("12abba34 = 4, now is " + check("12abba34"));
        System.out.println("12absba34 = 5, now is " + check("12absba34"));
        System.out.println("12a5bsb6a34 = 5, now is " + check("12absba34jkkj56"));
        System.out.println("12absba34jkkj56 = 5, now is " + check("12absba34jkkj56"));
    }

    private static int check(String line) {
        if (line == null || line.isEmpty()) return 0;

        int max = 0;
        for (int i = 0; i < line.length(); i++) {
            for (int j = i + 1; j <line.length(); j++) {
                if (isPalindrome(line, i, j) && (j - i + 1) > max) {
                    max = j - i + 1;
                }
            }
        }

        return max;
    }

//
//    private static Map<Character, List<Integer>> mapMatchesOnCount(String line) {
//        var mapping = new HashMap<Character, List<Integer>>(line.length());
//
//        for (int i = 0; i < line.toCharArray().length; i++) {
//            char ch = line.charAt(i);
//
//            if (!mapping.containsKey(ch)) {
//                mapping.put(ch, new ArrayList<>());
//            }
//
//            mapping.get(ch).add(i);
//        }
//
//        return mapping;
//    }

    private static boolean isPalindrome(String line, int head, int tail) {
        for (; head < tail; head++, tail--) {
            if (line.charAt(head) != line.charAt(tail)) return false;
        }

        return true;
    }

    private static boolean isPalindrome(String line) {
        if (line == null || line.isEmpty()) return false;

        for (int head = 0, tail = line.length() - 1; head < tail; head++, tail--) {
            if (line.charAt(head) != line.charAt(tail)) return false;
        }

        return true;
    }
}
