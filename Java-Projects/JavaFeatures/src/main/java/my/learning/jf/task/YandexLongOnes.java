package my.learning.jf.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class YandexLongOnes {
    public static void main(String[] args) throws IOException {
        char[] ones;
        try(var r = new BufferedReader(new InputStreamReader(System.in))) {
            int count = Integer.parseInt(r.readLine().strip());
            ones = new char[count];

            for(int i = 0; i < count; i++) {
                ones[i] = r.readLine().charAt(0);
            }
        }

        System.out.print(count(ones));
    }

    private static int count(char[] ones) {
        if (ones.length == 0) return 0;

        int counter = 0;
        int res = 0;

        for (char ch : ones) {
            if (ch == '1') counter++;
            res = Math.max(res, counter);
        }

        return res;
    }
}
