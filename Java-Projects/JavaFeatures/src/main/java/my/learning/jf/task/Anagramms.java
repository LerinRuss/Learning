package my.learning.jf.task;

import java.util.HashMap;

public class Anagramms {
    public static void main(String[] args) {
        // qwerty and qwerty - true
        // qwerty and ytrewq - true
        // qwerty and qywter - true
        // qwerty and oooqwerty - false
        // qwerty and 0qwerty0 - false
        // qwerty and qwerty00 - false
        // qwerty and awsedrf - false
        // both null or empty - true
        // one of them is null, empty or have different size - false

        System.out.println("qwerty and qwerty - true, now is " + check("qwerty", "qwerty"));
        System.out.println("qwerty and ytrewq - true, now is " + check("qwerty", "ytrewq"));
        System.out.println("qwerty and qywter - true, now is " + check("qwerty", "qywter"));
        System.out.println("qwwwerty and qywwwter - true, now is " + check("qwwwerty", "qywwwter"));
        System.out.println("qwerty and oooqwerty - false, now is " + check("qwerty", "oooqwerty"));
        System.out.println("qwerty and 0qwerty0 - false, now is " + check("qwerty", "0qwerty0"));
        System.out.println("qwerty and qwerty00 - false, now is " + check("qwerty", "qwerty00"));
        System.out.println("qwerty and awsedrf - false, now is " + check("qwerty", "awsedrf"));
        System.out.println("qwwwerty and qywwter - false, now is " + check("qwwwerty", "qywwter"));
        System.out.println("both null or empty - true, now is " + check(null, null));
        System.out.println("both null or empty - true, now is " + check("", ""));
        System.out.println("both null or empty - true, now is " + check("   ", "   "));
        System.out.println("one of them is null, empty or have different size - false, now is " + check(null, "qwerty"));
        System.out.println("one of them is null, empty or have different size - false, now is " + check("qwerty", null));
        System.out.println("one of them is null, empty or have different size - false, now is " + check("qwerty", ""));
        System.out.println("one of them is null, empty or have different size - false, now is " + check("", "qwerty"));
        System.out.println("one of them is null, empty or have different size - false, now is " + check("qwerty", "   "));
        System.out.println("one of them is null, empty or have different size - false, now is " + check("   ", "qwerty"));
        System.out.println("one of them is null, empty or have different size - false, now is " + check("   ", " "));
        System.out.println("one of them is null, empty or have different size - false, now is " + check(" ", "   "));
        System.out.println("one of them is null, empty or have different size - false, now is " + check(" qwerty ", " qwerty"));
    }

    private static boolean check(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        if (s1 == null || s2 == null) return false;

        if (s1.isEmpty() && s2.isEmpty()) return true;
        if (s1.isEmpty() || s2.isEmpty()) return false;

        if (s1.equals(s2)) return true;
        if (s1.length() != s2.length()) return false;

        var s1Map = new HashMap<Character, Integer>(s1.length());
        var s2Map = new HashMap<Character, Integer>(s2.length());

        for (int i = 0; i < s1.length(); i++) {
            s1Map.compute(s1.charAt(i), (k, v) -> v == null ? 1 : v + 1);
            s2Map.compute(s2.charAt(i), (k, v) -> v == null ? 1 : v + 1);
        }

        return s1Map.equals(s2Map);
    }
}
