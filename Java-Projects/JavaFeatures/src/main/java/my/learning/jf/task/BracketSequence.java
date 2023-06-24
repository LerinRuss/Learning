package my.learning.jf.task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BracketSequence {
    public static void main(String[] args) {
        /*
            Given n return [2n]

            n = 1 returns [()]
            n = 2 return [()(), (())]

            1st send n-1 to get res
            2st add () to each gotten res (left and right) + add each elem of res to (%s)
            3st return res as array

            n = 3

            n = 2 ()()
                  (())

            n = 3
                  [()(), (())]
                  {()()(), (())(), ()(()), (()()), ((()))}


                  ()()()
                  ()(())
                  (())()

                  (()())
                  ((()))

            n = 4
                  {()()(), (())(), ()(()), (()()), ((()))}
                  {()()()(), (())()(), ()(())(), ()()(()), ()(()()), (()())(), ()((())), ((()))(),
                  (()()()), ((())()), (()(())), ((()())), (((())))}

                  {()()()(), (())()(), ()(())(), ()()(()), ()(()()), ()((()))}

                  ()()()()

                  ()()(())
                  ()(())()
                  (())()()

                  ()(()())
                  ()((()))

                  (()())()
                  (())(())

                  ()(())()
                  (())()()
         */

//        System.out.println(obtain(4));
        print("", 0, 0, 4);
    }

    /*
            n = 1 returns [()]
            n = 2 return [()(), (())]

            1st send n-1 to get res
            2st add () to each gotten res (left and right) + add each elem of res to (%s)
            3st return res as array
     */
    private static Set<String> obtain(int n) {
        if (n == 1) return Set.of("()");

        Set<String> previousRes = obtain(n - 1);
        var res = new HashSet<String>(3 * previousRes.size());

        previousRes.forEach(elem -> {
            res.add("()"+elem);
            res.add(elem+"()");
            res.add("(" + elem + ")");
        });

        return res;
    }

    private static void print(String curr, int open, int closed, int n) {
        if (curr.length() == 2 * n) {
            System.out.println(curr);
            return;
        }

        if (open < n) print(curr + "(", open + 1, closed, n);
        if (closed < open) print(curr + ")", open, closed + 1, n);
    }
}

/*
open|closed|curr|n=2
2 2 "()()" - print*
2 1 "()(" -> closed -> *
1 1 "()" -> open -> *
2 2 "(())" - print*
2 1 "(()" -> closed -> *
2 0 "((" - > closed -> *
1 0 "(" -> open -> closed -> *
0 0 "" -> open -> *
 */
