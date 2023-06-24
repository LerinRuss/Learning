package my.learning.jf.task;

public class LongestOneSubString {
    public static void main(String[] args) {
        /*
            null or empty - 0
            000 - 0
            111 - 3
            111000 - 3
            000111 - 3
            0011100 - 3
            110011 - 2
            1110011 - 3
            11000111 - 3
            011001110 - 3
            011100110 - 3
            0110110 - 2
         */

        System.out.println("null or empty - 0, res is = " + count(""));
        System.out.println("null or empty - 0, res is = " + count(null));
        System.out.println("000 - 0, res is = " + count("000"));
        System.out.println("111 - 3, res is = " + count("111"));
        System.out.println("111000 - 3, res is = " + count("111000"));
        System.out.println("000111 - 3, res is = " + count("000111"));
        System.out.println("0011100 - 3, res is = " + count("0011100"));
        System.out.println("110011 - 2, res is = " + count("110011"));
        System.out.println("1110011 - 3, res is = " + count("1110011"));
        System.out.println("11000111 - 3, res is = " + count("11000111"));
        System.out.println("011001110 - 3, res is = " + count("011001110"));
        System.out.println("011100110 - 3, res is = " + count("011100110"));
        System.out.println("0110110 - 2   , res is = " + count("0110110"));
    }

    private static int count(String ones) {
        if (ones == null || ones.isEmpty()) return 0;

        int max = 0;
        int curr = 0;
        for (char c : ones.toCharArray()) {
            curr++;
            if (c == '0') curr = 0;
            max = Math.max(max, curr);
        }

        return max;
    }
}
