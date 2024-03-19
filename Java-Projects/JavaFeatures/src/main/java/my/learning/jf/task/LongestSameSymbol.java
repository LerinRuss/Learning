package my.learning.jf.task;

import java.util.ArrayList;

public class LongestSameSymbol {
    public static void main(String[] args) {
        System.out.println("0: " + solve("aaa") + " - 3");
        System.out.println("1: " + solve("aaabb") + " - 3");
        System.out.println("2: " + solve("ababab") + " - 1");
        System.out.println("3: " + solve("aaabbb") + " - 3");
        System.out.println("4: " + solve("baaab") + " - 3");
        System.out.println("5: " + solve("bbaaa") + " - 3");
        System.out.println("6: " + solve("") + " - 0");
        System.out.println("7: " + solve("a") + " - 1");
        System.out.println("8: " + solve("ab") + " - 1");
    }

    private static int solve(String str) {
        if (str == null || str.isEmpty()) return 0;

        int res = 0;

        var arr = new ArrayList<>();

        for(int leftIndex = 0; leftIndex < str.length();) {
            int rightIndex = leftIndex;

            while (rightIndex < str.length() && str.charAt(rightIndex) == str.charAt(leftIndex)) {
                rightIndex++;
                res = Math.max(res, rightIndex - leftIndex);
            }
            leftIndex = rightIndex;
        }

        return res;
    }
}
