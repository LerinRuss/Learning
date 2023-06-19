package my.learning.jf.task;

import java.util.*;

public class Brackets {
    private static final Map<Character, Character> closingToOpening = Map.of(
            ')', '(',
            ']', '[',
            '}', '{');

    public static void main(String[] args) {
        // {[()]} - true
        // {([)]} = false
        // {([]} - false
        // null, empty - true

        System.out.println("{[()]} - true, now is " + checkBrackets("{[()]}"));
        System.out.println("{([)]} = false, now is " + checkBrackets("{([)]}"));
        System.out.println("{([]} - false, now is " + checkBrackets("{([]}"));
        System.out.println("null, empty - true, now is " + checkBrackets(null));
        System.out.println("null, empty - true, now is " + checkBrackets(""));
    }

    private static boolean checkBrackets(String line) {
        if (line == null || line.isEmpty()) return true;

        Deque<Character> res = new ArrayDeque<>(line.length());

        for (char c : line.toCharArray()) {
            if (res.peek() != null && res.peek() == closingToOpening.get(c)) {
                res.pop();
            } else {
                res.push(c);
            }
        }

        return res.isEmpty();
    }
}
